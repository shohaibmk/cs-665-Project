package edu.bu.met.cs665;

import edu.bu.met.cs665.Library.LoanManager;
import edu.bu.met.cs665.repository.LoanRepository;
import org.bson.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class LoanManagerTest {

    /**
     * test method to test loan books
     * make sure user ID:2610,ISBN:1234 exists in Loan collection
     */
    @Test
    public void testLoanBook() {
        String input = "2610\n1234\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LoanManager loanManager = new LoanManager();
        loanManager.loanBook();

        LoanRepository loanRepository = new LoanRepository();
        Document filter = new Document("ID", "2610").append("ISBN", 1234);
        Document record = loanRepository.search(filter);


        assertNotNull(record);
    }

    /**
     * test method to test loan books
     * make sure user ID:26101 does not exist in Members collection
     */
    @Test
    public void testLoanBookFailed() {
        String input = "2610\n1234\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LoanManager loanManager = new LoanManager();
        loanManager.loanBook();

        LoanRepository loanRepository = new LoanRepository();
        Document filter = new Document("ID", "26101").append("ISBN", 1234);
        Document record = loanRepository.search(filter);


        assertNull(record);
    }

    /**
     * method to test return book method
     * make sure the Loan collection has a record present for ISBN:1234 and ID:2610
     */
    @Test
    public void testReturnBook() {
        String input = "2610\n1234\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        LoanManager manager = new LoanManager();
        manager.returnBook();

        LoanRepository loanRepository = new LoanRepository();
        Document filter = new Document("ID", "26101").append("ISBN", 1234);
        Document record = loanRepository.search(filter);

        assertNull(record);
    }

}
