package de.rechergg.nextpizza.user;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record User(@NotNull String name, @NotNull String lastName, @NotNull UUID uuid, @NotNull String passwordHash, @NotNull List<Token> tokens, long created) {

    public User(@NotNull String name, @NotNull String lastName, @NotNull UUID uuid, @NotNull String passwordHash) {
        this(name, lastName, uuid, passwordHash, new ArrayList<>(), System.currentTimeMillis());
    }

    @NotNull
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("name", this.name)
                .put("lastName", this.lastName)
                .put("uuid", this.uuid)
                .put("created", this.created);
    }
}