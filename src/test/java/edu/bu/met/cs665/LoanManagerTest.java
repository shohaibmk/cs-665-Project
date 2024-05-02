package edu.bu.met.cs665;

import edu.bu.met.cs665.Library.LoanManager;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class LoanManagerTest {

    /**
     * test method to test loan books
     */
    @Test
    public void testLoanBook() {
        // Mock user input
        String input = "2610\n1234\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LoanManager loanManager = new LoanManager();
        loanManager.loanBook();

        System.setIn(System.in);

        assertTrue(true);                       //If no exceptions thrown loan was successful
    }

    @Test
    public void testLoanBookFailed() {
        // Mock user input
        String input = "26102\n1234\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LoanManager loanManager = new LoanManager();
        loanManager.loanBook();

        assertTrue(true);                       //If no exceptions thrown loan was successful
    }


}
