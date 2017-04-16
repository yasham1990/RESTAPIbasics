package org.yasham.javabrains.messenger.message;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Profile {

	
	private long id;
	private String profileName;
	private Date created;
	private String firstName;
	private String lastName;
	
	public Profile() {
		
	}
	
	public Profile(long id, String profileName,String firstName,String lastName) {
		this.id=id;
		this.profileName=profileName;
		this.created=new Date();
		this.firstName=firstName;
		this.lastName=lastName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	



}
