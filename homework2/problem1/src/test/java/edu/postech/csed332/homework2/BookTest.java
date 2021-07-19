package edu.postech.csed332.homework2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        System.out.println(book.toString());
        Assertions.assertEquals(book.getTitle(), "Unit Testing");
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Book.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */

    @Test
    public void testStringRepresentation() {
        Book b = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(b.getStringRepresentation(),
                "{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}");
    }

    @Test
    public void testStringReprInput() {
        Book b = new Book("{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        Assertions.assertEquals(b.getTitle(), "Unit Testing");
        Assertions.assertEquals(b.getAuthors(), Arrays.asList("Name 1", "Name 2"));
    }

    @Test
    public void testContainingCollections() {
        Book book = new Book("{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        Collection c1 = new Collection("collection one");
        Collection c2 = new Collection("collection two");
        c1.addElement(book);
        c2.addElement(c1);
        Assertions.assertEquals(book.getContainingCollections(), Arrays.asList(c1, c2));
    }

    @Test
    public void testGetAuthors() {
        Book b1 = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Book b2 = new Book("{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        Assertions.assertEquals(b1.getAuthors(), Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(b2.getAuthors(), Arrays.asList("Name 1", "Name 2"));
    }

    @Test
    public void testJsonRepresentation() throws IOException {
        Book b = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        JSONObject j = new JSONObject("{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}");

        ObjectMapper mapper = new ObjectMapper();

        Assertions.assertEquals(mapper.readTree(b.getJsonRepresentation().toString()), mapper.readTree(j.toString()));
    }
}
