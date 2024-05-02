/**
 * Name: Shohaib Mallick
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/02/2024
 * File Name: Main.java
 */

package edu.bu.met.cs665;


import edu.bu.met.cs665.Library.LibraryManagementSystem;
import edu.bu.met.cs665.Library.LogsManager;

/**
 * This is the Main class.
 * The main menu is display
 */
public class Main {

    public static void main(String[] args) {

        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        LogsManager.log("Library Management System Starting");
        librarySystem.run();
        LogsManager.log("Library Management System Exiting");

    }


}
