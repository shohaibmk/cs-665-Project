package edu.bu.met.cs665.Handler;

import edu.bu.met.cs665.Library.LoanManager;

public class ReturnHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("in book return handler");

        if (request.getType() == RequestType.RETURN_BOOK) {            // Handle return request
            LoanManager loanManager = new LoanManager();
            loanManager.displayReturnMenu();
        } else if (nextHandler != null) {            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}
