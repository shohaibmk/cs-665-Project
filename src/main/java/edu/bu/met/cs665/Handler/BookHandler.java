package edu.bu.met.cs665.Handler;


public class BookHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("in book handler");
        if (request.getType() == RequestType.BOOK) {
            // Handle checkout request
            System.out.println("Book checkout book handler");
        } else if (nextHandler != null) {
            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}