package edu.bu.met.cs665.Library;

import edu.bu.met.cs665.Handler.Request;
import edu.bu.met.cs665.Handler.BookCheckoutHandler;
import edu.bu.met.cs665.Handler.BookReturnHandler;
import edu.bu.met.cs665.Handler.RequestHandler;

public class LibraryManagementSystem {
    private RequestHandler chain;

    public void setupChain() {
        BookCheckoutHandler checkoutHandler = new BookCheckoutHandler();
        BookReturnHandler returnHandler = new BookReturnHandler();

        checkoutHandler.setNextHandler(returnHandler);

        chain = checkoutHandler;
    }

    public void processRequest(Request request) {
        chain.handleRequest(request);
    }
}
