package edu.bu.met.cs665.Handler;


import edu.bu.met.cs665.Library.BooksManager;

public class ManageBookHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("in book handler");
        if (request.getType() == RequestType.MANAGE_BOOK) {// Handle manage books request
            BooksManager booksManager = new BooksManager();
            booksManager.displayBookManagerMenu();
        } else if (nextHandler != null) {
            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}