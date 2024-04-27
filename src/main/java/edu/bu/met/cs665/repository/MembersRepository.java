package edu.bu.met.cs665.repository;

import org.bson.Document;

public class MembersRepository extends MongoRepository{
    public MembersRepository() {
        super("Members");
    }


//    /**
//     * search member by name
//     * @param name
//     */
//    public Document searchByName(String name) {
//        return super.search("Name",name);
//    }

    /**
     * search memeber by ID
     * @param ID
     */
    public Document search(String ID) {
        return super.search("ID", ID);
    }


    /**
     * Delete book by ISBN number
     * @param ID
     */
    public void deleteOne(String ID) {
        super.deleteOne("ID",ID);
    }
}
