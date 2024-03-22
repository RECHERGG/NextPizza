package de.rechergg.nextpizza.endpoint;

import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.endpoint.impl.v1.SessionEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.date.LastFridaysEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.date.NextFridayEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.pizza.PizzaAreaChartEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.pizza.PizzaEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.user.LoginEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.user.UserEditEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.user.UserEndpoint;
import de.rechergg.nextpizza.endpoint.impl.v1.user.UsersEndpoint;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EndpointManager {

    private final Logger logger;
    private final List<Endpoint> endpoints;
    private final NextPizza nextPizza;
    private final Javalin javalin;

    public EndpointManager(NextPizza nextPizza) {
        this.logger = LoggerFactory.getLogger(EndpointManager.class);

        this.nextPizza = nextPizza;
        this.endpoints = new ArrayList<>();

        this.javalin = Javalin.createAndStart(config -> {
            config.showJavalinBanner = false;
        });

        loadEndpoints();
    }

    private void registerEndpoint(@NotNull Endpoint endpoint) {
        this.endpoints.add(endpoint);
    }

    private void loadEndpoints() {
        logger().info("Registering Endpoints...");

        registerEndpoint(new UserEndpoint(nextPizza()));
        registerEndpoint(new LoginEndpoint(nextPizza()));
        registerEndpoint(new UsersEndpoint(nextPizza()));
        registerEndpoint(new UserEditEndpoint(nextPizza()));
        registerEndpoint(new SessionEndpoint(nextPizza()));
        registerEndpoint(new PizzaEndpoint(nextPizza()));
        registerEndpoint(new PizzaAreaChartEndpoint(nextPizza()));
        registerEndpoint(new NextFridayEndpoint(nextPizza()));
        registerEndpoint(new LastFridaysEndpoint(nextPizza()));

        this.endpoints.forEach(endpoint -> {
            this.javalin.post(endpoint.path(), endpoint::post);
            this.javalin.put(endpoint.path(), endpoint::post);
            this.javalin.get(endpoint.path(), endpoint::get);
            this.javalin.delete(endpoint.path(), endpoint::delete);
        });

        logger().info("Successfully registered Endpoints...");
    }

    @NotNull
    public Logger logger() {
        return logger;
    }

    @NotNull
    public NextPizza nextPizza() {
        return nextPizza;
    }
}
