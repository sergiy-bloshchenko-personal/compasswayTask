package org.example.helpers;

import io.restassured.response.Response;
import org.example.objects.Email;
import org.example.objects.Emails;
import org.example.objects.User;
import java.util.*;
import static io.restassured.RestAssured.with;

public class RestHelper {

    private static String API_URL = "http://68.183.74.14:4005/api/";

    private static Map<String,String> getHeaders(String authCode){
        return Map.of(
                "accept","application/json",
                "content-type","application/json",
                "Authorization","Basic " + authCode);
    }

    public static Response postUsers(User userCreator, User userCreated) {
        Map<String,String> body = Map.of(
                "username",userCreated.username,
                "email",userCreated.email,
                "password", userCreated.password);

        return with().relaxedHTTPSValidation()
                .baseUri(API_URL)
                .basePath("users/")
                .headers(getHeaders(userCreator.getAuthCode()))
                .body(body)
                .when()
                .post();
    }

    public static User getUsersCurrent(User user){
        User currentUser = with().relaxedHTTPSValidation()
                .baseUri(API_URL)
                .basePath("users/current/")
                .headers(getHeaders(user.getAuthCode()))
                .expect()
                .statusCode(200)
                .when()
                .get()
                .as(User.class);
        if(!currentUser.password.equals(user.password)) currentUser.password = user.password;
        if(!currentUser.email.equals(user.email)) currentUser.email = user.email;
        return currentUser;
    }

    public static Email postEmails(User userSender, User userRecipient){
        String subject = "from "+userSender.username;
        String message = "with heart from "+userSender.username+" to "+userRecipient.username;
        return postEmails(userSender, userRecipient, subject, message);
    }

    public static Email postEmails(User userSender, User userRecipient, String subject, String message){
        Map<String,Object> email = Map.of(
                "sender",userSender.id,
                "recipient",userRecipient.email,
                "subject",subject,
                "message",message);

        return with().relaxedHTTPSValidation()
                .baseUri(API_URL)
                .basePath("emails/")
                .headers(getHeaders(userSender.getAuthCode()))
                .body(email)
                .expect()
                .statusCode(201)
                .when()
                .post()
                .as(Email.class);
    }

    public static Emails getEmails(User user) {
        return with().relaxedHTTPSValidation()
                .baseUri(API_URL)
                .basePath("emails/")
                .headers(getHeaders(user.getAuthCode()))
                .expect()
                .statusCode(200)
                .when()
                .get()
                .as(Emails.class);
    }

    public static Email getEmail(User user, int emailId){
        return with().relaxedHTTPSValidation()
                .baseUri(API_URL)
                .basePath("emails/"+emailId+"/")
                .headers(getHeaders(user.getAuthCode()))
                .expect()
                .statusCode(200)
                .when()
                .get()
                .as(Email.class);
    }

    public static Response deleteEmail(User user, int emailId){
        return with().relaxedHTTPSValidation()
                .baseUri(API_URL)
                .basePath("emails/"+emailId+"/")
                .headers(getHeaders(user.getAuthCode()))
                .when()
                .delete();
    }
}
