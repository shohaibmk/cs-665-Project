package edu.bu.met.cs665.Handler;

public class BookCheckoutHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getType() == RequestType.CHECKOUT_BOOK) {
            // Handle checkout request
            System.out.println("Book checkout handled");
        } else if (nextHandler != null) {
            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}