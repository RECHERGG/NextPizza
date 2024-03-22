package de.rechergg.nextpizza.user;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Keygen {

    private final int length;

    public Keygen() {
        this.length = 32;
    }

    public Keygen(int length) {
        this.length = length;
    }

    @NotNull
    public String generateKey() {
        return generate("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    public int generateInt() {
        return Integer.parseInt(generateIntAsString());
    }

    @NotNull
    public String generateIntAsString() {
        return generate("0123456789");
    }

    @NotNull
    private String generate(@NotNull String pattern) {
        List<Character> chars = new ArrayList<>();
        for (char character : pattern.toCharArray()) {
            chars.add(character);
        }

        StringBuilder key = new StringBuilder();
        while (key.length() < length) {
            Collections.shuffle(chars);
            key.append(chars.getFirst());
        }
        return key.toString();
    }
}
