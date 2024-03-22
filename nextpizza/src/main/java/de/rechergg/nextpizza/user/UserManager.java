package de.rechergg.nextpizza.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mongodb.lang.Nullable;
import de.rechergg.nextpizza.NextPizza;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private final Logger logger;
    private final NextPizza nextPizza;
    private final List<User> users;

    public UserManager(@NotNull NextPizza nextPizza) {
        this.logger = LoggerFactory.getLogger(UserManager.class);
        this.nextPizza = nextPizza;
        this.users = new ArrayList<>();
    }

    @NotNull
    public String createUser(@NotNull String name, @NotNull String lastName) {
        String password = new Keygen(12).generateKey();
        String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        UUID uuid = UUID.randomUUID();
        User user = new User(name, lastName, uuid, passwordHash);

        this.users.add(user);
        this.nextPizza.database().userCollection().addUser(user);
        logger().info("User \"{} {}\" with UUID \"{}\" has been created!", name, lastName, uuid );

        return password;
    }

    public void deleteUser(@NotNull User user) {
        this.users.remove(user);
        this.nextPizza.database().userCollection().deleteUser(user);
    }

    public Token login(@NotNull User user, @NotNull String password, @NotNull String ip, @NotNull String userAgent) {
        boolean valid = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash()).verified;
        if (!valid) return null;

        if (user.tokens().stream().anyMatch(t -> !t.lastIp().equals(ip))) {
            //Nachricht?!
        }
        Token token = new Token(new Keygen().generateKey(), ip, userAgent);

        user.tokens().add(token);
        updateUser(user);
        return token;
    }

    public void setPassword(@NotNull User user, @NotNull String password) {
        String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        String name = user.name();
        String lastName = user.lastName();
        UUID uuid = user.uuid();
        List<Token> tokens = user.tokens();
        long created = user.created();

        User newUser = new User(name, lastName, uuid, passwordHash, tokens, created);
        updateUser(newUser);
    }

    public void setNameAndLastName(@NotNull User user, @NotNull String name, @NotNull String lastName) {
        String passwordHash = user.passwordHash();
        UUID uuid = user.uuid();
        List<Token> tokens = user.tokens();
        long created = user.created();

        User newUser = new User(name, lastName, uuid, passwordHash, tokens, created);
        updateUser(newUser);
    }

    protected void logout(@NotNull User user, @NotNull String token) {
        Token tokenByToken = getToken(user, token);
        if (tokenByToken == null) return;

        user.tokens().remove(tokenByToken);
        updateUser(user);
    }

    public void logoutAll(@NotNull User user) {
        user.tokens().clear();
        updateUser(user);
    }

    public void updateToken(@NotNull User user, @NotNull String token, @NotNull String ip, @NotNull String userAgent) {
        Token tokenByToken = getToken(user, token);
        if (tokenByToken == null) return;

        for (int i = 0; i < user.tokens().size(); i++) {
            Token t = user.tokens().get(i);
            if (!user.tokens().get(i).equals(tokenByToken)) continue;

            user.tokens().remove(t);
            user.tokens().add(new Token(t.toString(), t.created(), ip, userAgent));
        }
    }

    private void updateUser(@NotNull User user) {
        this.users.stream().filter(u -> u.uuid().equals(user.uuid())).findFirst().ifPresent(us -> {
            this.users.remove(us);
            this.users.add(user);
        });
        this.nextPizza.database().userCollection().updateUser(user);
    }

    @Nullable
    public User getUser(@NotNull String token) {
        return this.users.stream().filter(user -> user.tokens().stream().map(Token::toString)
                .toList().contains(token)).findFirst().orElse(null);
    }

    @Nullable
    public User getUser(@NotNull UUID uuid) {
        return this.users.stream()
                .filter(user -> user.uuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public User getUser(@NotNull String name, @NotNull String lastName) {
        return this.users.stream()
                .filter(user -> user.name().equals(name) && user.lastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private Token getToken(@NotNull User user, @NotNull String token) {
        return user.tokens().stream().filter(t -> t.toString().equals(token)).findFirst().orElse(null);
    }

    @NotNull
    public Logger logger() {
        return this.logger;
    }

    @NotNull
    public List<User> users() {
        return this.users;
    }
}
