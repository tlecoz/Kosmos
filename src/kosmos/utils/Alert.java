package kosmos.utils;

public class Alert {
	
	
	public static void error(String message){
		System.err.println(message);
		System.exit(0);
	}
	public static void error(String className,String functionName,String message){
		System.err.println("ERROR : "+className+"."+functionName+" -> "+message);
		System.exit(0);
	}
}
