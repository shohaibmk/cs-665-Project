package edu.bu.met.cs665.Handler;


public class MemberHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("in member handler");
        if (request.getType() == RequestType.MANAGE_BOOK) {
            // Handle checkout request
            System.out.println("Book member handled");
        } else if (nextHandler != null) {
            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}