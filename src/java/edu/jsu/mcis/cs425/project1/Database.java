package edu.jsu.mcis.cs425.project1;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Database {
    
    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public Database() throws NamingException {
        
        try {
            
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }
        
        catch (SQLException e) {}

    }

    public String getResultSetTable(ResultSet resultset) throws ServletException, IOException {
        
        ResultSetMetaData metadata = null;
        
        String table = "";
        String tableheading;
        String tablerow;
        
        String key;
        String value;
        
        try {
            
            System.out.println("*** Getting Query Results ... ");

            metadata = resultset.getMetaData();

            int numberOfColumns = metadata.getColumnCount();
            
            table += "<table border=\"1\">";
            tableheading = "<tr>";
            
            System.out.println("*** Number of Columns: " + numberOfColumns);
            
            for (int i = 1; i <= numberOfColumns; i++) {
            
                key = metadata.getColumnLabel(i);
                
                tableheading += "<th>" + key + "</th>";
            
            }
            
            tableheading += "</tr>";
            
            table += tableheading;
                        
            while(resultset.next()) {
                
                tablerow = "<tr>";
                
                for (int i = 1; i <= numberOfColumns; i++) {

                    value = resultset.getString(i);

                    if (resultset.wasNull()) {
                        tablerow += "<td></td>";
                    }

                    else {
                        tablerow += "<td>" + value + "</td>";
                    }
                    
                }
                
                tablerow += "</tr>";
                
                table += tablerow;
                
            }
            
            table += "</table><br />";

        }
        
        catch (Exception e) {}
        
        return table;
        
    } // End getResultSetTable()
    
    public void closeConnection() {
        
        if (conn != null) {
            
            try {
                conn.close();
            }
            
            catch (SQLException e) {}
            
        }
    
    } // End closeConnection()
    
    public Connection getConnection() { return conn; }
    
}
