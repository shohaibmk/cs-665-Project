package edu.bu.met.cs665.Library;

import edu.bu.met.cs665.Handler.*;

public class LibraryManagementSystem {
    private RequestHandler chain;

    public void setupChain() {
        LoanHandler checkoutHandler = new LoanHandler();
        ReturnHandler returnHandler = new ReturnHandler();
        BookHandler bookHandler = new BookHandler();
        MemberHandler memberHandler = new MemberHandler();

        checkoutHandler.setNextHandler(returnHandler);
        returnHandler.setNextHandler(bookHandler);
        bookHandler.setNextHandler(memberHandler);
        chain = checkoutHandler;
    }

    public void processRequest(Request request) {
        chain.handleRequest(request);
    }
}
