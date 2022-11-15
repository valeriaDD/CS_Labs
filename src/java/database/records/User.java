package database.records;

import java.security.*;

public record User(String email, String passwordHash, PublicKey publicKey) {

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public byte[] signMessage(String plaintext, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(plaintext.getBytes());

        return signature.sign();
    }
}
