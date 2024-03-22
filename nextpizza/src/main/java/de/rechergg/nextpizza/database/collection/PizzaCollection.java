package de.rechergg.nextpizza.database.collection;

import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import de.rechergg.nextpizza.database.Database;
import de.rechergg.nextpizza.database.subscriber.IEmptySubscriber;
import de.rechergg.nextpizza.settings.PizzaOrder;

public record PizzaCollection(Database database) implements ICollection<PizzaOrder> {

    public void addPizzaRequest(PizzaOrder pizzaOrder) {
        collection().insertOne(pizzaOrder, new InsertOneOptions().bypassDocumentValidation(true)).subscribe(new IEmptySubscriber<InsertOneResult>() {
            @Override
            public void onError(Throwable throwable) {
                database.logger().error(throwable.getMessage());
            }
        });
    }

    @Override
    public void load() {
        collection().find().subscribe(new IEmptySubscriber<PizzaOrder>() {
            @Override
            public void onNext(PizzaOrder pizzaOrder) {
                IEmptySubscriber.super.onNext(pizzaOrder);
            }

            @Override
            public void onError(Throwable throwable) {
                database.logger().error(throwable.getMessage());
            }
        });
    }

    @Override
    public MongoCollection<PizzaOrder> collection() {
        return this.database.database().getCollection("pizza", PizzaOrder.class);
    }
}
