package org.example.objects;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.helpers.RestHelper;

import java.util.Base64;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class User {
    public int id;
    public String username;
    public String email;
    public String password;

    public User() {
        this.username = "sergiyb_" + RandomStringUtils.randomAlphabetic(10).toLowerCase();
        this.password = RandomStringUtils.randomAlphabetic(10);
        this.email = username+"@example.com";
    }

    public User(String username) {
        this.username = username;
        this.password = RandomStringUtils.randomAlphabetic(10);
        this.email = username+"@example.com";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = username+"@example.com";
    }

    public String getAuthCode(){
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public static User getQaUser(){
        return RestHelper.getUsersCurrent(new User("qa_test_user", "QM8dvwgR457D4NL"));
    }

    public static User createNewUser(){
        User newUser = new User();
        Response createNewUser = RestHelper.postUsers(getQaUser(),newUser);
        assertEquals("Status code is not correct on user '"+newUser.username+"' creation " + createNewUser.asString(), 201, createNewUser.statusCode());
        User new_user = createNewUser.as(User.class);
        new_user.password = newUser.password;
        return new_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + ", " +
                "username='" + username + "', " +
                "email='" + email + "', " +
                "password='" + password + "'}";
    }
}
