package com.mycompany.voting_projects;

import java.sql.*;

/**
*
* @author mateusz93
*/
public class DataBase 
{
    private Connection connection;
    private static DataBase istance;
    
    public static DataBase getIstance()
    {
    	if(istance==null)
    		istance=new DataBase();
    	return istance;
    }
    
    public Connection getConnection()
    {
    	final String DB_URL="jdbc:mysql://localhost:3306/projects_voting";
 	    final String USER = "root";
 	    final String PASS = "";
        try 
        {
        	Class.forName("com.mysql.jdbc.Driver");
   			connection = DriverManager.getConnection(DB_URL, USER, PASS);
   	    } 
        catch (ClassNotFoundException e) 
        {
   			e.printStackTrace();
   		}
        catch(SQLException e1)
        {
        	e1.printStackTrace();
        }
        
        return connection;
    }
    
}
