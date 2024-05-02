package edu.bu.met.cs665.Library;

import com.mongodb.client.FindIterable;
import edu.bu.met.cs665.repository.BooksRepository;
import org.bson.Document;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BooksManager {

    String bookName, author;
    int pages, publicationYear, noOfCopies;
    long ISBN;


    public BooksManager(String bookName, String author, int pages, int publicationYear, long ISBN, int noOfCopies) {
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
            scanner.nextLine();                         //added to consume newline character

            System.out.print("\nAuthor: ");
            author = scanner.nextLine();


            System.out.print("\nYear of Publiction: ");
            publicationYear = scanner.nextInt();

            System.out.print("\nNo of pages: ");
            pages = scanner.nextInt();

            System.out.print("\nNo of Copies: ");
            noOfCopies = scanner.nextInt();


            //document of type BSON
            Document doc = new Document("Book Name", bookName).append("ISBN", ISBN).append("Author", author).append("Year of Publiction", publicationYear).append("No of Pages", pages).append("No of Copies", noOfCopies);

            BooksRepository booksRepository = new BooksRepository();
            booksRepository.insertOne(doc);
        } catch (Exception e) {
            System.err.print(e);
//            return 0;
        }

    }

    public void removeBook() {
        int choice = findBookMenu();
        BooksRepository booksRepository = new BooksRepository();
        Scanner scanner = new Scanner(System.in);
        switch (choice) {
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

    public Document findBook() {
        int choice = findBookMenu();
        BooksRepository booksRepository = new BooksRepository();
        Scanner scanner = new Scanner(System.in);
        Document document = null;

        switch (choice) {
            case 1:
                System.out.print("\nBook Name: ");
                document = booksRepository.search(scanner.nextLine());
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
     *
     * @param ISBN
     * @return Document
     */
    public Document findBook(long ISBN) {
        BooksRepository booksRepository = new BooksRepository();
        return booksRepository.search(ISBN);
    }

    private int findBookMenu() {
        Scanner sc = null;
        int choice = 0;
        try {
            sc = new Scanner(System.in);
            do {
                System.out.println("Find book by\n1)Book Name\n2)ISBN\nchoice: ");
                choice = sc.nextInt();
            } while (choice != 1 && choice != 2);
        } catch (InputMismatchException e) {
            System.out.println("Please input a valid number!!!");
        } catch (Exception e) {
            System.err.print(e);
        }

        return choice;
    }

    public void updateBook(Document filter, Document update) {
        System.out.println(filter);
        System.out.println(update);
        BooksRepository booksRepository = new BooksRepository();
        booksRepository.updateOne(filter, update);
    }

    public void displayBookManagerMenu() {
        Scanner scanner = null;
        int choice = 1;
        while (choice != 0) {
            try {
                scanner = new Scanner(System.in);
                System.out.println("\n\nMENU\n1)Add book in invertory\n2)Remove book from inventory\n3)View all books in inventory\n4)View book\nAny other number to go back");
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        removeBook();
                        break;
                    case 3:
                        String bookName, author;
                        long ISBN;
                        int yearOfPublication, noOfPages;
                        BooksRepository booksRepository = new BooksRepository();
                        FindIterable<Document> records = booksRepository.search();

                        System.out.println("----------------------------------------------------------------------------------------------------------");
                        System.out.printf("| %-20s | %-10s | %-10s | %-30s | %-20s |\n", "ISBN", "Year", "Pages", "Book Name", "Author");
                        System.out.println("----------------------------------------------------------------------------------------------------------");

                        for (Document record : records) {
                            ISBN = (long) record.get("ISBN");
                            yearOfPublication = (int) record.get("Year of Publiction");
                            noOfPages = (int) record.get("No of Pages");
                            bookName = (String) record.get("Book Name");
                            author = (String) record.get("Author");

                            System.out.printf("| %-20d | %-10d | %-10d | %-30s | %-20s |\n", ISBN, yearOfPublication, noOfPages, bookName, author);
                        }
                        System.out.println("----------------------------------------------------------------------------------------------------------");

                        break;
                    case 4:
                        Document document = findBook();
                        if (document == null) System.out.println("Book not found!!!");
                        else System.out.println(document);
                        break;
                    default:
                        choice = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input !!!");
                LogsManager.log("Exception in LoanManager Class - " + e);
            } catch (Exception e) {
                LogsManager.log("Exception in LoanManager Class - " + e);
                System.err.println(e);
            }
        }
    }
}
