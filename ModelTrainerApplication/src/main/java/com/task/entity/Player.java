package com.task.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	int id;
	String name;
	String status;
	@Temporal(TemporalType.TIMESTAMP)
	Date createdTime;
	byte[] jsonFile;
	byte[] xmlFile;

	public Player() {
	}

	public Player(int i, String string, String string2) {
		this.id = i;
		this.name = string;
		this.status = string2;
	}

	public Player(int id, String name, String status, Date createdTime, byte[] jsonFile, byte[] xmlFile) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.createdTime = createdTime;
		this.jsonFile = jsonFile;
		this.xmlFile = xmlFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public byte[] getJsonFile() {
		return jsonFile;
	}

	public void setJsonFile(byte[] jsonFile) {
		this.jsonFile = jsonFile;
	}

	public byte[] getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(byte[] xmlFile) {
		this.xmlFile = xmlFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", status=" + status + ", createdTime=" + createdTime
				+ ", jsonFile=" + Arrays.toString(jsonFile) + ", xmlFile=" + Arrays.toString(xmlFile) + "]";
	}

}
