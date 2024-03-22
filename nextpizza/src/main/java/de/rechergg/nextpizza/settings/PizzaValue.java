package de.rechergg.nextpizza.settings;

import org.jetbrains.annotations.NotNull;

public record PizzaValue(@NotNull PizzaType type, int slices) {
}
