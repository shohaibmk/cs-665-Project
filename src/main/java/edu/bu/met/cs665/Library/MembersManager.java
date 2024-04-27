package edu.bu.met.cs665.Library;

import edu.bu.met.cs665.repository.MembersRepository;
import org.bson.Document;

import java.util.ArrayList;
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

            booksIssued = new ArrayList<>();
            booksIssued.add("The Forest Gump");
            booksIssued.add("My Book");


//            booksIssued = null;

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
            if (issuedBooksList == null) {
                MembersRepository membersRepository = new MembersRepository();
                membersRepository.deleteOne(document.get("ID").toString());
//                System.out.println(document.get("ID"));
                System.out.println("Deleting");

            } else {
                System.out.println("Member has issued books, so cannot be deleted");
            }

        }
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
