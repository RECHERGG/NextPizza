package de.rechergg.nextpizza.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import de.rechergg.nextpizza.NextPizza;
import de.rechergg.nextpizza.database.collection.PizzaCollection;
import de.rechergg.nextpizza.database.collection.UserCollection;
import org.bson.UuidRepresentation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    private final Logger logger;
    private final NextPizza nextPizza;
    private final MongoClient client;
    private final MongoDatabase database;
    private final UserCollection userCollection;
    private final PizzaCollection pizzaCollection;

    public Database(NextPizza nextPizza) {
        this.logger = LoggerFactory.getLogger(Database.class);
        logger().info("Setting up Database...");

        this.nextPizza = nextPizza;

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27018"))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .retryWrites(true)
                .retryReads(true)
                .build();

        this.client = MongoClients.create(settings);
        this.database = this.client.getDatabase("nextpizza");

        this.userCollection = new UserCollection(this);
        this.pizzaCollection = new PizzaCollection(this);

        logger().info("Successfully connected to Database!");
    }

    @NotNull
    public NextPizza nextPizza() {
        return this.nextPizza;
    }

    @NotNull
    public Logger logger() {
        return this.logger;
    }

    @NotNull
    public MongoClient client() {
        return this.client;
    }

    @NotNull
    public MongoDatabase database() {
        return this.database;
    }

    @NotNull
    public UserCollection userCollection() {
        return this.userCollection;
    }

    @NotNull
    public PizzaCollection pizzaCollection() {
        return pizzaCollection;
    }
}
