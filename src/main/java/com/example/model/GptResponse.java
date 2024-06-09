package com.example.model;

import java.util.List;

public class GptResponse {
	private List<Choices> choices;
	private String responseContent;

	public List<Choices> getChoices() {
		return choices;
	}

	public void setChoices(List<Choices> choices) {
		this.choices = choices;
	}
	
	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	

}
