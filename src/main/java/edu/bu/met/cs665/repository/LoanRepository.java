package edu.bu.met.cs665.repository;

import org.bson.Document;

public class LoanRepository extends MongoRepository {


    public LoanRepository() {
        super("Loan");
    }

    /**
     *
     * @param filter
     * @return Document
     */
    public Document search(Document filter) {
        return super.search(filter);
    }

}
