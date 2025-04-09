package edu.phystech.hw2.contact;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class ContactTest {

    @Test
    public void justWorks() {

        Assertions.assertDoesNotThrow(() -> {
            Contact first = new Contact("username", "username@gmail.com");
            Contact second = new Contact("123", "123@gmail.com");

            Contact withoutEmail = new Contact("withoutEmail");
            Assertions.assertEquals(Contact.UNKNOWN_EMAIL, withoutEmail.email());
        });

    }

    @Test
    public void validationTest() {
        var exception =
                Assertions.assertThrows(InvalidContactFieldException.class, () -> new Contact("   ", "123@gmai.com"));
        Assertions.assertEquals("username", exception.getFieldName());
        exception =
                Assertions.assertThrows(InvalidContactFieldException.class, () -> new Contact("   1", "123@mai.ru"));
        Assertions.assertEquals("email", exception.getFieldName());
        exception = Assertions.assertThrows(InvalidContactFieldException.class, () -> new Contact("   ", ""));
        Assertions.assertEquals("username", exception.getFieldName());
    }

    @Test
    public void compareTest() {
        var result = Stream.of(new Contact("AFD"), new Contact("a"), new Contact("zZ")).sorted(Contact::compareTo)
                .map(Contact::username).toList();
        Assertions.assertEquals(List.of("a", "zZ", "AFD"), result);
        Assertions.assertInstanceOf(Comparable.class, new Contact("AFD"));
    }
}

record Contact(String username, String email) implements Comparable<Contact> {
    public static final String UNKNOWN_EMAIL = "UNKNOWN";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@gmail\\.com$");

    public Contact {
        if (username == null || username.isBlank()) {
            throw new InvalidContactFieldException("username", "ничего из параметров не должно быть пустым");
        }

        if (email == null || email.isBlank()) {
            email = UNKNOWN_EMAIL;
        } else {
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new InvalidContactFieldException("email", "почта может заканчиваться только на @gmail.com");
            }
        }
    }

    public Contact(String username) {
        this(username, UNKNOWN_EMAIL);
    }

    @Override
    public int compareTo(Contact other) {
        Objects.requireNonNull(other);
        return Integer.compare(this.username.length(), other.username.length());
    }
}
//record Contact(String username, String email){
//    Contact {
//        if (username.isEmpty()) {
//            username = "Oleg";
//        }
//        if (email.isEmpty()) {
//            email = username + "@gmail.com";
//        }
//    }



