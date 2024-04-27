package edu.bu.met.cs665.repository;

public class EmployeeRepository extends MongoRepository{
    public EmployeeRepository() {
        super("Employee");
    }
}