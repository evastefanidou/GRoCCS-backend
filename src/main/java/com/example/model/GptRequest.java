package com.example.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GptRequest {
	private String model;
	private List<Message> messages;
	@JsonProperty("response_format") //added this JsonProperty annotation from Jackson to specify the JSON key name for the responseFormat field, since responseFormat was not recognized and produced a 400 bad request error 
	private ResponseFormat responseFormat; 
	private double temperature;

	//Added the third parameter
	public GptRequest(String model,List<Message> messages, ResponseFormat responseFormat ,double temperature ) {
		this.model = model;
		this.messages = messages;
		this.responseFormat= responseFormat;
		this.temperature= temperature;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public ResponseFormat getResponseFormat() {
		return responseFormat;
	}
	
	public void setResponseFormat(ResponseFormat responseFormat) {
		this.responseFormat = responseFormat;
	}

	public double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}


}
