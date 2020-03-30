package domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseClass implements Serializable{

	private final String message;
	private final Map<String, Object> input;

	public ResponseClass(String message, Map<String, Object> input) {
		this.message = message;
		this.input = input;
	}

	public String getMessage() {
		return this.message;
	}

	public Map<String, Object> getInput() {
		return this.input;
	}

}
