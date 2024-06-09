package com.example.gptIntegration;

public class TamperedPrompt {
	private String selectedSmell;
	
	public TamperedPrompt(String initialPrompt) {
		this.selectedSmell=initialPrompt;
	}
	
	public String getSelectedSmell() {
		return selectedSmell;
	}

	public void setSelectedSmell(String selectedSmell) {
		this.selectedSmell = selectedSmell;
	}

	/*This method must take as a parameter the file(s) that were attached by the user in order to ask
	gpt a question about the selected code smell and if this appears on the files attached */
	public String generateNewPrompt() {

		if(selectedSmell.equals("Lack of comments")) {
			return "Parse and examine all the Java code and track down any classes with poor commenting. For each Java class, state its name, if you deem that there are sufficient comments on the code or not, and if not, which methods of the class could be further elaborated with comments. Identify methods whose functionality is not clear and should be further explained with comments. Lastly, provide some short suggestion for the class overall to improve the commenting. Provide your response only in JSON format.The JSON response should follow this schema: "
					+ "{\r\n"
					+ "	\"classesIdentified\": [\r\n"
					+ "		{\r\n"
					+ "			\"name\": \"\",\r\n"
					+ "			\"hasInsufficientComments\": false,\r\n"
					+ "			\"methodsWithInsufficientComments\": [methodName],\r\n"
					+ " 		\"suggestion\" : \"The \"+name\" class ... \"\r\n"
					+ "		}\r\n"
					+ "	]\r\n"
					+ "}";
		}
		else if(selectedSmell.equals("Violation of SOLID Principles")) {
			return "Parse and examine all the Java code that you are provided with. For each Java class, state its name and if you deem that the class violates any SOLID design principles (Single Responsibility Principle, Open/Closed Principle, Liskov Substitution Principle, Interface Segregation Principle, Dependency Inversion Principle) or not. Also, provide any principles that are being violated, if there are any, and a short description of why the class violates the design principle. Provide your response only in JSON format. The JSON response should follow this schema: "
					+ "{	\"classesIdentified\": [\r\n"
					+ "	 {\r\n"
					+ "	\"name\": \"\", \r\n"
					+ "	\"violatesDesignPrinciples\": true,\r\n"
					+ "	\"violatedDesignPrinciples\": [],\r\n"
					+ " \"description\" : \"The \"+name\" class ... \"\r\n"
					+ "	 }\r\n"
					+ "	]\r\n"
					+ "} ";
		}
		else if(selectedSmell.equals("Naming of methods/classes does not adhere to conventions")) {
			return "Parse and analyze all the Java code to identify any classes and methods whose naming hinders readability and understandability. Also, provide a short suggestion for the entities with poor naming, on how to improve the understandability. Provide your response only in JSON format. The JSON response should follow this schema: "
					+ "{\r\n"
					+ "	\"classesIdentified\": [\r\n"
					+ "	 {\r\n"
					+ "	\"name\": \"\",\r\n"
					+ "	\"isPoorlyNamed\": true,\r\n"
					+ "	\"poorlyNamedMethods\": [methodName],\r\n"
					+ " \"suggestion\" : \"The \"+name\" class ... \"\r\n"
					+ "	 }\r\n"
					+ "	]\r\n"
					+ "}";
		}
		else if(selectedSmell.equals("Naming of fields does not adhere to conventions")) {
			return "Parse and analyze all the Java code to detect fields within classes that violate naming conventions. For each identified class, list poorly named fields and provide suggestions for improving their clarity. Provide your response only in JSON format. The JSON response should follow this schema: "
					+ "{\r\n"
					+ "	\"classesIdentified\": [\r\n"
					+ "	 {\r\n"
					+ "	\"name\": \"\",\r\n"
					+ "	\"hasPoorlyNamedFields\": true,\r\n"
					+ "	\"poorlyNamedFields\": [ field1, field2, ... ],\r\n"
					+ " \"suggestion\" : \"The \"+name\" class ... \"\r\n"
					+ "	 }\r\n"
					+ "	]\r\n"
					+ "}";
			
		}
		else if(selectedSmell.equals("High coupling")) {
			return "Parse and examine all the Java source code that you are provided with and look for any instances of high coupling. Examine each class and identify any that depends heavily on other classes by calling their methods or by using their properties and their data. As a last step, provide a suggestion to decouple the class if you deem that it is coupled. The prior steps should be followed for each class that you are provided with. Provide your response only in JSON format. The JSON response should follow this schema:"
					+ "{\r\n"
					+ "	classesIdentified: [\r\n"
					+ "		{\r\n"
					+ "			\"name\" : \"\",\r\n"
					+ "			\"isHighlyCoupled\": true,\r\n"
					+ "			\"externalMethodNames\": [methodName],\r\n"
					+ "			\"suggestion\": \"The \"+name\" class ... \"\r\n"
					+ "		}\r\n"
					+ "	]\r\n"
					+ "}";
		}
		else if(selectedSmell.equals("Complicated methods/classes")) {
			
			return "Parse and analyze all the provided Java source code to identify complicated methods and classes. For each Java class, state its name, if you deem it is complicated or not, it's complicated methods and a short description of why the class is complicated. Provide your response only in JSON format. It is imperative that the JSON response follows this schema: "
					+ "{\r\n"
					+ "	\"classesIdentified\": [\r\n"
					+ "	 {\r\n"
					+ "		\"name\": \"\",\r\n"
					+ "		\"isComplicated\": true,\r\n"
					+ "		\"complicatedMethods\": [methodName], \r\n"
					+ " 	\"description\" : \"The \"+name\" class ... \"\r\n"
					+ "	 }\r\n"
					+ "	]\r\n"
					+ "}";//Removed the complexity rating property";
			
		}
		else if(selectedSmell.equals("God class")) {
			return "Parse and analyze all the Java code and track down any God classes with multiple responsibilities. For each Java class below, state its name, if you deem that the class violates the Single Responsibility Principle or not, and if so, which methods can be extracted to another class. Lastly, provide a description for each class. Provide your response only in JSON format. The JSON response should follow this schema:"
					+ "{\r\n"
					+ "	\"classesIdentified\": [\r\n"
					+ "		{\r\n"
					+ "        \"name\": \"\",\r\n"
					+ "        \"violatesSingleResponsibilityPrinciple\": true,\r\n"
					+ "        \"methodsToBeExtracted\": [methodName],\r\n"
					+ " 	   \"description\" : \"The \"+name\" class ... \"\r\n"
					+ "		}\r\n"
					+ "	]\r\n"
					+ "} "; // removed number of attributes field and the adjective "short" for the description
		}
		else if(selectedSmell.equals("Duplicate code")) {
			return "Parse and analyze all the provided Java source code and identify sections of code that are duplicated within or across classes. Focus on identifying code segments that are identical or highly similar in structure and functionality. For each Java class below, state its name, if it contains duplicate code or not and if so, specify the exact methods with duplicate code segments. As a last step you should provide a short description for each class and advise for refactoring options, if there are any. Provide your response only in JSON format. The JSON response should follow this schema:"
					+ "{\r\n"
					+ "	\"classesIdentified\": [\r\n"
					+ "	 {\r\n"
					+ "		\"name\": \"\",\r\n"
					+ "		\"containsDuplicateCode\": true,\r\n"
					+ "		\"methodsWithDuplicateCode\": [methodName], \r\n"
					+ " 	\"description\" : \"The \"+name\" class ... \"\r\n"
					+ "	 }\r\n"
					+ "	]\r\n"
					+ "}";
		}
			return "";
	}

}
