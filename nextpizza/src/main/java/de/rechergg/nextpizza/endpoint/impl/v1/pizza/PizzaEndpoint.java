package de.rechergg.nextpizza.endpoint.impl.v1.pizza;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.endpoint.Endpoint;
import de.rechergg.nextpizza.endpoint.Responses;
import de.rechergg.nextpizza.settings.PizzaManager;
import de.rechergg.nextpizza.settings.PizzaOrder;
import de.rechergg.nextpizza.settings.PizzaType;
import de.rechergg.nextpizza.settings.PizzaValue;
import de.rechergg.nextpizza.user.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PizzaEndpoint extends Endpoint {

    private static final String PATH = Endpoint.API_PATH + "pizza/";


    public PizzaEndpoint(@NotNull NextPizza nextPizza) {
        super(PATH, nextPizza);
    }

    @Override
    public void post(Context context) {
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

        User u = nextPizza().userManager().getUser(authArray[1]);
        if (u == null) {
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

        nextPizza().userManager().updateToken(u, authArray[1], ip, userAgent);
        JSONObject object = parseJSON(context.body());

        if (object == null) {
            context.status(400);
            context.json(Responses.invalidBody());
            return;
        }

        if (!object.has("uuid") || !object.has("pizzas") || object.getJSONArray("pizzas").isEmpty()) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        UUID uuid = UUID.fromString(object.getString("uuid"));
        List<PizzaValue> pizzas = new ArrayList<>();

        User user = this.nextPizza().userManager().getUser(uuid);

        if (user == null) {
            context.status(400);
            context.json(Responses.userNotFound());
            return;
        }

        JSONArray array = object.getJSONArray("pizzas");
        for (int n = 0; n < array.length(); n++) {
            JSONObject jsonObject = array.getJSONObject(n);

            int slices = jsonObject.getInt("slices");
            PizzaType type = null;
            for (PizzaType pizzaType : PizzaType.values()) {
                if (pizzaType.name().equalsIgnoreCase(jsonObject.getString("type"))) {
                    type = pizzaType;
                    break;
                }
            }

            if (type == null) {
                context.status(400);
                context.json(Responses.malformedRequest());
                return;
            }

            pizzas.add(new PizzaValue(type, slices));
        }

        PizzaManager pizzaManager = this.nextPizza().pizzaManager();
        pizzaManager.addRequest(user, pizzaManager.getNextFridays().getFirst(), pizzas);

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("message", "Request added");

        context.status(200);
        context.json(message.toString());
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

        JSONObject object = parseJSON(context.body());

        if (object == null) {
            context.status(400);
            context.json(Responses.invalidBody());
            return;
        }

        if (!object.has("date")) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }
        String date = object.getString("date");
        List<PizzaOrder> pizzaOrders = this.nextPizza().pizzaManager().getPizzaRequestsByDate(date);

        if (pizzaOrders == null || pizzaOrders.isEmpty()) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        List<JSONObject> array = new ArrayList<>();
        pizzaOrders.forEach(request -> {

            JSONArray pizzaArray = new JSONArray();
            request.pizzas().forEach(pizza ->{
                pizzaArray.put(new JSONObject()
                        .put("type", pizza.type())
                        .put("slices", pizza.slices()));
            });

            array.add(new JSONObject()
                    .put("uuid", request.user().uuid())
                    .put("pizzas", pizzaArray));
        });

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("data", new JSONObject()
                        .put("users", array));

        context.status(200);
        context.json(message.toString());
    }

    @Override
    public void delete(Context context) {

    }
}
