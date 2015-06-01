package com.central1.interview;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Eric on 2015-05-29.
 */
public class GetPostServlet extends HttpServlet {
    @Override
    public void init() throws ServletException
    {
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
        try
        {
            List<Database.Post> allPosts = Database.getInstance().getAllPosts();
            Gson gson = new Gson();
            String json = gson.toJson(allPosts);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);
        }
        catch ( InterfaceException e )
        {
            resp.sendRedirect( "/?error=" + e.getMessage() );
        }
        finally
        {
            resp.getWriter().flush();
            resp.getWriter().close();
        }
    }
}
