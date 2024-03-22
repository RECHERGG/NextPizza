package de.rechergg.nextpizza.user;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public record Token(@NotNull String token, long created, @NotNull String lastIp, @NotNull String lastUserAgent) {

    public Token(@NotNull String token, @NotNull String lastIp, @NotNull String lastUserAgent) {
        this(token, System.currentTimeMillis() - 1000 * 60 * 60, lastIp, lastUserAgent);
    }

    @NotNull
    public String toString() {
        return this.token;
    }

    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("created", this.created)
                .put("lastIp", this.lastIp)
                .put("lastUserAgent", this.lastUserAgent);
    }

    public String serialize() {
        return token + "##" + created + "##" + lastIp + "##" + lastUserAgent;
    }

    public static Token deSerialize(@NotNull String serialized) {
        String[] args = serialized.split("##");
        if (args.length != 4) return null;

        return new Token(args[0], Long.parseLong(args[1]), args[2], args[3]);
    }
}
