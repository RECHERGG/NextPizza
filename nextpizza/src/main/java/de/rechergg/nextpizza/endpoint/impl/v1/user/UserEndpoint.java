package de.rechergg.nextpizza.endpoint.impl.v1.user;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.endpoint.Endpoint;
import de.rechergg.nextpizza.endpoint.Responses;
import de.rechergg.nextpizza.user.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.UUID;

public class UserEndpoint extends Endpoint {

    private static final String PATH = Endpoint.API_PATH + "user";

    public UserEndpoint(@NotNull NextPizza nextPizza) {
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

        if (!object.has("name") || !object.has("lastName")) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        String name = object.getString("name");
        String lastName = object.getString("lastName");
        UUID uuid = UUID.randomUUID();

        String password = this.nextPizza().userManager().createUser(name, lastName);

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("data", new JSONObject()
                        .put("name", name)
                        .put("lastName", lastName)
                        .put("uuid", uuid)
                        .put("password", password));

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
        String uuidParam = context.queryParam("uuid");

        if (uuidParam == null) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        UUID uuid = UUID.fromString(uuidParam);
        User user = this.nextPizza().userManager().getUser(uuid);

        if (user == null) {
            context.status(400);
            context.json(Responses.userNotFound());
            return;
        }

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("data", new JSONObject()
                        .put("name", user.name())
                        .put("lastName", user.lastName())
                        .put("uuid", user.uuid()));

        context.status(200);
        context.json(message.toString());
    }

    @Override
    public void delete(Context context) {
        JSONObject object = parseJSON(context.body());

        if (object == null) {
            context.status(400);
            context.json(Responses.invalidBody());
            return;
        }

        if (!object.has("uuid")) {
            context.status(400);
            context.json(Responses.malformedRequest());
            return;
        }

        UUID uuid = UUID.fromString(object.getString("uuid"));
        User user = this.nextPizza().userManager().getUser(uuid);

        if (user == null) {
            context.status(400);
            context.json(Responses.userNotFound());
            return;
        }

        this.nextPizza().userManager().deleteUser(user);

        JSONObject message = new JSONObject()
                .put("status", 200)
                .put("message", "Successfully deleted User");

        context.status(200);
        context.json(message.toString());
    }
}
