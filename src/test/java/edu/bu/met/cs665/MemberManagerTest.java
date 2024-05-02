package edu.bu.met.cs665;

import edu.bu.met.cs665.Library.MembersManager;
import org.bson.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MemberManagerTest {

    /**
     * method to test addMember method
     */
    @Test
    public void testAddMember() {
        String input = "Shohaib mk\nBU2001\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MembersManager membersManager = new MembersManager();
        membersManager.addMemeber();

        Document record = membersManager.findMember("BU2001");
        assertNotNull(record);
    }

    /**
     * method to test removeMember mthod
     */
    @Test
    public void testRemovemember() {
        String input = "BU2001\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MembersManager membersManager = new MembersManager();
        membersManager.deleteMember();

        Document record = membersManager.findMember("BU2001");
        assertNull(record);
    }
}
