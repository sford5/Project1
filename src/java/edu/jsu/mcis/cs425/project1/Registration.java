package edu.jsu.mcis.cs425.project1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registration extends HttpServlet {

       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Database db = null;
        
        Connection connection;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        String parameter;
        String table = "";
        
        boolean hasresults;
            
        try {
            
            db = new Database();
            connection = db.getConnection();

            parameter = request.getParameter("search");
            
            query = "SELECT * FROM registrations WHERE sessionID = ?";
            
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, parameter);
            
            hasresults = pstatement.execute();
            
            while ( hasresults || pstatement.getUpdateCount() != -1 ) {
                
                if ( hasresults ) {
                    resultset = pstatement.getResultSet();
                    table += db.getResultSetTable(resultset);
                }
                
                else {
                    
                    if ( pstatement.getUpdateCount() == -1 ) {
                        break;
                    }
                    
                }

                hasresults = pstatement.getMoreResults();
            
            }

            out.println("<p>Search Parameter: " + parameter + "</p>" + table);

        }
        
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        finally {
            
            out.close();
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (db != null) { db.closeConnection(); }
            
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        doGet(request, response);
        
    }

    @Override
    public String getServletInfo() { return "Registration Servlet"; }

}


