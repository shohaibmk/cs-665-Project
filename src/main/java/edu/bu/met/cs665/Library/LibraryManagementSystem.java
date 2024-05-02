package edu.bu.met.cs665.Library;

import edu.bu.met.cs665.Handler.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementSystem {
    private RequestHandler chain;

    /**
     * method to set up the chain of responsibilities
     */
    public void setupChain() {
        LoanHandler loanHandler = new LoanHandler();
        ReturnHandler returnHandler = new ReturnHandler();
        ManageBookHandler manageBookHandler = new ManageBookHandler();
        ManageMemberHandler manageMemberHandler = new ManageMemberHandler();

        loanHandler.setNextHandler(returnHandler);
        returnHandler.setNextHandler(manageBookHandler);
        manageBookHandler.setNextHandler(manageMemberHandler);
        chain = loanHandler;
    }

    /**
     * method to process the request
     *
     * @param request
     */
    public void processRequest(Request request) {
        chain.handleRequest(request);
    }

    /**
     * method to display main menu and make a request
     */
    public void run() {
        Request request = null;

        try {
            setupChain();                                                 //initiating the chain of handlers
            int choice = 1;
            while (choice != 0) {
                choice = displayMenu();
                switch (choice) {
                    case 1:
                        request = new Request(RequestType.LOAN_BOOK);
                        break;
                    case 2:
                        request = new Request(RequestType.RETURN_BOOK);
                        break;
                    case 3:
                        request = new Request(RequestType.MANAGE_BOOK);
                        break;
                    case 4:
                        request = new Request(RequestType.MANAGE_MEMBER);
                        break;
                    default:
                        choice = 0;
                }
                if (choice != 0) processRequest(request);
            }

        } catch (Exception e) {
            System.err.println(e);
            LogsManager.log("Exception in LibraryManagementSystem Class - " + e);
        }
    }

    /**
     * method to display menu
     *
     * @return int
     */
    private int displayMenu() {
        Scanner scanner = null;
        while (true) {
            int choice;
            try {
                scanner = new Scanner(System.in);
                System.out.println("\n\n\n\nMenu\n1)Loan\n2)Return\n3)Book Manager\n4)Member Manager");
                System.out.println("Any other number to exit");
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input !!!");
                LogsManager.log("Exception in LibraryManagementSystem class - " + e);
            } catch (Exception e) {
                LogsManager.log("Exception in LibraryManagementSystem class - " + e);
            }
        }
    }
}
