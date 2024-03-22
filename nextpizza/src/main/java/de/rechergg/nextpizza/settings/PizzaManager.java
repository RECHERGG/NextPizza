package de.rechergg.nextpizza.settings;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PizzaManager {

    private final NextPizza nextPizza;
    private final List<PizzaOrder> pizzaOrders;

    public PizzaManager(NextPizza nextPizza) {
        this.nextPizza = nextPizza;
        this.pizzaOrders = new ArrayList<>();
    }

    public void addRequest(@NotNull User user, @NotNull Date date, List<PizzaValue> pizzas) {
        PizzaOrder pizzaOrder = new PizzaOrder(user, date, pizzas);

        this.pizzaOrders.add(pizzaOrder);
        this.nextPizza.database().pizzaCollection().addPizzaRequest(pizzaOrder);
    }

    public List<Date> getNextFridays() {
        DateTime startDate = DateTime.parse(Instant.now().toString());
        DateTime endDate = DateTime.parse(Instant.now().plus(150, ChronoUnit.DAYS).toString());

        List<Date> fridays = new ArrayList<>();
        boolean reachedAFriday = false;
        while (startDate.isBefore(endDate)) {
            if (startDate.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                fridays.add(startDate.toDate());
                reachedAFriday = true;
            }
            if (reachedAFriday) {
                startDate = startDate.plusWeeks(1);
            } else {
                startDate = startDate.plusDays(1);
            }
        }

        return fridays;
    }

    public List<Date> getLastFridays() {
        DateTime startDate = DateTime.parse(Instant.now().toString());
        DateTime endDate = DateTime.parse(Instant.now().minus(53, ChronoUnit.DAYS).toString());

        List<Date> fridays = new ArrayList<>();
        boolean reachedAFriday = false;
        while (startDate.isAfter(endDate)) {
            if (startDate.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                if (!isToday(startDate.toDate())) {
                    fridays.add(startDate.toDate());
                }
                reachedAFriday = true;
            }
            if (reachedAFriday) {
                startDate = startDate.minusWeeks(1);
            } else {
                startDate = startDate.minusDays(1);
            }
        }

        return fridays;
    }

    public List<Long> getLastFridaysInMillis() {
        DateTime startDate = DateTime.parse(Instant.now().toString());
        DateTime endDate = DateTime.parse(Instant.now().minus(53, ChronoUnit.DAYS).toString());

        List<Long> fridays = new ArrayList<>();
        boolean reachedAFriday = false;
        while (startDate.isAfter(endDate)) {
            if (startDate.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                if (!isToday(startDate.toDate())) {
                    fridays.add(startDate.toDate().getTime());
                }
                reachedAFriday = true;
            }
            if (reachedAFriday) {
                startDate = startDate.minusWeeks(1);
            } else {
                startDate = startDate.minusDays(1);
            }
        }

        return fridays;
    }

    private static boolean isToday(Date date){
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate  = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }

    @Nullable
    public List<PizzaOrder> getPizzaRequestsByDate(@NotNull Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");

        return this.pizzaOrders.stream()
                .filter(request -> dateFormat.format(request.date()).equals(dateFormat.format(date)))
                .toList();
    }

    @Nullable
    public List<PizzaOrder> getPizzaRequestsByDate(@NotNull String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");

        return this.pizzaOrders.stream()
                .filter(request -> dateFormat.format(request.date()).equals(date))
                .toList();
    }
}
