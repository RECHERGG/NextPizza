package de.rechergg.nextpizza.endpoint;

import org.json.JSONObject;

public class Responses {

    public static String invalidBody() {
        return new JSONObject()
                .put("status", 400)
                .put("message", "Body is not a JSONObject")
                .toString();
    }

    public static String userNotFound() {
        return new JSONObject()
                .put("status", 400)
                .put("message", "The user could not be found")
                .toString();
    }

    public static String malformedRequest() {
        return new JSONObject()
                .put("status", 400)
                .put("message", "Malformed request")
                .toString();
    }

    public static String invalidCredentials() {
        return new JSONObject()
                .put("status", 401)
                .put("message", "Invalid credentials")
                .toString();
    }

    public static String missingToken() {
        return new JSONObject()
                .put("status", 401)
                .put("message", "No token provided")
                .toString();
    }

    public static String malformedToken() {
        return new JSONObject()
                .put("status", 400)
                .put("message", "Token is malformed")
                .toString();
    }
}
