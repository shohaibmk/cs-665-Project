package edu.bu.met.cs665.Library;

import edu.bu.met.cs665.repository.BooksRepository;
import org.bson.Document;

import java.util.Scanner;

public class BooksManager {

    String bookName,author;
    int pages,publicationYear,noOfCopies;
    long ISBN;


    public BooksManager(String bookName, String author, int pages, int publicationYear, long ISBN,int noOfCopies){
        this.bookName = bookName;
        this.ISBN = ISBN;
        this.author = author;
        this.pages = pages;
        this.publicationYear = publicationYear;
        this.noOfCopies = noOfCopies;
    }

    public BooksManager() {

    }

    /**
     * method to add Books to the inventory
     */
    public void addBook() {

        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);

            System.out.print("\nBook Name: ");
            bookName = scanner.nextLine();

            System.out.print("\nISBN: ");
            ISBN = scanner.nextLong();

            System.out.print("\nAuthor: ");
            author = scanner.nextLine();

            System.out.print("\nYear of Publiction: ");
            publicationYear = scanner.nextInt();

            System.out.print("\nNo of pages: ");
            pages = scanner.nextInt();

            System.out.print("\nNo of Copies: ");
            noOfCopies = scanner.nextInt();


            //document of type BSON
            Document doc = new Document("Book Name", bookName).append("ISBN", ISBN).append("Author", author).append("Year of Publiction", publicationYear).append("No of Pages", pages).append("No of Copies",noOfCopies);

            BooksRepository booksRepository = new BooksRepository();
            booksRepository.insertOne(doc);
        }
        catch (Exception e){
            System.err.print(e);
//            return 0;
        }

    }

    public void removeBook(){
        int choice = bookMenu();
        BooksRepository booksRepository = new BooksRepository();
        Scanner scanner = new Scanner(System.in);
        switch (choice){
            case 1:
                System.out.print("\nBook Name: ");
                booksRepository.deleteOne(scanner.next());
                break;
            case 2:
                System.out.print("\nISBN: ");
                booksRepository.deleteOne(scanner.nextLong());
                break;
        }
    }

    public Document findBook(){
        int choice = bookMenu();
        BooksRepository booksRepository = new BooksRepository();
        Scanner scanner = new Scanner(System.in);
        Document document = null;

        switch (choice){
            case 1:
                System.out.print("\nBook Name: ");
                document = booksRepository.search(scanner.next());
                break;
            case 2:
                System.out.print("\nISBN: ");
                document = booksRepository.search(scanner.nextLong());
                break;
        }
        return document;
    }

    /**
     * mthod to find book in the DB
     * @param ISBN
     * @return Document
     */
    public Document findBook(long ISBN){
        BooksRepository booksRepository = new BooksRepository();
        Document bookRecord = booksRepository.search(ISBN);
        return bookRecord;
    }

    private int bookMenu() {
        Scanner sc = null;
        int choice = 0;
        try {
            sc = new Scanner(System.in);
            do {
                System.out.println("Find book by\n1)Book Name\n2)ISBN\nchoice: ");
                choice = sc.nextInt();
            } while (choice != 1 && choice != 2);
        }
        catch (Exception e) {
            System.err.print(e);
        }

        return choice;
    }

    public void updateBook(Document filter,Document update){
        System.out.println(filter);
        System.out.println(update);
        BooksRepository booksRepository = new BooksRepository();
        booksRepository.updateOne(filter,update);
    }



}
