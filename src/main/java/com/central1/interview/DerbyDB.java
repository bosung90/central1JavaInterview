package com.central1.interview;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2015-05-30.
 */
public class DerbyDB implements DBInterface {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String PROTOCOL = "jdbc:derby:";
    private static String DB_NAME = "postings";

    private static final DerbyDB instance = new DerbyDB();

    public DerbyDB() {
        init();
    }

    public DerbyDB(String Db_name)
    {
        DB_NAME = Db_name;
        init();
    }

    private void init()
    {
        Connection conn = null;
        try
        {
            Class.forName( DRIVER ).newInstance();
            conn = getConnection();

            Statement s = conn.createStatement();
            s.execute( "create table person(" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, " +
                    "email varchar(128), " +
                    "password varchar(20)," +
                    "CONSTRAINT person_primary_key PRIMARY KEY (id) )" );
            s.execute( "create table post(" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, " +
                    "personId int, " +
                    "message varchar(1024)," +
                    "CONSTRAINT post_primary_key PRIMARY KEY (id) )" );
            System.out.println( "Initialized DB Tables." );
        }
        catch ( SQLException e )
        {
            if ( "X0Y32".equals( e.getSQLState() ) )
            {
                // DB already initialized...
            }
            else
            {
                e.printStackTrace();
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static DerbyDB getInstance()
    {
        return instance;
    }

    public void addUser( String email, String password )
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            Statement s = conn.createStatement();
            s.execute( "insert into person(email, password) values('" + email.toLowerCase() + "', '" + password + "')" );
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection( conn );
        }
    }

    public void addPost( String email, String password, String message )
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery( "select id FROM person where email = '" + email.toLowerCase() + "' and password = '" + password + "'" );
            if ( rs.next() )
            {
                int personId = rs.getInt( "id" );
                s.execute( "insert into post(personId, message) values(" + personId + ", '" + message + "')" );
            }
            else
            {
                throw new InterfaceException( "User not found" );
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection( conn );
        }
    }

    public List<Database.Post> getAllPosts()
    {
        Connection conn = null;
        List<Database.Post> posts = new ArrayList<>();
        try
        {
            conn = getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery( "select post.id as id, message, email  FROM post, person where post.personId = person.id" );
            while ( rs.next() )
            {
                posts.add( new Database.Post( rs.getInt( "id" ), rs.getString( "email" ), rs.getString( "message" ) ) );
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection( conn );
        }
        return posts;
    }

    private void closeConnection( Connection conn )
    {
        try
        {
            if ( conn != null )
            {
                conn.close();
            }
        }
        catch ( SQLException sqle )
        {
            sqle.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(PROTOCOL + DB_NAME + ";create=true");
    }
}
