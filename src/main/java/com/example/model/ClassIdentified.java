package com.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ClassIdentified {
	private String name;


	@JsonAlias({ "isComplicated", "violatesDesignPrinciples", "violatesSingleResponsibilityPrinciple", "hasInsufficientComments", "isPoorlyNamed", "containsDuplicateCode", "hasPoorlyNamedFields", "isHighlyCoupled" })
	private boolean booleanValue;

	// private boolean isComplicated;
	@JsonAlias({ "complicatedMethods", "violatedDesignPrinciples", "methodsToBeExtracted", "methodsWithInsufficientComments", "poorlyNamedMethods", "methodsWithDuplicateCode", "poorlyNamedFields", "externalMethodNames" })
	private String[] arrayOfMethods;
	// private String packageInfo;
	@JsonAlias({"suggestion"})
	private String description="";

	// private double complexityRating;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getBooleanValue() {
		return booleanValue;
	}
	
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}


	/*
	 * public String getPackageInfo() { return packageInfo; }
	 */

	public String[] getArrayOfMethods() {
		return arrayOfMethods;
	}
	
	public void setArrayOfMethods(String[] arrayOfMethods) {
		this.arrayOfMethods = arrayOfMethods;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/*
	 * public double getComplexityRating() { return complexityRating; }
	 */
}
