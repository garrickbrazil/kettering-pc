import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		DynamicCourses dyno = new DynamicCourses();
		
		
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
		dyno.storeDynamicCourses(201301);

		
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
		
		
		System.out.println("\n\n========== Course Scheduler Valid Choices==========\n");
		
		// Demo courses
		List<String> givenIDs = new ArrayList<String>();
		String schedulerResponse = "";
		
		while(!schedulerResponse.equals("2")){
			
			if(givenIDs.size() > 0) givenIDs = new ArrayList<String>();
			
			// Course choices
			int factor = 0;
			for (String str : dyno.getDynamicCourseIDs()){
				if (factor == 6){ System.out.println(str + "\u00A0\u00A0\u00A0\u00A0\t"); factor = 0; }
				else{ System.out.print(str + "\u00A0\u00A0\u00A0\u00A0\t"); factor++; }
			}
			
			
			String inputStr = "";
			System.out.println("\n\nPlease enter in courses you wish to add and then choose done.");
			while(!inputStr.equals("3")){
				
				System.out.println("\n1. Add course");
				System.out.println("2. See current");
				System.out.println("3. Done");
				
				inputStr = input.nextLine();
				
				if(inputStr.equals("1")){
					System.out.print("\nCourse: ");
					givenIDs.add(input.nextLine());	
				}
				
				else if(inputStr.equals("2")){
					System.out.print("\nCourses added: ");
					for(String course : givenIDs){
						System.out.print(course + "\t");
					}
				}
			}
			
			// Execute
			dyno.setClassOptions(givenIDs);
			
			
			// Print results
			System.out.println("\n" + dyno.getWorkingSchedules().size() + " " + ((dyno.getWorkingSchedules().size()==1)?"option.":"options."));
			for(List<Course> i : dyno.getWorkingSchedules()){
				for(int j = 0; j < i.size(); j++) System.out.print(i.get(j).getCourseID() + "-" + i.get(j).getSection() + " ");
				System.out.println();
			}
		
			System.out.println("\n\n1. Go again\n2. Exit");
			schedulerResponse = input.nextLine();
		}
		
		input.close();
		
	}

}
