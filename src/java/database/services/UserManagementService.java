package database.services;

import database.Database;
import database.records.User;

import java.security.*;

public class UserManagementService {
    private final Database database;
    private final KeyPairGenerator keyPairGenerator;


    public UserManagementService() throws NoSuchAlgorithmException {
        this.database = new Database();
        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        this.keyPairGenerator.initialize(2048);
    }

    public PrivateKey addUser(String email, String password) throws Exception {
        if (database.getUser(email) != null) {
            throw new Exception("Invalid email");
        }
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        String hashedPassword = new String(messageDigest.digest(password.getBytes()));
        User user = new User(email, hashedPassword, keyPair.getPublic());

        this.database.addUser(email, user);

        return keyPair.getPrivate();
    }

    public boolean isAuthenticationAllowed(String email, String password) throws Exception {
        User user = database.getUser(email);
        if (user == null) {
            throw new Exception("Invalid email or password");
        }
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String hashedPassword = new String(messageDigest.digest(password.getBytes()));

        return user.getPasswordHash().equals(hashedPassword);
    }

    public boolean isCorrectSignature(String email, String message, byte[] signature) throws Exception {
        User user = database.getUser(email);
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        Signature ecdsaSignature = Signature.getInstance("SHA256withECDSA");
        ecdsaSignature.initVerify(user.getPublicKey());
        ecdsaSignature.update(message.getBytes());

        return ecdsaSignature.verify(signature);
    }
}
