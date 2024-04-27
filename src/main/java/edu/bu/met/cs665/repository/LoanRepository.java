package edu.bu.met.cs665.repository;

import org.bson.Document;

public class LoanRepository extends MongoRepository {


    public LoanRepository() {
        super("Loan");
    }

    /**
     *
     * @param ISBN
     * @return
     */
    public Document search(String ISBN) {
        return super.search("ISBN", ISBN);
    }

}
