package com.central1.interview;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database
{
	private static final Database instance = new Database();
    private DBInterface db;

	private Database()
	{
        db = new DerbyDB();
	}

    public void setDataSource(DBInterface db)
    {
        this.db = db;
    }

	public static Database getInstance()
	{
		return instance;
	}

	public void addUser( String email, String password ) throws InterfaceException
	{
		if ( password.length() > 10 )
		{
			throw new InterfaceException( "Password is too long" );
		}

		if ( password.length() < 5 )
		{
			throw new InterfaceException( "Password is too short" );
		}
        try
        {
            db.addUser(email, password);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
	}

	public void addPost( String email, String password, String message ) throws InterfaceException
	{
		if ( email.toLowerCase().contains( "farshad" ) )
		{
			throw new InterfaceException( "Farshad is not allowed to post!" );
		}
        try
        {
            db.addPost(email, password, message);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
	}

	public List<Post> getAllPosts()
	{
		List<Post> posts = null;
        try
        {
            posts = db.getAllPosts();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
		return posts;
	}

	public static class Post implements Serializable
	{
		private final int id;
		private final String email;
		private final String message;

		protected Post( int id, String email, String message )
		{
			this.id = id;
			this.email = email;
			this.message = message;
		}

		public int getId()
		{
			return id;
		}

		public String getEmail()
		{
			return email;
		}

		public String getMessage()
		{
			return message;
		}
	}
}
