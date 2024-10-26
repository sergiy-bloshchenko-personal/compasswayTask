package org.example.tests;

import io.restassured.response.Response;
import org.example.helpers.RestHelper;
import org.example.objects.Email;
import org.example.objects.Emails;
import org.example.objects.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApiTesting {

    private static final User qaUser = User.getQaUser();
    private static User createNewUser(){
        User newUser = new User();
        Response createNewUser = RestHelper.postUsers(qaUser,newUser);
        assertEquals("Status code is not correct on user '"+newUser.username+"' creation " + createNewUser.asString(), 201, createNewUser.statusCode());
        User new_user = createNewUser.as(User.class);
        new_user.password = newUser.password;
        return new_user;
    }

    @Test
    public void shouldBePossibleCreateNewUserTesting(){
        User newUser = createNewUser();
        assertEquals("New user '"+newUser.username+"' is not authorized", newUser, RestHelper.getUsersCurrent(newUser));
    }

    @Test
    public void shouldNotBePossibleCreateExistingUserTesting(){
        User newUser = createNewUser();
        Response createNewUser = RestHelper.postUsers(qaUser,newUser);
        assertNotEquals("Status code is not correct on existing user '"+newUser.username+"' creation " + createNewUser.asString(), 201, createNewUser.statusCode());
    }

    @Test
    public void newUserGetEmptyEmailsListTesting(){
        User newUser = createNewUser();
        Emails emails = RestHelper.getEmails(newUser);
        assertEquals("There are some emails exist for new user", 0, emails.count);
        assertEquals("There are some emails exist for new user", 0, emails.results.size());
    }

    @Test
    public void userGetsNotEmptyEmailsTesting(){
        User sender = createNewUser();
        User receipient = qaUser;

        Email email1 = RestHelper.postEmails(sender,receipient);
        assertEquals("Sender is not correct in the sent email", sender.id, email1.sender);
        assertEquals("Recipient is not correct in the sent email", receipient.email, email1.recipient);

        Email email2 = RestHelper.postEmails(sender,receipient);
        assertNotEquals("Emails have similar IDs", email1.id, email2.id);

        Emails emails = RestHelper.getEmails(sender);
        assertEquals("Number of emails is not correct", 2, emails.count);
        assertEquals("ArrayList size is not correct", 2, emails.results.size());
        assertEquals("Email1 is not correct", email1, emails.results.get(0));
        assertEquals("Email2 is not correct", email2, emails.results.get(1));
    }

    @Test
    public void userGetsEmailByIdTesting(){
        User sender = createNewUser();
        User receipient = qaUser;
        Email emailSent = RestHelper.postEmails(sender,receipient);
        assertEquals("Sender is not correct in the sent email", sender.id, emailSent.sender);
        assertEquals("Recipient is not correct in the sent email", receipient.email, emailSent.recipient);

        Email emailReceived = RestHelper.getEmail(sender,emailSent.id);
        assertEquals("EmailReceived is not correct", emailReceived, emailSent);
    }

    @Test
    public void userDeletesEmailByIdTesting(){
        User sender = createNewUser();
        User receipient = qaUser;

        Email emailSent1 = RestHelper.postEmails(sender,receipient);
        assertEquals("Sender is not correct in the sent email", sender.id, emailSent1.sender);

        Email emailSent2 = RestHelper.postEmails(sender,receipient);
        assertEquals("Sender is not correct in the sent email", sender.id, emailSent2.sender);

        Emails emails = RestHelper.getEmails(sender);
        assertEquals("Number of emails is not correct",2, emails.count);
        assertEquals("ArrayList size is not correct",2, emails.results.size());
        assertEquals("Email1 is not correct", emailSent1, emails.results.get(0));

        assertEquals("Status code is not correct on email deleting",204, RestHelper.deleteEmail(sender, emailSent1.id).statusCode());

        emails = RestHelper.getEmails(sender);
        assertEquals("Number of emails is not correct",1, emails.count);
        assertEquals("ArrayList size is not correct",1, emails.results.size());
        assertEquals("Email1 is not correct", emailSent2, emails.results.get(0));

        assertEquals("Status code is not correct on not existing email deleting",404, RestHelper.deleteEmail(sender, emailSent1.id).statusCode());
    }
}
