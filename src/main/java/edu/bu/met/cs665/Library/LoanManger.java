package edu.bu.met.cs665.Library;

import edu.bu.met.cs665.repository.LoanRepository;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LoanManger {

    long ISBN;
    String ID, bookName;
    Date date;

    public LoanManger() {

    }

    public LoanManger(long ISBN, String ID, String bookName, Date date) {
        this.ISBN = ISBN;
        this.ID = ID;
        this.date = date;
        this.bookName = bookName;
    }

    /**
     * method to loan books
     */
    public void loanBook() {
        Scanner scanner = new Scanner(System.in);
        BooksManager booksManager = new BooksManager();
        MembersManager membersManager = new MembersManager();
        LoanRepository loanRepository = new LoanRepository();

        try {
            System.out.println("ID: ");
            ID = scanner.next();
            System.out.println("ISBN: ");
            ISBN = scanner.nextLong();

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String formattedDate = dateFormat.format(currentDate);

            Document bookRecord = booksManager.findBook(ISBN);
            Document memberRecord = membersManager.findMember(ID);

            if (bookRecord == null){                                                //book not found in DB
                System.out.println("Book does not exist in the repository");
            }
            if(memberRecord == null){                                               //member not found in DB
                System.out.println("Member does not exist in the repository");
            }
            else {                                                                  //issuing the book
                System.out.println(bookRecord);
                System.out.println(memberRecord);
                Document document = new Document("ISBN", ISBN).append("ID", ID).append("Date", formattedDate);
                int noOfCopies = (int) bookRecord.get("No of Copies");
                ArrayList<String> booksIssued = new ArrayList<>() ;// = (ArrayList<String>) memberRecord.get("Books Issued");
                if(memberRecord.get("Books Issued")!=null){
                    booksIssued = (ArrayList<String>) memberRecord.get("Books Issued");
                }



                booksIssued.add(bookRecord.get("Book Name").toString());

                booksManager.updateBook(new Document("ISBN",ISBN),new Document("$set",new Document("No of Copies",--noOfCopies)));
                membersManager.updateMember(new Document("ID",ID),new Document("$set",new Document("Books Issued",booksIssued)));

                System.out.println(booksIssued);

                loanRepository.insertOne(document);

            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }


    /**
     * method to return books
     */
    public void returnBook() {
    }

    /**
     * method to view loaned books
     */
    public void viewLoanedBooks() {
    }
}
