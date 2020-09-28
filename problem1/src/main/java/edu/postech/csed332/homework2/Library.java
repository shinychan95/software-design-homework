package edu.postech.csed332.homework2;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A container class for all collections (that eventually contain all
 * books). A library can be exported to or imported from a JSON file.
 */
public final class Library {
    private List<Collection> collections;

    /**
     * Builds a new, empty library.
     */
    public Library() {
        // TODO implement this
        collections = new ArrayList<>();
    }

    /**
     * Builds a new library and restores its contents from a file.
     *
     * @param fileName the file from where to restore the library.
     */
    public Library(String fileName) {
        // TODO implement this
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            collections = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while(line != null) {
                stringBuilder.append(line).append('\n');
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            JSONObject obj = new JSONObject(stringBuilder.toString());
            for (Object o : obj.getJSONArray("collections")) {
                Collection c = Collection.restoreCollection(o.toString());
                collections.add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the contents of the library to the given file.
     *
     * @param fileName the file where to save the library
     */
    public void saveLibraryToFile(String fileName) {
        // TODO implement this
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        for (Collection c : collections) {
            arr.put(c.getJsonRepresentation());
        }

        obj.put("collections", arr);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the set of all books that belong to the collections
     * of a given name. Note that different collections may have the
     * same name. Return null if there is no collection of the
     * given name, and the empty set of there are such collections but
     * all of them are empty. For example, suppose that
     * - "Computer Science" is a top collection.
     * - "Operating Systems" is a collection under "Computer Science".
     * - "Linux Kernel" is a book under "Operating System".
     * - "Software Engineering" is a collection under "Computer Science".
     * - "Software Design Methods" is a book under "Software Engineering".
     * Then, the findBooks method for "Computer Science" returns the set
     * of two books "Linux Kernel" and "Software Design Methods".
     *
     * @param collection a collection name
     * @return a set of books
     */
    public Set<Book> findBooks(String collection) {
        // TODO implement this
        Set<Book> b = new HashSet<>();

        // TODO - 아니 왜 String 비교 연산 때때로 다르지...
        for (Collection c : collections) {
            if (c.getName().equals(collection)) {
                List<Element> check = new ArrayList<>();
                List<Element> next = new ArrayList<>();

                check.add(c);

                while (!check.isEmpty()) {
                    for (Element e1 : check) {
                        for (Element e2 : ((Collection) e1).getElements()) {
                            if (e2 instanceof Book) {
                                b.add((Book) e2);
                            } else {
                                next.add(e2);
                            }
                        }
                    }
                    check.clear();
                    for (Element e : next) {
                        check.add(e);
                    }
                    next.clear();
                }
                return b;
            }
        }
        return null;
    }

    /**
     * Return the set of all books written by a given author in this
     * collection (including the sub-collections). Return the empty
     * set if there is no book written by the author. Note that a book
     * may involve multiple authors.
     *
     * @param author an author
     * @return Return the set of books written by the given author
     */
    public Set<Book> findBooksByAuthor(String author) {
        // TODO implement this
        Set<Book> b = new HashSet<>();

        for (Collection c: collections) {
            for (Element e : c.getElements()) {
                if (e instanceof Book) {
                    if (((Book) e).getAuthors().contains(author)) {
                        b.add((Book) e);
                    }
                }
            }
        }
        return b;
    }

    /**
     * Returns the collections contained in the library.
     *
     * @return library contained elements
     */
    public List<Collection> getCollections() {
        return collections;
    }
}
