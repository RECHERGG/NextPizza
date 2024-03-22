package de.rechergg.nextpizza.database.collection;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import de.rechergg.nextpizza.database.Database;
import de.rechergg.nextpizza.database.subscriber.IEmptySubscriber;
import de.rechergg.nextpizza.user.User;
import de.rechergg.nextpizza.user.UserManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record UserCollection(Database database) implements ICollection<User> {

    public void addUser(@NotNull User user) {
        collection().insertOne(user, new InsertOneOptions().bypassDocumentValidation(true)).subscribe(new IEmptySubscriber<InsertOneResult>() {
            @Override
            public void onError(Throwable throwable) {
                database.logger().error(throwable.getMessage());
            }
        });
    }

    public void updateUser(@NotNull User user) {
        collection().replaceOne(Filters.eq("uuid", user.uuid()), user).subscribe(new IEmptySubscriber<UpdateResult>() {
            @Override
            public void onError(Throwable throwable) {
                database.logger().error(throwable.getMessage());
            }
        });
    }

    public void deleteUser(@NotNull User user) {
        collection().deleteOne(Filters.eq("uuid", user.uuid())).subscribe(new IEmptySubscriber<>() {
            @Override
            public void onError(Throwable throwable) {
                database.logger().error(throwable.getMessage());
            }
        });
    }

    @Override
    public void load() {
        collection().find().subscribe(new IEmptySubscriber<User>() {
            @Override
            public void onNext(User user) {
                if (user == null) {
                    return;
                }

                UserManager userManager = database.nextPizza().userManager();
                List<User> users = userManager.users();
                if (users.contains(user)) return;

                userManager.users().add(user);
            }

            @Override
            public void onError(Throwable throwable) {
                database.logger().error(throwable.getMessage());
            }
        });
    }

    @Override
    public MongoCollection<User> collection() {
        return this.database.database().getCollection("user", User.class);
    }
}
