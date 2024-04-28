package edu.bu.met.cs665.Library;

import com.mongodb.client.FindIterable;
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
            System.out.print("\nID: ");
            ID = scanner.next();
            System.out.print("\nISBN: ");
            ISBN = scanner.nextLong();


            Document bookRecord = booksManager.findBook(ISBN);
            Document memberRecord = membersManager.findMember(ID);

            if (bookRecord == null) {                                                //book not found in DB
                System.out.println("Book does not exist in the repository");
            }
            if (memberRecord == null) {                                               //member not found in DB
                System.out.println("Member does not exist in the repository");
            } else {                                                                  //issuing the book
                ArrayList<String> booksIssued = new ArrayList<>();

                System.out.println(bookRecord);
                System.out.println(memberRecord);
                int noOfCopies = (int) bookRecord.get("No of Copies");
                if (memberRecord.get("Books Issued") != null) {                                 //If memeber has books issued
                    booksIssued = (ArrayList<String>) memberRecord.get("Books Issued");
                }
                if (noOfCopies == 0) {                                                        //book not in inventory
                    System.out.println("Book cannot be issued, no books in the inventory!!!");
                } else {
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String formattedDate = dateFormat.format(currentDate);
                    Document document = new Document("ISBN", ISBN).append("ID", ID).append("Date", formattedDate);

                    booksIssued.add(bookRecord.get("Book Name").toString());

                    booksManager.updateBook(new Document("ISBN", ISBN), new Document("$set", new Document("No of Copies", --noOfCopies)));
                    membersManager.updateMember(new Document("ID", ID), new Document("$set", new Document("Books Issued", booksIssued)));

                    System.out.println(booksIssued);

                    loanRepository.insertOne(document);
                }


            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }


    /**
     * method to return books
     */
    public void returnBook() {
        Scanner scanner = new Scanner(System.in);
        BooksManager booksManager = new BooksManager();
        MembersManager membersManager = new MembersManager();
        LoanRepository loanRepository = new LoanRepository();

        try {
            System.out.print("\nID: ");
            ID = scanner.next();
            System.out.print("\nISBN: ");
            ISBN = scanner.nextLong();

            Document filter = new Document("ID",ID).append("ISBN",ISBN);
            Document record = loanRepository.search(filter);
            System.out.println(record);

            if(record == null){                                                                     //record not found
                System.out.println("Record not found !!!");
            }
            else {                                                                                  //if record found
                Document member = membersManager.findMember(ID);
                Document book = booksManager.findBook(ISBN);
                ArrayList<String> issuedBooksList = (ArrayList<String>) member.get("Books Issued");
                String bookName = book.get("Book Name").toString();
                int noOfCopies = (int) book.get("No of Copies");

                issuedBooksList.remove(bookName);


                booksManager.updateBook(new Document("ISBN", ISBN), new Document("$set", new Document("No of Copies", ++noOfCopies)));

                if(issuedBooksList.isEmpty()){
                    membersManager.updateMember(new Document("ID", ID), new Document("$set", new Document("Books Issued", null)));
                }
                else
                    membersManager.updateMember(new Document("ID", ID), new Document("$set", new Document("Books Issued", issuedBooksList)));



                loanRepository.deleteOne(filter);

                System.out.println(issuedBooksList);
                System.out.println(member);
            }

        }
        catch (Exception e){
            System.err.print(e);
        }
    }

    private Document findRecord(){
        Document record = null;



        return record;
    }

    /**
     * method to view loaned books
     */
    public void viewLoanedBooks() {
        LoanRepository loanRepository = new LoanRepository();
        FindIterable<Document> records = loanRepository.search();
        long ISBN;
        String ID,date;

        for (Document record : records) {
            ISBN = (long) record.get("ISBN");
            ID = (String) record.get("ID");
            date =  record.get("Date").toString();
            System.out.print("\nDate:"+date+"\t\t\tISBN:"+ISBN+"\t\t\tID:"+ID);
        }
    }
}
