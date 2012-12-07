import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.codec.EncoderException;
import org.apache.http.client.ClientProtocolException;

/********************************************************************
 * Class: MainApp
 * Purpose: runs and controls the main KU Application
/*******************************************************************/
public class MainApp {
	
	public static void main(String[] args) throws EncoderException, ClientProtocolException, IOException {
	
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
		Menu menu = new Menu();
		user.storeSchedule(201301);
		user.storeGrades();
		
		
		// Print grades
		for(int i = 0; i < user.getGrades().size(); i++) if(user.getGrades().get(i).getValidCourseGrade()) System.out.println("\n" + user.getGrades().get(i).toString());
		
		
		// Print quick menu
		System.out.println("\nMenu!");
		System.out.println(menu.getFriday().getDinner().getGrill());
		
		input.close();
		
	}

}
