package group22.ci;

public class MavenResult{
	Boolean result;
	String message;
	
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
}