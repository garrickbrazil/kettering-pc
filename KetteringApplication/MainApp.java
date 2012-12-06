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
		
		
		// User
		System.out.print("Username: ");
		user.setUsername(input.nextLine());
		
		
		// Password
		System.out.print("Password: ");
		user.setPassword(input.nextLine());
		
		
		// Login
		user.loginBanner();
		user.loginBlackboard();
		
		
		// Store
		user.storeSchedule(201301);
		user.storeGrades();
		
		
		System.out.println("\nGrades!\n");
		
		//Print known grades
		for (int i = 0; i < user.getGrades().size(); i++){
			Grade current = user.getGrades().get(i);
			
			if(current.getValidCourseGrade()){
				System.out.println(current.getCourseName());
				
				for(int j = 0; j < current.getGradeItems().size(); j++){
					GradeItem currentDetail = current.getGradeItems().get(j);
					if (currentDetail.getValidGrade()){
						System.out.print("\n\t" + currentDetail.getGradeName());
						System.out.print(" Score: " + currentDetail.getScore());
						System.out.print(" Possible: " + currentDetail.getPointsPossible());
						System.out.print(" Percentage: " + Math.round(currentDetail.getScore()/currentDetail.getPointsPossible()*100) + "%");
					}	
				}
				
				System.out.println("\n\tAdjusted Total: " + current.getTotal());
				System.out.println("\tPoints Possible: " + current.getPointsPossible());
				System.out.println("\tPercentage: " + Math.round(current.getTotal()/current.getPointsPossible() * 100) + "%");
			}
		}
		
		input.close();
		
	}

}
