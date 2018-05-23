package util;

public class Result {

	private boolean result;
	private String description;
	
	public Result(boolean result) {
		this.result = result;
		this.description = null;
	}
	
	public Result (boolean result, String description) {
		this.result = result;
		this.description = description;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
