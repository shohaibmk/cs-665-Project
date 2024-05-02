package edu.bu.met.cs665.Handler;

import edu.bu.met.cs665.Library.LoanManager;

public class LoanHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getType() == RequestType.LOAN_BOOK) {            // Handle Loan request
            LoanManager loanManager = new LoanManager();
            loanManager.displayLoanMenu();
            System.out.println("Book loan handled");
        } else if (nextHandler != null) {            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}
