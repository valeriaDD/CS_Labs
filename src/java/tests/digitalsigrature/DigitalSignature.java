package tests.digitalsigrature;

import database.records.User;
import database.services.UserManagementService;
import org.junit.Test;

import java.security.PrivateKey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DigitalSignature {

    @Test
    public void userCanSendMessageWithSignature() throws Exception {
        UserManagementService userManager = new UserManagementService();
        String email = "email@mail.com";
        String password = "password";
        String message = "Some text";
        PrivateKey privateKey = userManager.addUser(email, password);

        User user = userManager.getUser(email);
        byte[] signature = user.signMessage(message, privateKey);

        assertTrue(userManager.isCorrectSignature(email, message, signature));
    }

    @Test
    public void alteredTextDoesntPassSignatureCheck() throws Exception {
        UserManagementService userManager = new UserManagementService();
        String email = "email@mail.com";
        String password = "password";
        String message = "Some text";
        PrivateKey privateKey = userManager.addUser(email, password);

        User user = userManager.getUser(email);
        byte[] signature = user.signMessage("Some other text", privateKey);

        assertFalse(userManager.isCorrectSignature(email, message, signature));
    }
}
