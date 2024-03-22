package de.rechergg.nextpizza.endpoint.impl.v1.pizza;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.endpoint.Endpoint;
import de.rechergg.nextpizza.endpoint.Responses;
import de.rechergg.nextpizza.settings.PizzaOrder;
import de.rechergg.nextpizza.user.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzaAreaChartEndpoint extends Endpoint {

    private static final String PATH = Endpoint.API_PATH + "pizza-last-fridays/";

    public PizzaAreaChartEndpoint(@NotNull NextPizza nextPizza) {
        super(PATH, nextPizza);
    }

    @Override
    public void post(Context context) {

    }

    @Override
    public void put(Context context) {

    }

    @Override
    public void get(Context context) {
        String auth = context.header("Authorization");

        if (auth == null) {
            context.status(401);
            context.json(Responses.missingToken());
            return;
        }

        String[] authArray = auth.split(" ");
        if (authArray.length != 2 || !authArray[0].equals("Bearer")) {
            context.status(400);
            context.json(Responses.malformedToken());
            return;
        }

        User user = nextPizza().userManager().getUser(authArray[1]);
        if (user == null) {
            context.status(401);
            context.json(Responses.invalidCredentials());
            return;
        }

        String ip = context.header("cf-connection-ip");
        String userAgent = context.userAgent();
        if (ip == null) {
            ip = context.ip();
        }

        if (userAgent == null) {
            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
        }

        nextPizza().userManager().updateToken(user, authArray[1], ip, userAgent);

        List<Date> lastFridays = nextPizza().pizzaManager().getLastFridays().reversed();
        lastFridays.add(nextPizza().pizzaManager().getNextFridays().getFirst());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");

        List<JSONObject> pizzasDateArray = new ArrayList<>();
        JSONArray pizzaArray = new JSONArray();

        lastFridays.forEach(friday -> {
            List<PizzaOrder> pizzaOrders = new ArrayList<>(nextPizza().pizzaManager().getPizzaRequestsByDate(friday));

            AtomicInteger meatSlices = new AtomicInteger();
            AtomicInteger veggieSlices = new AtomicInteger();
            AtomicInteger veganSlices = new AtomicInteger();

            pizzaOrders.forEach(request -> {
                if (dateFormat.format(request.date()).equals(dateFormat.format(friday))) {
                    request.pizzas().forEach(pizza -> {
                        if (pizza.type().name().equals("MEAT")) {
                            meatSlices.addAndGet(pizza.slices());
                        }

                        if (pizza.type().name().equals("VEGGIE")) {
                            veggieSlices.addAndGet(pizza.slices());
                        }

                        if (pizza.type().name().equals("VEGAN")) {
                            veganSlices.addAndGet(pizza.slices());
                        }

                    });
                }
            });

            pizzaArray.put(new JSONObject()
                    .put("type", "MEAT")
                    .put("slices", meatSlices));

            pizzaArray.put(new JSONObject()
                    .put("type", "VEGGIE")
                    .put("slices", veggieSlices));

            pizzaArray.put(new JSONObject()
                    .put("type", "VEGAN")
                    .put("slices", veganSlices));

            pizzasDateArray.add(new JSONObject()
                    .put("date", friday.getTime())
                    .put("pizzas", pizzaArray.toList()));

            pizzaArray.clear();
        });

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("data", new JSONObject()
                        .put("fridays", pizzasDateArray));

        context.status(200);
        context.json(message.toString());
    }

    @Override
    public void delete(Context context) {

    }
}
