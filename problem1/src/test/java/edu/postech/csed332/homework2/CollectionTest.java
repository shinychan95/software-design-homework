package edu.postech.csed332.homework2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class CollectionTest {

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Collection.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */

    @Test
    public void testStringRepresentation() {
        Collection c1 = new Collection("computer");
        Collection c2 = new Collection("Software");
        Collection c3 = new Collection("design");
        Book b = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        c1.addElement(c2);
        c2.addElement(b);
        c2.addElement(c3);
        Assertions.assertEquals(
                c1.getStringRepresentation(),
                "{\"elements\":[{\"elements\":[{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}," +
                        "{\"elements\":[],\"name\":\"design\"}],\"name\":\"Software\"}],\"name\":\"computer\"}"
        );
    }

    @Test
    public void testJsonRepresentation() throws IOException {
        Collection c1 = new Collection("computer");
        Book b = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        c1.addElement(b);
        JSONObject jsonC1 = new JSONObject();
        jsonC1.put("name", "computer");
        JSONArray jsonE = new JSONArray();
        JSONObject jsonB = new JSONObject("{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        jsonE.put(jsonB);
        jsonC1.put("elements", jsonE);

        ObjectMapper mapper = new ObjectMapper();

        Assertions.assertEquals(mapper.readTree(c1.getJsonRepresentation().toString()),
                mapper.readTree(jsonC1.toString()));
    }

    @Test
    public void testGetElements(){
        Collection c1 = new Collection("collection one");
        Collection c2 = new Collection("collection two");
        Book b = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        c1.addElement(c2);
        c1.addElement(b);
        Assertions.assertEquals(c1.getElements(), Arrays.asList(c2, b));
    }


    @Test
    public void testAddElement(){
        Collection c1 = new Collection("collection one");
        Assertions.assertEquals(c1.getElements().size(), 0);

        Book b = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        c1.addElement(b);
        Assertions.assertEquals(c1.getElements().size(), 1);
        Assertions.assertEquals(((Book) (c1.getElements().get(0))).getTitle(), "Unit Testing");

    }

    @Test
    public void testDeleteElement(){
        Collection c1 = new Collection("collection one");
        Book b1 = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Book b2 = new Book("Unit Testing2", Arrays.asList("Name 3", "Name 4"));
        c1.addElement(b1);
        c1.addElement(b2);
        Assertions.assertEquals(c1.getElements().size(), 2);
        c1.deleteElement(b1);
        Assertions.assertEquals(c1.getElements().size(), 1);
    }


    @Test
    public void testRestoreCollection(){
        Collection c1 = new Collection("collection one");
        Collection c2 = new Collection("collection two");
        Collection c3 = new Collection("collection three");
        c1.addElement(c2);
        c1.addElement(c3);
        Book b1 = new Book("b1", Arrays.asList("Name 1", "Name 2"));
        Book b2 = new Book("b2", Arrays.asList("Name 1", "Name 2"));
        c1.addElement(b1);
        c2.addElement(b2);

        String stringRepr = c1.getStringRepresentation();
        Collection restored = Collection.restoreCollection(stringRepr);
        Assertions.assertEquals(restored.getName(), "collection one");
        Assertions.assertEquals(((Book)(restored.getElements().get(2))).getTitle(), "b1");
        Assertions.assertEquals(((Collection)(restored.getElements().get(1))).getName(), "collection three");
    }
}
