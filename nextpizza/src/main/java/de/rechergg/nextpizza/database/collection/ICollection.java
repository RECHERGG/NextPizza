package de.rechergg.nextpizza.database.collection;

import com.mongodb.reactivestreams.client.MongoCollection;

public interface ICollection<T> {

    void load();

    MongoCollection<T> collection();
}
