package com.example.model;

import java.util.List;

public class JSONResponse {
	private List<ClassIdentified> classesIdentified;
	
	public List<ClassIdentified> getClassesIdentified(){
		return classesIdentified;
	}

	public void setClassesIdentified(List<ClassIdentified> classesIdentified) {
		this.classesIdentified = classesIdentified;
	}
	
	

}
