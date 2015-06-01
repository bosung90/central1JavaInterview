package com.central1.interview.junit;

import com.central1.interview.Database;
import com.central1.interview.DerbyDB;
import com.central1.interview.InterfaceException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Eric on 2015-05-30.
 */
public class MyUnitTests {

    private static final String TEST_DB_NAME = "Test";

    @BeforeClass
    public static void initDB() throws IOException {
        deleteTestDB();

        Database.getInstance().setDataSource(new DerbyDB(TEST_DB_NAME));
    }

    //Deleting TestDB at the end of junit throws file-in-use exception
    private static void deleteTestDB() throws IOException {
        Path start = Paths.get(TEST_DB_NAME);
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException
            {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e)
                    throws IOException
            {
                if (e == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed
                    throw e;
                }
            }
        });
    }

    @Test
    public void testSignUpShortPasswordFail() {
        boolean isFailed = false;
        try
        {
            Database.getInstance().addUser("sample@example.com", "1234");
        }
        catch (InterfaceException e)
        {
            isFailed = true;
        }
        Assert.assertTrue("Check if short password throws exception", isFailed);
    }

    @Test
    public void testSignUpLongPasswordFail() {
        boolean isFailed = false;
        try
        {
            Database.getInstance().addUser("sample@example.com", "12345678901");
        }
        catch (InterfaceException e)
        {
            isFailed = true;
        }
        Assert.assertTrue("Check if long password throws exception", isFailed);
    }

    @Test
    public void testSignUpPasswordSuccess() {
        boolean isFailed = false;
        try
        {
            Database.getInstance().addUser("sample@example.com", "1234567890");
        }
        catch (InterfaceException e)
        {
            isFailed = true;
        }
        Assert.assertFalse("Check if good upperbound password doesn't throw exception", isFailed);

        isFailed = false;
        try
        {
            Database.getInstance().addUser("sample2@example.com", "12345");
        }
        catch (InterfaceException e)
        {
            isFailed = true;
        }
        Assert.assertFalse("Check if good lowerbound password doesn't throw exception", isFailed);
    }

    @Test
    public void testAddPostSuccess() {
        try
        {
            Database.getInstance().addUser("sample3@example.com", "12345");
            Database.getInstance().addPost("sample3@example.com", "12345", "SampleMessage");
        }
        catch (Exception e)
        {
            Assert.fail("addPost threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDuplicateSignup() {
        try
        {
            Database.getInstance().addUser("sample4@example.com", "12345");
            Database.getInstance().addUser("sample4@example.com", "12346");
        }
        catch (Exception e)
        {
            return;
        }
        Assert.fail("Duplicate sign-up did not throw an exception.");
    }

    @Test
    public void testDuplicateSignupCaps() {
        try
        {
            Database.getInstance().addUser("sample5@example.com", "12345");
            Database.getInstance().addUser("Sample5@example.com", "12346");
        }
        catch (Exception e)
        {
            return;
        }
        Assert.fail("Duplicate sign-up with caps did not throw an exception.");
    }

    @Test
    public void testAddPostCapsEmail() {
        try
        {
            Database.getInstance().addUser("sample6@example.com", "12345");
            Database.getInstance().addPost("Sample6@example.com", "12345", "SampleMessage");
        }
        catch (Exception e)
        {
            Assert.fail("Email address should ignore all caps and allow add post.");
        }
    }

    @Test
    public void testAddPostFail() {
        try
        {
            Database.getInstance().addPost("farshad@example.com", "SamplePass", "SampleMessage");
        }
        catch (Exception e)
        {
            return;
        }
        Assert.fail("addPost with email farshad did not throw an exception.");
    }

    @Test
    public void testGetAllPosts() {
        try
        {
            Database.getInstance().addUser("sample7@example.com", "12345");
            SecureRandom random = new SecureRandom();
            String randomString = new BigInteger(130, random).toString(32);
            Database.getInstance().addPost("sample7@example.com", "12345", randomString);
            List<Database.Post> posts = Database.getInstance().getAllPosts();
            for(Database.Post p : posts)
            {
                if(p.getMessage().equals(randomString))
                    return;
            }
        }
        catch (Exception e)
        {
            Assert.fail("testing GetAllPosts threw an exception: " + e.getMessage());
            return;
        }
        Assert.fail("adding a post with random string and retrieving it did not work.");
    }
}
