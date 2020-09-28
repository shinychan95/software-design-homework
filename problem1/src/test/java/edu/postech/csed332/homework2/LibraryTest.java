package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

public class LibraryTest {

    @BeforeEach
    void setup() {
        Library library = new Library();
        Collection c1 = new Collection("collection one");
        Collection c2 = new Collection("collection two");
        Collection c3 = new Collection("collection three");
        Collection c4 = new Collection("collection four");
        c1.addElement(c3);
        c1.addElement(c4);
        Book b1 = new Book("b1", Arrays.asList("Name 1", "Name 2"));
        Book b2 = new Book("b2", Arrays.asList("Name 1", "Name 3"));
        c1.addElement(b1);
        c3.addElement(b2);
        library.getCollections().add(c1);
        library.getCollections().add(c2);
        library.getCollections().add(c3);
        library.getCollections().add(c4);

        library.saveLibraryToFile("test.json");
    }

    @Test
    public void testfindBooksNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooks("any"));
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Library.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */


    @Test
    public void testSaveLibraryToFile(){
        File f = new File("test.json");
        Assertions.assertEquals(f.exists(), true);
    }

    @Test
    public void testCreateLibraryByFilename(){
        Library library = new Library("test.json");
        List<Collection> c = library.getCollections();
        Assertions.assertEquals(c.size(), 4);
        Assertions.assertEquals(c.get(0).getElements().size(), 3);
        Assertions.assertEquals(c.get(0).getElements().get(2) instanceof Book, true);
    }

    @Test
    public void testFindBook(){
        Library Library = new Library("test.json");
        Set<Book> books = Library.findBooks("collection one");
        Assertions.assertEquals(books.size(), 2);
    }

    @Test
    public void testFindBookByAuthor(){
        Library Library = new Library("test.json");
        Set<Book> books = Library.findBooksByAuthor("Name 1");
        Assertions.assertEquals(books.size(), 2);
        books = Library.findBooksByAuthor("Name 3");
        Assertions.assertEquals(books.size(), 1);
    }
}
