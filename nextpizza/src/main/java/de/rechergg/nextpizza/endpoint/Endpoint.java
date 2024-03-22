package de.rechergg.nextpizza.endpoint;

import de.rechergg.nextpizza.NextPizza;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Endpoint {

    public static final String API_PATH = "/api/v1/";
    private final String path;
    private final NextPizza nextPizza;

    public Endpoint(@NotNull String path, @NotNull NextPizza nextPizza) {
        this.path = path;
        this.nextPizza = nextPizza;
    }

    public abstract void post(Context context);
    public abstract void put(Context context);
    public abstract void get(Context context);
    public abstract void delete(Context context);

    public JSONObject parseJSON(String body) {
        JSONObject object;
        try {
            object = new JSONObject(body);
        } catch (JSONException exception) {
            return null;
        }
        return object;
    }

    @NotNull
    public NextPizza nextPizza() {
        return this.nextPizza;
    }

    public String path() {
        return path;
    }
}
