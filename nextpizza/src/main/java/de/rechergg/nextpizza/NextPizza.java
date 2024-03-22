package de.rechergg.nextpizza;

import de.rechergg.nextpizza.database.Database;
import de.rechergg.nextpizza.endpoint.EndpointManager;
import de.rechergg.nextpizza.settings.PizzaManager;
import de.rechergg.nextpizza.user.UserManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class NextPizza {

    private final Logger logger;
    private final Database database;
    private final UserManager userManager;
    private final PizzaManager pizzaManager;
    private final EndpointManager endpointManager;

    public NextPizza() {
        this.logger = LoggerFactory.getLogger(NextPizza.class);
        this.logger.info("Starting NextPizza`s Backend...");

        this.database = new Database(this);

        this.userManager = new UserManager(this);
        this.pizzaManager = new PizzaManager(this);

        this.endpointManager = new EndpointManager(this);

        this.logger.info("Admin account created with \"{}\" as password!", this.userManager.createUser("admin", "admin"));
    }

    @NotNull
    public Logger logger() {
        return this.logger;
    }

    @NotNull
    public Database database() {
        return this.database;
    }

    @NotNull
    public UserManager userManager() {
        return this.userManager;
    }

    @NotNull
    public PizzaManager pizzaManager() {
        return pizzaManager;
    }

    @NotNull
    public EndpointManager endpointManager() {
        return endpointManager;
    }
}