package com.central1.interview;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet
{
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		try
		{
			Database.getInstance().addUser( req.getParameter( "email" ), req.getParameter( "password" ) );
			resp.sendRedirect( "/" );
		}
		catch ( InterfaceException e )
		{
			resp.sendRedirect( "/sign-up.jsp?error=" + e.getMessage() );
		}
	}
}
