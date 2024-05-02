package edu.bu.met.cs665.Handler;


import edu.bu.met.cs665.Library.MembersManager;

public class ManageMemberHandler implements RequestHandler {
    private RequestHandler nextHandler;

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("in member handler");
        if (request.getType() == RequestType.MANAGE_MEMBER) {            // Handle checkout request
            MembersManager membersManager = new MembersManager();
            membersManager.displayMemberManagerMenu();
        } else if (nextHandler != null) {
            // Pass the request to the next handler
            nextHandler.handleRequest(request);
        }
    }
}