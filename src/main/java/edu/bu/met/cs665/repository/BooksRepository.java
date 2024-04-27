package edu.bu.met.cs665.repository;


import org.bson.Document;

public class BooksRepository extends MongoRepository{
    public BooksRepository() {
        super("Books");
    }

    /**
     * delete book by book name
     * @param bookName
     */
    public void deleteOne(String bookName) {
        super.deleteOne("Book Name",bookName);
    }

    /**
     * Delete book by ISBN number
     * @param ISBN
     */
    public void deleteOne(long ISBN) {
        super.deleteOne("ISBN",ISBN);
    }

    /**
     * search book by book name
     * @param bookName
     */
    public Document search(String bookName) {
        return super.search("Book Name",bookName);
    }

    /**
     * search book by ISBN number
     * @param ISBN
     */
    public Document search(long ISBN) {
        return super.search("ISBN", ISBN);
    }


}

