package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.example.gptIntegration.TamperedPrompt;
import com.example.model.ClassIdentified;
import com.example.model.GptRequest;
import com.example.model.GptResponse;
import com.example.model.JSONResponse;
import com.example.model.Message;
import com.example.model.ResponseFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@RestController
@Data
@CrossOrigin(origins= "http://localhost:3000")//so as to allow requests from the react application
public class HelloController {

	private Map<String, String> javaFilesMap;

	@Autowired
	private RestTemplate restTemplate;

	private ArrayList<String> problematicMethods;
	private ArrayList<String> classesToAnnotate;
	private String model="gpt-3.5-turbo-0125"; //or gpt-4-turbo-preview,gpt-3.5-turbo-1106 (this one performed well), since they support JSON output
	private String apiUrl="https://api.openai.com/v1/chat/completions";

	public HelloController(ArrayList<String> problematicMethods,ArrayList<String> classesToAnnotate ) {
		this.problematicMethods=problematicMethods;
		this.classesToAnnotate=classesToAnnotate;
	}

	public ArrayList<String> getProblematicMethods() {
		return problematicMethods;
	}

	public ArrayList<String> getAnnotatedClasses(){
		return classesToAnnotate;
	}
	
	//method to interact with the model, send the source code and pose a question regarding the quality of it
	@PostMapping("/chatWithModel")
	public ResponseEntity<String> chatWithModel(@RequestParam("prompt") String prompt) {
	    TamperedPrompt newPrompt = new TamperedPrompt(prompt);
	    List<Message> messages = new ArrayList<>();
	    messages.add(new Message("system", "You are a helpful assistant. " + newPrompt.generateNewPrompt()));
	    for (Map.Entry<String, String> javaFile : javaFilesMap.entrySet()) {
	        messages.add(new Message("user", javaFile.getValue()));
	    }

	    // Added the third parameter, so as to instruct the model to provide a JSON response
	    GptRequest request = new GptRequest(model, messages, new ResponseFormat("json_object"), 0.1);

	    try {
	        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

	        // Process the response
	        ObjectMapper objectMapper = new ObjectMapper();
	        JSONResponse jsonResponse = objectMapper.readValue(response.getChoices().get(0).getMessage().getContent(), JSONResponse.class);
	        StringBuilder concatenatedDescriptions = new StringBuilder();
	        //Extract the desired information
	        problematicMethods = new ArrayList<>();
	        classesToAnnotate = new ArrayList<>();
	        for (ClassIdentified classIdentified : jsonResponse.getClassesIdentified()) {
	            concatenatedDescriptions.append(classIdentified.getDescription());//.append("\n");
	            if (classIdentified.getBooleanValue()==true) {
	                String[] problematicValues = classIdentified.getArrayOfMethods();
	                String nameOfClass = classIdentified.getName();
	                classesToAnnotate.add(nameOfClass);
	                for (String method : problematicValues) {
	                    System.out.print(method + ", ");
	                    problematicMethods.add(method);
	                }
	            }
	        }
	        System.out.println("Actual GPT response:\n" + response.getChoices().get(0).getMessage().getContent());
	        return ResponseEntity.ok(concatenatedDescriptions.toString());
	    }
	    	catch (HttpClientErrorException e) {
	        if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: Token limit for this request was reached. The context window of 16,385 tokens was surpassed. Please select fewer source code files.");
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while calling the GPT API.");
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the JSON response. Please try again.");
	    }
	}
	
	//method to read and upload the source files' content to the server
	@PostMapping("/uploadFiles")
	public ResponseEntity<String> uploadFiles(MultipartHttpServletRequest request) {
		Map<String, MultipartFile> fileMap = request.getFileMap();
		this.javaFilesMap = new HashMap<>(); // Create a new instance each time
		// Check if there are files uploaded
		if (!fileMap.isEmpty()) {
			// Iterate over the files and print their content
			int i=0;
			for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
				MultipartFile file = entry.getValue();
				try {
					// Reset the input stream for each file
					InputStream inputStream = file.getInputStream();

					// Read the content of the file
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder content = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						content.append(line).append("\n");
					}

					// Print the content to the console
					System.out.println("Name of the JAVA class: " + file.getOriginalFilename());
					content.toString(); 
					javaFilesMap.put("file"+i,content.toString());
					i++;

					System.out.println(content.toString());
					inputStream.close(); //added this line to close the input stream and safeguard resources
				} 
				catch (IOException e) {
					e.printStackTrace();
					return ResponseEntity.badRequest().body("Failed to read file content.");
				}
			}
		} 
		else {
			return ResponseEntity.badRequest().body("No files uploaded.");
		}

		// For now, just returning a success message and the size of the map
		return ResponseEntity.ok("Files uploaded successfully and size of Map: " +javaFilesMap.size());
	}



}

