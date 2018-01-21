package com.mycompany.voting_projects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
*
* @author mateusz93
*/
public class DataBaseQuery 
{
	
	Logger log = Logger.getLogger(DataBaseQuery.class.getName());
	
	public ArrayList<Project> getProjectsList(){
		Connection connection = DataBase.getIstance().getConnection();
		PreparedStatement operation;
		ArrayList<Project> a = new ArrayList<Project>();

		try 
		{
			operation=connection.prepareStatement("select * from projects order by name;");
			ResultSet result=operation.executeQuery();
			
			while (result.next()) {
				Project p = new Project(result.getInt(1), result.getString(2), result.getString(3), result.getBoolean(4));
	            System.out.println(p.toString());
	            a.add(p);
	        }
		} 
		catch (SQLException e) 
		{
			log.error("Error in getProjectsList() " + e);
		}
		return a;
	}

	
	public boolean makeVote(int projectId, int userId, String vote)
	{
		boolean ris=false;
		boolean open=false;
		Connection connection = DataBase.getIstance().getConnection();
		PreparedStatement operation;
		try 
		{
			operation=connection.prepareStatement("select open from projects where idproject=?;");
			operation.setInt(1,projectId);
			ResultSet result=operation.executeQuery();
			result.next();
			open = result.getBoolean(1);
		} 
		catch (Exception e) 
		{
			log.error("Error in makeVote() " + e);
		}
		System.out.println(open);

		if(open){
			try 
			{
				//Because the project's and user's id are the primary key for 'projects_voted_by_users' table,
				//The insert operation will be made only if this two values aren't already in the table, so an user
				//can't vote more times the same project
				operation=connection.prepareStatement("insert into projects_voted_by_users values(?,?,?);");
				operation.setInt(1,projectId);
				operation.setInt(2,userId);
				operation.setString(3,vote);

				int op = operation.executeUpdate();
				ris=true;
			} 
			catch (Exception e) 
			{
				log.error("Error insert in makeVote() " + e);
			}
		}
		return ris;
	}
	
	
	
	public Boolean closeProject(int id)
	{
		
		Connection connection = DataBase.getIstance().getConnection();
		PreparedStatement operation;
		Boolean ris = false;
	    try 
	    {
	    	operation=connection.prepareStatement("update projects set open=0 where idproject=?");
			operation.setInt(1,id);

			if(operation.executeUpdate()!=0);
				ris = true;
			
		}
	    catch (SQLException e) 
	    {
			log.error("Error in closeProject() " + e);
		}	
	    return ris;
	}
	
	public String projectDetails(int id){
		Connection connection = DataBase.getIstance().getConnection();
		PreparedStatement operation;
		
		Project p = new Project();
		int votesFor = 0;
		int votesAgainst = 0;
		try 
		{
			operation=connection.prepareStatement("select * from projects where idproject=?;");
			operation.setInt(1,id);

			ResultSet result=operation.executeQuery();
			result.next();
			p = new Project(result.getInt(1), result.getString(2), result.getString(3), result.getBoolean(4)); 
		} 
		catch (Exception e) 
		{
			log.error("Error in select projects " + e);
		}
		try 
		{
			operation=connection.prepareStatement("select count(*) from projects join projects_voted_by_users on idproject = projects_idproject where vote='for';");
			ResultSet result=operation.executeQuery();
			result.next();
			votesFor = result.getInt(1); 
		} 
		catch (Exception e) 
		{
			log.error("Error in count for " + e);

		}
		try 
		{
			operation=connection.prepareStatement("select count(*) from projects join projects_voted_by_users on idproject = projects_idproject where vote='against';");
			ResultSet result=operation.executeQuery();
			result.next();
			votesAgainst = result.getInt(1); 
		} 
		catch (Exception e) 
		{
			log.error("Error in count against " + e);

		}
		return p.toString() + " votes for: " + votesFor + " votes against: " + votesAgainst;
	}
	/*
	public static void main(String [] args){
		DataBaseQuery dq = new DataBaseQuery(); 
		dq.insertProject();
		//dq.closeProject(1);

		ArrayList<Project> a = dq.getProjectsList();
		//dq.insertUser();
		dq.getUsers();
		dq.makeVote(1, 8, "against");
		System.out.println(dq.selectAll("projects_voted_by_users"));
		System.out.println(dq.projectDetails(1));

		
	}*/
	
	
}
