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
		Directory dir = new Directory();
		
		
		
		System.out.println("\n========== Directory Test ==========\n");

		// Directory info
		System.out.print("Search query: ");
		String search = input.nextLine();
		System.out.println("\n1. Student\n2. Faculty");
		String type = input.nextLine();
		
		// Search
		if(type.equals("1")) dir.search(search, "student");
		else if(type.equals("2")) dir.search(search, "faculty");
		else System.exit(-1);
		
		
		// Print search results
		if(type.equals("1")) System.out.println(dir.getStudentQuery().toString());
		else System.out.println(dir.getFacultyQuery().toString());
		
		
		System.out.println("\n========== Login Test ==========\n");

		// User info
		System.out.print("Username: ");
		user.setUsername(input.nextLine());
		System.out.print("Password: ");
		user.setPassword(input.nextLine());
		
		
		// Login
		user.loginBanner();
		user.loginBlackboard();
		
		
		
		System.out.println("\n\n========== Storing ==========\n");
		Menu menu = new Menu();
		user.storeSchedule(201301);
		user.storeGrades();
		user.storeTranscript();
		
		
		System.out.println("\n\n========== Grades Print==========\n");
		for(int i = 0; i < user.getGrades().size(); i++) if(user.getGrades().get(i).getValidCourseGrade()) System.out.println("\n" + user.getGrades().get(i).toString());
		
		
		
		System.out.println("\n\n========== Menu Print ==========\n");
		System.out.println(menu.toString());
		
		
		input.close();
		
		
	}

}
