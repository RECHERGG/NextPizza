package de.rechergg.nextpizza.endpoint.impl.v1.user;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.endpoint.Endpoint;
import de.rechergg.nextpizza.endpoint.Responses;
import de.rechergg.nextpizza.user.Token;
import de.rechergg.nextpizza.user.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class LoginEndpoint extends Endpoint {

    private static final String PATH = Endpoint.API_PATH + "login/";
    public LoginEndpoint(@NotNull NextPizza nextPizza) {
        super(PATH, nextPizza);
    }

    @Override
    public void post(Context context) {
        JSONObject object = parseJSON(context.body());

        if (object == null) {
            context.status(400);
            context.json(Responses.invalidBody());
            return;
        }

        if (!object.has("name") || !object.has("lastName") || !object.has("password")) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        String name = object.getString("name");
        String lastName = object.getString("lastName");
        String password = object.getString("password");

        User user = nextPizza().userManager().getUser(name, lastName);

        if (user == null) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        String userAgent = context.userAgent();
        String ip = context.header("cf-connection-ip");

        if (userAgent == null) {
            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
        }

        if (ip == null) {
            ip = context.ip();
        }

        Token token = nextPizza().userManager().login(user, password, ip, userAgent);

        if (token == null) {
            context.status(400);
            context.json(Responses.invalidCredentials());
            return;
        }

        context.json(new JSONObject()
                .put("status", 200)
                .put("token", token.toString())
                .toString());
    }

    @Override
    public void put(Context context) {

    }

    @Override
    public void get(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
