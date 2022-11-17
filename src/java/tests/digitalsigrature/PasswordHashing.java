package tests.digitalsigrature;

import database.services.UserManagementService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordHashing {

    @Test
    public void authenticationIsAllowedForNewCreatedUser() throws Exception {
        UserManagementService userManager = new UserManagementService();
        String email = "email@mail.com";
        String password = "password";

        userManager.addUser(email, password);

        assertTrue(userManager.isAuthenticationAllowed(email, password));
    }

    @Test
    public void authenticationWithInvalidPasswordIsNotAllowed() throws Exception {
        UserManagementService userManager = new UserManagementService();
        String email = "email@mail.com";
        String password = "password";

        userManager.addUser(email, password);

        assertFalse(userManager.isAuthenticationAllowed(email, "some_string"));
    }
}
