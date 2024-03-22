package de.rechergg.nextpizza.settings;

import de.rechergg.nextpizza.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

//one pizza has normally 8 slices +-2
public record PizzaOrder(@NotNull User user, @NotNull Date date, @NotNull List<PizzaValue> pizzas) {

}
