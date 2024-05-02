package edu.bu.met.cs665.Library;

import com.mongodb.client.FindIterable;
import edu.bu.met.cs665.repository.MembersRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MembersManager {

    String name, ID;
    ArrayList<String> booksIssued;

    public MembersManager(String name, String ID, ArrayList<String> booksIssued) {
        this.name = name;
        this.ID = ID;
        this.booksIssued = booksIssued;
    }

    public MembersManager() {

    }

    /**
     * method to add members
     */
    public void addMemeber() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);

            System.out.print("\nName: ");
            name = scanner.nextLine();

            System.out.print("\nID: ");
            ID = scanner.next();

            booksIssued = null;

            Document doc = new Document("Name", name).append("ID", ID).append("Books Issued", booksIssued);

            MembersRepository membersRepository = new MembersRepository();
            membersRepository.insertOne(doc);
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    /**
     * method to delete members
     */
    public void deleteMember() {
        Document document = findMember();

        if (document == null) {                                   //document not found in DB
            System.out.println("Member not found");
        } else {                                                  //document found in DB
            ArrayList<String> issuedBooksList = (ArrayList<String>) document.get("Books Issued");


            System.out.println(issuedBooksList);
            if (issuedBooksList == null || issuedBooksList.isEmpty()) {
                MembersRepository membersRepository = new MembersRepository();
                membersRepository.deleteOne(document.get("ID").toString());
//                System.out.println(document.get("ID"));
                System.out.println("Deleting");

            } else {
                System.out.println("Member has issued books, so cannot be deleted");
            }

        }
    }

    public Document findMember(String ID) {
        MembersRepository membersRepository = new MembersRepository();
        Document memberRecord = membersRepository.search(ID);
        return memberRecord;
    }

    /**
     * method to find memebers
     */
    public Document findMember() {
//        int choice = memberMenu();
        try {
            Scanner scanner = new Scanner(System.in);
            MembersRepository membersRepository = new MembersRepository();
            Document document = null;

            System.out.print("ID: ");
            document = membersRepository.search(scanner.nextLine());

            return document;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    void updateMember(Document filter, Document update) {
        MembersRepository membersRepository = new MembersRepository();
        membersRepository.updateOne(filter, update);

    }

    public void displayMemberManagerMenu() {
        Scanner scanner = null;
        int choice = 1;
        while (choice != 0) {
            try {
                scanner = new Scanner(System.in);
                System.out.println("\n\nMENU\n1)Register new member\n2)Delete member\n3)View all registered members\n4)View member\nAny other number to go back");
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addMemeber();
                        break;
                    case 2:
                        deleteMember();
                        break;
                    case 3:
                        String name, ID;
                        ArrayList<String> booksIssued = new ArrayList<>();

                        MembersRepository membersRepository = new MembersRepository();
                        FindIterable<Document> records = membersRepository.search();


                        System.out.println("--------------------------------------------------------------------------------------------------------------");
                        System.out.printf("| %-10s | %-20s | %-70s |%n", "ID", "Name", "Books Issued");
                        System.out.println("--------------------------------------------------------------------------------------------------------------");
                        for (Document record : records) {
                            ID = (String) record.get("ID");
                            name = (String) record.get("Name");
                            if (record.get("Books Issued") != null)
                                booksIssued = (ArrayList<String>) record.get("Books Issued");
                            else
                                booksIssued = new ArrayList<>();;



//                             Print each record with proper formatting
                            System.out.printf("| %-10s | %-20s | %-70s |%n", ID, name, String.join(", ", booksIssued));
                        }
                        System.out.println("--------------------------------------------------------------------------------------------------------------");


                        break;
                    case 4:
                        Document document = findMember();
                        if (document == null) System.out.println("Member not found!!!");
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

//    private int memberMenu() {
//        Scanner sc = null;
//        int choice = 0;
//        try {
//            sc = new Scanner(System.in);
//            do {
//                System.out.print("Find member by\n1)Name\n2)ID\nchoice: ");
//                choice = sc.nextInt();
//            } while (choice != 1 && choice != 2);
//        } catch (Exception e) {
//            System.err.print(e);
//        }
//
//        return choice;
//    }
}
