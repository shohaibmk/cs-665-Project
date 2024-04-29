/**
 * Name: Shohaib Mallick
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/02/2024
 * File Name: Main.java
 */

package edu.bu.met.cs665;


import edu.bu.met.cs665.Handler.Request;
import edu.bu.met.cs665.Handler.RequestType;
import edu.bu.met.cs665.Library.LibraryManagementSystem;

import java.util.Scanner;

/**
 * This is the Main class.
 */
public class Main {


    public static void main(String[] args) {


        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        librarySystem.setupChain();

        try {
            Request request = null;

            int choice = 1;
            while (choice != 0) {
                System.out.println("\n\n\n\nMenu\n1)Loan\n2)Return\n3)Book Manager\n4)Member Manager");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        request = new Request(RequestType.CHECKOUT_BOOK);
                        break;
                    case 2:
                        request = new Request(RequestType.RETURN_BOOK);
                        break;
                    case 3:
                        request = new Request(RequestType.BOOK);
                        break;
                    case 4:
                        request = new Request(RequestType.MEMBER);
                        break;
                    default:
                        choice = 0;
                }
                librarySystem.processRequest(request);
            }

        } catch (Exception e) {
            System.err.println(e);
        }


    }


}
