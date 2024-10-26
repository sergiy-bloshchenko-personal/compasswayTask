package org.example.objects;

import java.util.Objects;

public class Email {
    public int id;
    public int sender;
    public String recipient;
    public String subject;
    public String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return id == email.id && sender == email.sender && Objects.equals(recipient, email.recipient) && Objects.equals(subject, email.subject) && Objects.equals(message, email.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, recipient, subject, message);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id + ", " +
                "sender=" + sender + ", " +
                "recipient='" + recipient + "', " +
                "subject='" + subject + "', " +
                "message='" + message + "'}";
    }
}
