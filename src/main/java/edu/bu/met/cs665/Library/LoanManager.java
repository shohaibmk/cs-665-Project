package edu.bu.met.cs665.Library;

import com.mongodb.client.FindIterable;
import edu.bu.met.cs665.repository.LoanRepository;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LoanManager {

    long ISBN;
    String ID, bookName;
    Date date;

    public LoanManager() {

    }

    public LoanManager(long ISBN, String ID, String bookName, Date date) {
        this.ISBN = ISBN;
        this.ID = ID;
        this.date = date;
        this.bookName = bookName;
    }

    /**
     * method to loan books
     */
    public void loanBook() {
        LogsManager.log("Request to loan book created");
        Scanner scanner = new Scanner(System.in);
        BooksManager booksManager = new BooksManager();
        MembersManager membersManager = new MembersManager();
        LoanRepository loanRepository = new LoanRepository();

        try {
            System.out.print("\nID: ");
            ID = scanner.next();
            System.out.print("\nISBN: ");
            ISBN = scanner.nextLong();


            Document bookRecord = booksManager.findBook(ISBN);
            Document memberRecord = membersManager.findMember(ID);

            if (bookRecord == null) {                                                //book not found in DB
                System.out.println("Book does not exist in the repository");
                LogsManager.log("Loan failed - Book does not exist in the repository");
            }
            if (memberRecord == null) {                                               //member not found in DB
                System.out.println("Member does not exist in the repository");
                LogsManager.log("Loan failed - Member does not exist in the repository");
            } else if (bookRecord != null && memberRecord != null) {                                                                  //issuing the book
                ArrayList<String> booksIssued = new ArrayList<>();

                int noOfCopies = (int) bookRecord.get("No of Copies");
                if (memberRecord.get("Books Issued") != null) {                                 //If memeber has books issued
                    booksIssued = (ArrayList<String>) memberRecord.get("Books Issued");
                }
                if (noOfCopies == 0) {                                                        //book not in inventory
                    System.out.println("Book cannot be issued, no books in the inventory!!!");
                    LogsManager.log("Loan failed - No books in the inventory");
                } else {
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String formattedDate = dateFormat.format(currentDate);
                    Document document = new Document("ISBN", ISBN).append("ID", ID).append("Date", formattedDate);

                    booksIssued.add(bookRecord.get("Book Name").toString());

                    booksManager.updateBook(new Document("ISBN", ISBN), new Document("$set", new Document("No of Copies", --noOfCopies)));
                    membersManager.updateMember(new Document("ID", ID), new Document("$set", new Document("Books Issued", booksIssued)));


                    loanRepository.insertOne(document);
                    LogsManager.log("Loan completed successfully - ISBN:" + ISBN + " ID:" + ID);
                }
            }
        } catch (Exception e) {
            System.err.print(e);
            LogsManager.log("Exception in LoanManager Class - " + e);
            LogsManager.log("Loan Failed");
        }
    }


    /**
     * method to return books
     */
    public void returnBook() {
        LogsManager.log("Request to return book created");

        Scanner scanner = new Scanner(System.in);
        BooksManager booksManager = new BooksManager();
        MembersManager membersManager = new MembersManager();
        LoanRepository loanRepository = new LoanRepository();

        try {
            System.out.print("\nID: ");
            ID = scanner.next();
            System.out.print("\nISBN: ");
            ISBN = scanner.nextLong();

            Document filter = new Document("ID", ID).append("ISBN", ISBN);
            Document record = loanRepository.search(filter);

            if (record == null) {                                                                     //record not found
                System.out.println("Record not found !!!");
                LogsManager.log("Return failed - record not found");
            } else {                                                                                  //if record found
                Document member = membersManager.findMember(ID);
                Document book = booksManager.findBook(ISBN);
                ArrayList<String> issuedBooksList = (ArrayList<String>) member.get("Books Issued");
                String bookName = book.get("Book Name").toString();
                int noOfCopies = (int) book.get("No of Copies");

                issuedBooksList.remove(bookName);


                booksManager.updateBook(new Document("ISBN", ISBN), new Document("$set", new Document("No of Copies", ++noOfCopies)));

                if (issuedBooksList.isEmpty()) {
                    membersManager.updateMember(new Document("ID", ID), new Document("$set", new Document("Books Issued", null)));
                } else
                    membersManager.updateMember(new Document("ID", ID), new Document("$set", new Document("Books Issued", issuedBooksList)));


                loanRepository.deleteOne(filter);
                System.out.println("Return Complete");
                LogsManager.log("Return completed successfully");
            }

        } catch (Exception e) {
            System.err.print(e);
            LogsManager.log("Exception in LoanManager Class - "+e);
            LogsManager.log("Return Failed");

        }
    }

    private Document findRecord() {
        Document record = null;
        return record;
    }

    /**
     * method to view loaned books
     */
    public void viewLoanedBooks() {
        LoanRepository loanRepository = new LoanRepository();
        LogsManager.log("Viewing loan records");
        LogsManager.log("Retrieving loan records");
        try {

            FindIterable<Document> records = loanRepository.search();
            long ISBN;
            String ID, date;

            System.out.println("-------------------------------------------------------");

            System.out.printf("| %-20s | %-15s | %-10s |\n", "Date", "ISBN", "ID");
            System.out.println("-------------------------------------------------------");
            for (Document record : records) {
                ISBN = (long) record.get("ISBN");
                ID = (String) record.get("ID");
                date = record.get("Date").toString();

                System.out.printf("| %-20s | %-15d | %-10s |\n", date, ISBN, ID);
            }
            System.out.println("-------------------------------------------------------");

            LogsManager.log("loan records retrieved and displayed successfully");

        } catch (Exception e) {
            LogsManager.log("Exception in LoanManager Class - " + e);
            LogsManager.log("Retrieving loan records failed");

        }
    }

    public void displayLoanMenu() {

        Scanner scanner = null;
        int choice = 1;
        while (choice != 0) {
            try {
                scanner = new Scanner(System.in);
                System.out.println("\n\nMENU\n1)Loan Book\n2)View Loaned Books\nAny other number to go back");
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        loanBook();
                        break;
                    case 2:
                        viewLoanedBooks();
                        break;
                    default:
                        choice = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input !!!");
                LogsManager.log("Exception in LoanManager Class - " + e);
            } catch (Exception e) {
                LogsManager.log("Exception in LoanManager Class - " + e);
            }
        }
    }

    public void displayReturnMenu() {
        Scanner scanner = null;
        int choice = 1;
        while (choice != 0) {
            try {
                scanner = new Scanner(System.in);
                System.out.println("\n\nMENU\n1)Return Book\n2)View Loaned Books\nAny other number to go back");
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        returnBook();
                        break;
                    case 2:
                        viewLoanedBooks();
                        break;
                    default:
                        choice = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input !!!");
                LogsManager.log("Exception in LoanManager Class - " + e);
            } catch (Exception e) {
                LogsManager.log("Exception in LoanManager Class - " + e);
            }
        }
    }
}
