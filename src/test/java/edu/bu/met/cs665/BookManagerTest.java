package edu.bu.met.cs665;

import edu.bu.met.cs665.Library.BooksManager;
import org.bson.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BookManagerTest {


    /**
     * method to test addbook method
     */
    @Test
    public void addBookTest() {
        String input = "One Hundred Years of Solitude\n9780065023961\nGabriel García Márquez\n1998\n417\n9";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        BooksManager booksManager = new BooksManager();
        booksManager.addBook();

        long ISBN = 9780065023961L;
        Document record = booksManager.findBook(ISBN);

        assertNotNull(record);

    }

    /**
     * method to test removeBookByISBN method
     */
    @Test
    public void removeBookByISBNTest() {
//        String input = "9780065023961L";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
        long ISBN = 9780065023961L;

        BooksManager booksManager = new BooksManager();
        booksManager.removeBookByISBN(ISBN);

        Document record = booksManager.findBook(ISBN);

        assertNull(record);
    }

    /**
     * method to test removeBookByName method
     */
    @Test
    public void removeBookByNameTest() {

        String input = "One Hundred Years of Solitude\n9780065023961\nGabriel García Márquez\n1998\n417\n9";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        BooksManager booksManager = new BooksManager();
        booksManager.addBook();

        String bookName = "One Hundred Years of Solitude";

        booksManager.removeBookByName(bookName);

        Document record = booksManager.findBook(bookName);

        assertNull(record);
    }


}
