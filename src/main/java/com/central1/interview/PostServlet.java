package com.central1.interview;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostServlet extends HttpServlet
{
	@Override
	public void init() throws ServletException
	{
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		try
		{
			String message = req.getParameter( "message" );
			if ( message.length() == 0 )
			{
				throw new InterfaceException( "Must have a message!" );
			}
			Database.getInstance().addPost(
					req.getParameter( "email" ),
					req.getParameter( "password" ),
					message );
			resp.sendRedirect( "/list-posts.jsp" );
		}
		catch ( InterfaceException e )
		{
			resp.sendRedirect( "/?error=" + e.getMessage() );
		}
	}
}