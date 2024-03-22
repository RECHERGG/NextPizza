package de.rechergg.nextpizza.settings;

public enum PizzaType {
    VEGGIE("VEGGIE"), MEAT("MEAT"), VEGAN("VEGAN");

    private final String value;
    PizzaType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
