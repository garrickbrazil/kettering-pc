import java.util.Scanner;

import org.apache.commons.codec.EncoderException;

/********************************************************************
 * Class: MainApp
 * Purpose: runs and controls the main KU Application
/*******************************************************************/
public class MainApp {
	
	public static void main(String[] args) throws EncoderException {
	
		// Objects
		Scanner input = new Scanner(System.in);
		Student user = new Student();
		
		
		// User info
		System.out.print("Username: ");
		user.setUsername(input.nextLine());
		System.out.print("Password: ");
		user.setPassword(input.nextLine());
		
		
		// Login
		user.loginBanner();
		user.loginBlackboard();
		
		
		// Store
		user.storeSchedule(201301);
		user.storeGrades();
		
		
		// Print grades
		for(int i = 0; i < user.getGrades().size(); i++) if(user.getGrades().get(i).getValidCourseGrade()) System.out.println("\n" + user.getGrades().get(i).toString());
		
		input.close();
		
	}

}
