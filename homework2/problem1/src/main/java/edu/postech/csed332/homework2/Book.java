package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A book contains the title and the author(s), each of which is
 * represented as a string. A book can be exported to and import
 * from a string representation in JSON (https://www.json.org/).
 * Using the string, you should be able to construct a book object.
 */
public final class Book extends Element {
    private String title;
    private List<String> authors;

    /**
     * Builds a book with the given title and author(s).
     *
     * @param title   the title of the book
     * @param authors the author(s) of the book
     */
    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
        // TODO write more code if necessary
        this.setParentCollection(null);
    }

    /**
     * Builds a book from the string representation in JSON, which
     * contains the title and author(s) of the book.
     *
     * @param stringRepr the JSON string representation
     */
    public Book(String stringRepr) {
        // TODO implement this
        JSONObject book = new JSONObject(stringRepr);
        title = book.getString("title");

        JSONArray arr = book.getJSONArray("authors");
        List<String> list = new ArrayList<>();

        for (Object s : arr) {
            list.add((String) s);
        }
        authors = list;

        this.setParentCollection(null);
    }

    /**
     * Returns the JSON string representation of this book. The string
     * representation contains the title and author(s) of the book.
     *
     * @return the string representation
     */
    public String getStringRepresentation() {
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("authors", authors);
        return obj.toString();
    }

    public JSONObject getJsonRepresentation() {
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("authors", authors);
        return obj;
    }

    /**
     * Returns all the collections that this book belongs to directly
     * and indirectly, starting from the bottom-level collection.
     * <p>
     * For example, suppose that "Computer Science" is a top collection,
     * "Operating Systems" is a collection under "Computer Science", and
     * "Linux Kernel" is a book under "Operating System". Then, this
     * method for "The Linux Kernel" returns the list of the collections
     * (Operating System, Computer Science), starting from the bottom.
     *
     * @return the list of collections
     */
    public List<Collection> getContainingCollections() {
        // TODO implement this
        List<Collection> containingCollections = new ArrayList<>();
        Collection c = this.getParentCollection();
        while (c != null) {
            containingCollections.add(c);
            c = c.getParentCollection();
        }
        return containingCollections;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author(s) of the book.
     *
     * @return the author(s)
     */
    public List<String> getAuthors() {
        return authors;
    }
}