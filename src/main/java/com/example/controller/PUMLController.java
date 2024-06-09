package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins= "http://localhost:3000")
public class PUMLController {

	private String pumlFilePath= "src/main/resources/puml_file";
	@Autowired
	private HelloController helloController;
	
	private final ResourceLoader resourceLoader;

	public PUMLController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@PostMapping("/processPUML") //changed from getMapping to PostMapping, since the frontend will now send the selected smell
	public String processPUML(@RequestParam("selectedSmell") String selectedSmell) { 
		try {

			// Construct complete file path
			String completeFilePath = pumlFilePath + "/output.puml";
			// Read PUML code from file
			StringBuilder pumlCode = new StringBuilder();
			Resource resource = resourceLoader.getResource("file:" + completeFilePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				pumlCode.append(line).append("\n");
			}
			reader.close();

			// Parse and modify PUML code
			return "note \"<size:30>" +selectedSmell+ "</size>\" as N1\n"+ modifyPUML(pumlCode.toString(), helloController.getProblematicMethods(), helloController.getAnnotatedClasses());

		} catch (IOException e) {
			e.printStackTrace();
			return "Error parsing PUML code: " + e.getMessage();
		}
	}


	private String modifyPUML(String pumlCode, ArrayList<String> problematicValues, ArrayList<String> classesToAnnotate) {
		// Modify parsed PUML code here to replace the problematic methods' names with <font color=red>+problematicValue
		for(String problematicValue: problematicValues) {
			pumlCode= pumlCode.replace(problematicValue+"(", "<font color=red>"+problematicValue+"(").replace(" " + problematicValue + " :", " <font color=red>" + problematicValue + "</font> :");
		}
		for (String className : classesToAnnotate) { // classesToAnnotate are the classes for which the boolean value=true in the JSON response
			// Modify PUML code to replace the problematic class name with the class name+ #pink ##[bold]red
			String[] diagramElements= {"class ", "abstract class ","enum ","interface "};
		    for (String element : diagramElements) {
		        String oldElement = element + className + "{";
		        String newElement = element + className + " #pink ##[bold]red {";
		        pumlCode = pumlCode.replace(oldElement, newElement); // similar to pumlCode = pumlCode.replace("class " + className + "{", "class " + className + " #pink ##[bold]red {");
		    }
		}
		return pumlCode.replace("skinparam classAttributeIconSize 0",""); //remove this string from the puml file that is not needed
	}
	
	

}
