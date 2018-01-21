package com.mycompany.voting_projects;

/**
 *
 * @author mateusz93
 */
public class Project {
  private int ID;
  private String name;
  private String description;
  private Boolean open;
  
  public Project(int iD, String name, String description, Boolean open) {
	this.ID = iD;
	this.name = name;
	this.description = description;
	this.open = open;
  }

  public Project() {
	
  }

  public int getID() {
    return ID;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getOpen() {
    return open;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOpen(Boolean open) {
    this.open = open;
  }
  
  @Override
  public String toString() {
		return "Project [ID=" + ID + ", name=" + name + ", description=" + description + ", open=" + open + "]";
  }
  
  
 
}
