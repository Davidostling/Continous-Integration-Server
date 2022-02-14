package group22.ci;

/**
 * Class used to store the information given with the results of each maven command call
 */
public class MavenResult{
	Boolean result;
	String message;
	String details = "";
	
	public MavenResult (Boolean result, String message){
		this.result = result;
		this.message = message;
	}
	
	public Boolean getResult (){
		return result;
	}
	
	public String getMessage (){
		return message;
	}
	
	public void setDetails(String details){
		this.details = details;
	}
	
	public String getDetails(){
		return details;
	}
}