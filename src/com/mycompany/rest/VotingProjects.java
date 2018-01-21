package com.mycompany.rest;



import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.mycompany.voting_projects.DataBaseQuery;
import com.mycompany.voting_projects.Project;

/**
*
* @author mateusz93
*/

@Path("/projects")
public class VotingProjects {
	Logger log = Logger.getLogger(VotingProjects.class.getName());
	
	//This method return the list of the projects
	@Path("/getProjects")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getProjectsList() {
		log.info("begin getProjectsList()");
		DataBaseQuery dq = new DataBaseQuery();
		ArrayList<Project> list = dq.getProjectsList();
		return list.toString();
	}
	
	//This method checks if the projects is open and inserts the vote, if the user hasn't voted for this project yet.
	@Path("/makeVote/{projectID}/{userID}/{vote}")
	@POST
	@Produces(MediaType.TEXT_HTML)
	public String makeVote(@PathParam("projectID") int projectID, @PathParam("userID") int userID, @PathParam("vote") String vote) {
		log.info("begin makeVote()");
		DataBaseQuery dq = new DataBaseQuery();
		Boolean voteReturn = dq.makeVote(projectID, userID, vote);
		return voteReturn.toString();
	}
	
	//This method execute the query to get details of project with id equals to that passed in param
	@Path("/getDetails/{id}")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String projectDetails(@PathParam("id") int id) {
		log.info("begin projectDetails()()");
		String out="";
		DataBaseQuery dq = new DataBaseQuery();		
		out = dq.projectDetails(id);
		
		return out;
	}
	
	//This method execute the query to close the project with the id passed in param
	@Path("/closeProject/{id}")
	@PUT
	@Produces(MediaType.TEXT_HTML)
	public String closeProject(@PathParam("id") int id){
		log.info("begin closeProject()");
		DataBaseQuery dq = new DataBaseQuery();
		return dq.closeProject(id).toString();
	}
	
	
}

