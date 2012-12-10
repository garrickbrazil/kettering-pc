import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
				
		
		System.out.println("\n========== Directory ==========\n");

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
		
		
		System.out.println("\n========== Login ==========\n");

		// User info
		System.out.print("Username: ");
		user.setUsername(input.nextLine());
		System.out.print("Password: ");
		user.setPassword(input.nextLine());
		
		user.loginBanner();
		user.loginBlackboard();
		
		
		System.out.println("\n\n========== Storing ==========\n");
		Menu menu = new Menu();
		user.storeSchedule(201301);
		user.storeFinalGrades(201201);
		user.storeMidtermGrades(201201);
		user.storeCurrentGrades();
		user.storeTranscript();
		user.storeAccount();
		user.storeDynamicCourses(201301);

		
		
		System.out.println("\n\n========== Menu ==========\n");
		System.out.println(menu.toString());
		
		
		System.out.println("\n\n========== Current Grades ==========\n");
		for(int i = 0; i < user.getCurrentGrades().size(); i++) if(user.getCurrentGrades().get(i).getValidCourseGrade()) System.out.println("\n" + user.getCurrentGrades().get(i).toString());
		
		
		System.out.println("\n\n========== Final Grades ==========\n");
		for(int i = 0; i < user.getFinalGrades().size(); i++) System.out.println(user.getFinalGrades().get(i).toString());
		System.out.println("\n" + user.getUndergradSummary().toString());
		
		
		System.out.println("\n\n========== Midterm Grades ==========\n");
		for(int i = 0; i < user.getMidtermGrades().size(); i++) System.out.println(user.getMidtermGrades().get(i).toString());
		
		
		System.out.println("\n\n========== Account Total ==========\n");
		System.out.println(user.getAccountTotal().toString());
		
		
		System.out.println("\n\n========== Course Scheduler Demo for Math 102, Mech 100, and CS 101 ==========\n");
		
		// Demo courses
		List<String> givenIDs = new ArrayList<String>();
		givenIDs.add("MECH 100");
		givenIDs.add("MATH 102");
		givenIDs.add("CS 101");
		
		// Execute
		List<List<Course>> results = user.getClassOptions(givenIDs);
		
		
		// Print results
		System.out.println(results.size() + " " + ((results.size()==1)?"option.":"options."));
		for(List<Course> i : results){
			for(int j = 0; j < i.size(); j++) System.out.print(i.get(j).getCourseID() + "-" + i.get(j).getSection() + " ");
			System.out.println();
		}
		
		
		input.close();
		
	}

}
