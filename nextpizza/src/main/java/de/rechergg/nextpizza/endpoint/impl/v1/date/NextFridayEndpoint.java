package de.rechergg.nextpizza.endpoint.impl.v1.date;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.endpoint.Endpoint;
import de.rechergg.nextpizza.endpoint.Responses;
import de.rechergg.nextpizza.user.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class NextFridayEndpoint extends Endpoint {

    private final static String PATH = Endpoint.API_PATH + "next-friday";

    public NextFridayEndpoint(@NotNull NextPizza nextPizza) {
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

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("data", new JSONObject()
                        .put("friday", nextPizza().pizzaManager().getNextFridays().getFirst().getTime()));

        context.status(200);
        context.json(message.toString());
    }

    @Override
    public void delete(Context context) {

    }
}
