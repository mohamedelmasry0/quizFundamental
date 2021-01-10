package launcher;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

import data.Answer;
import data.MCQchoice;
import data.MCQquestion;
import data.Question;
import data.Student;
import data.DAO.MCQquestionDAO;
import data.DAO.QuestionDAO;
public class Launcher {
public static  void main(String[] args) throws SQLException {
	/*MCQquestionDAO mcq = new MCQquestionDAO();
	List<MCQquestion> results = mcq.Read();
	 for(Object result:results) {
		 System.out.println(result);
	 }*/
	
	/*String[] topics ={"life"};
	String topicStr = "life";
	MCQquestion mcqquestion = new MCQquestion("What is going on with me",1,4,topics);
	
	List<MCQchoice> testChoiceList = new ArrayList<>();
	
	MCQchoice mcQchoice1 = new MCQchoice("choice1",false);
	MCQchoice mcQchoice2 = new MCQchoice("choice2",false);
	MCQchoice mcQchoice3 = new MCQchoice("choice3",true);
	MCQchoice mcQchoice4 = new MCQchoice("choice4",false);
	
	testChoiceList.add(mcQchoice1);
	testChoiceList.add(mcQchoice2);
	testChoiceList.add(mcQchoice3);
	testChoiceList.add(mcQchoice4);
	
	MCQquestionDAO mcq = new MCQquestionDAO();
	mcq.Create(mcqquestion,topicStr,testChoiceList);
	
	
    String[] topics = {"java","css","html"};
    QuestionDAO questionDAO = new QuestionDAO();
    questionDAO.searchTopics(topics, 1);*/
    

	
	Scanner scanner = new Scanner(System.in); 
	String yesOrno = "y";
	
	while(yesOrno.equalsIgnoreCase("y")) {
	     	printMenu();
	     
		    int userOption = scanner.nextInt();
		    
		    if (userOption ==1) {
		    	create();	
		    }
		    
		    else if (userOption == 2) {
		    	read();
		    	    
				}
		    
		    else if (userOption == 3) {
		    	update(); 	
		  		}
		    
		    else if (userOption == 4) {
				delete();	
		  		}
		    
		    else if (userOption == 5) {
				search();
				}
		    
			else if (userOption==6) {
				///???????????????????????????????????????????????????????????????????????????????????????????????????????
				quiz();  
				}
		    System.out.println("Do you want to continue ? press y to continue ,press any key to exit !");
		    yesOrno = scanner.next();
		    scanner.nextLine();
		
	}
	scanner.close(); 
	
	
	}

public static void create() throws SQLException {
	Scanner scanner = new Scanner (System.in);
	Question question = new Question();
	Answer answer = new Answer();
    QuestionDAO questionDAO = new QuestionDAO();
    
    System.out.println("Enter question id:");
    int id = scanner.nextInt();
    question.setId(id);
    
    System.out.println("Enter question:");
    String ques = scanner.next();
    //???????????????????????????????????????????????????????????????????????????????????????????
    ques+=scanner.nextLine();
    question.setQuestion(ques);
   
    System.out.println("Enter difficulty:");
    int diff = scanner.nextInt();
    question.setDifficulty(diff);
    
    System.out.println("Enter Answer:");
    String ans = scanner.next();
    ans+=scanner.nextLine();
    System.out.println("ans " +ans);
    answer.setAnswer(ans);
    
	System.out.println("How many topics you want to add : ");
    int topicsLength = scanner.nextInt();
    scanner.nextLine();
      String[] topicsList =  new String[topicsLength];

	 for(int i = 0; i < topicsLength; i++) {
		 System.out.println("Enter topic "+(i+1)+":");
		 topicsList[i]=scanner.nextLine();
	 	}
	  question.setTopics(topicsList);
	  //?????????????????????????????????????????????????????????????????????????????????????????????
	  String topicStr = question.topicsToString(topicsList);
	  questionDAO.Create(question,topicStr,answer);
	
	}
public static void printMenu() {
Scanner scanner = new Scanner (System.in);
Student student = new Student();

	
    System.out.println("Welcome!");
   
    System.out.println("Enter ur name");
    String userName = scanner.nextLine();
    student.setUserName(userName);
   
    System.out.println("Enter ur ID");
    int id = scanner.nextInt();
    student.setId(id);
    
    if (!student.authentication(userName, id)) {
    	System.out.println("Unauthorized");
    	scanner.close();
    	return;
    	}
	System.out.println("Press 1 for creating a question\nPress 2 for Reading the questions\nPress 3 for updating a question\nPress 4 for deleting a question\nPress 5 for searching for a question\npress 6 for starting a quiz");
	}

public static void read() throws SQLException {
	System.out.println("fetching the data.....");
	
	QuestionDAO questionDAO = new QuestionDAO();
	List<Question> list =questionDAO.Read(); 
	List<Answer> answers = questionDAO.getAnswersList();
	Iterator<Answer> itAns = answers.iterator();
	
	 for(int i = 0; i < list.size(); i++) {
         System.out.println("result " +(i+1) +",id: "+list.get(i).getId()+", question: "+list.get(i).getQuestion()+", difficulty: "+list.get(i).getDifficulty()+", topics: "+list.get(i).topicsToString(list.get(i).getTopics())+" Answer : "+answers.get(i).getAnswer());
         }
	 
     }

public static void update() throws SQLException {
	Scanner scanner = new Scanner (System.in);
	QuestionDAO questionDAO = new QuestionDAO();
	Question question = new Question();
	System.out.println("Choose the question based on id:");
	int id = scanner.nextInt(); 
	question.setId(id);
	System.out.println("Press 1 if u want to update only the question\n press 2 if u want to update only the difficulty\n press 3 if u want to update both question and difficulty");
	int updateMethod = scanner.nextInt();
	if(updateMethod==3 ) {
		System.out.println("Type the difficulty level:");
		int dif = scanner.nextInt();
		question.setDifficulty(dif);
		System.out.println("Type the question update:");
		String ques = scanner.nextLine();
		ques =scanner.nextLine();
		question.setQuestion(ques);
		questionDAO.Update(question);
		}
	else if (updateMethod==1) {
		//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		System.out.println("Type the question update:");
		String ques = scanner.nextLine();
		ques =scanner.nextLine();
		question.setQuestion(ques);
		question.setDifficulty(question.getDifficulty());
		questionDAO.Update(question);
	}
	else if (updateMethod==2) {
		//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		System.out.println("Type the difficulty level:");
		int dif = scanner.nextInt();
		question.setDifficulty(dif);
		question.setQuestion(question.getQuestion());
		questionDAO.Update(question);
		}
	printMenu();
	}

public static void delete() throws SQLException {
	Scanner scanner = new Scanner (System.in);
	QuestionDAO questionDAO = new QuestionDAO();
	System.out.println("Enter the id of the question u want to delete:");
	int id = scanner.nextInt();
	questionDAO.Delete(id);
	printMenu();
}

public static void search() throws SQLException {
	Scanner scanner = new Scanner(System.in);
	QuestionDAO questionDAO = new QuestionDAO();
	Question question = new Question();
	System.out.println("Press 1 if u want to search the question based on keyword in the question.\n Press 2 if u want to search the question based on the difficulty.\n Press 3 if u want to search based on both question and difficulty. ");
	int searchKind = scanner.nextInt();
	if (searchKind==3) {
		System.out.println("Enter a key word:");
		String keyWord = scanner.next();
		scanner.nextLine();
		question.setQuestion(keyWord);
		System.out.println("Enter the difficulty:");
		int dif = scanner.nextInt();
		question.setDifficulty(dif);
		questionDAO.search(question);
	}
	else if (searchKind ==1) {
		System.out.println("Enter a key word:");
		String keyWord = scanner.next();
		scanner.nextLine();
		question.setQuestion(keyWord);
		question.setDifficulty(-1);
		questionDAO.search(question);
	}
	else if (searchKind==2) {
		///////////////////???????????????????????????????????????????????????????????????????????????????????
		System.out.println("Enter the difficulty:");
		int dif = scanner.nextInt();
		question.setDifficulty(dif);
		question.setQuestion(null);
		questionDAO.search(question);
		}
	
	}

public static void quiz() {
	String yesOrno = "y";
	Question question = new Question();
	QuestionDAO questionDAO = new QuestionDAO();
	Scanner scanner = new Scanner(System.in);
	System.out.println("Write the number of topics");
	  int topicsLength = scanner.nextInt();
	    scanner.nextLine();
	 
	      String[] topicsList =  new String[topicsLength];

		 for(int i = 0; i < topicsLength; i++) {
			 System.out.println("Enter topic "+(i+1)+":");
			 topicsList[i]=scanner.nextLine();
		 	}
		 
		   System.out.println("Enter difficulty:");
		    int diffcultyLevel = scanner.nextInt();
	 
    try {
    	List<Question> questions = questionDAO.searchTopics(topicsList, diffcultyLevel);
    	Iterator<Question> it = questions.iterator();
    	List<Answer> ansList = questionDAO.getAnswersList();
    	Iterator<Answer> itAns = ansList.iterator();
    	
        System.out.println("Do you want to take quiz ? press y to continue,press any key to quit !");
	    yesOrno = scanner.next();
	    scanner.nextLine();
	    if( yesOrno.equalsIgnoreCase("y")) {
	    	
	    
	    int index = 1;
	    while(it.hasNext()) {
    		System.out.println(index+": "+it.next().getQuestion());
    		index++;
    	}
	    
		String[] userAnswers = new String[questions.size()];
		for (int i = 0; i < userAnswers.length; i++) {
			System.out.println("Enter Answer " + (i + 1) + ":");
			userAnswers[i] = scanner.nextLine();
		}

		int correctAnswersCount = 0;
		while (itAns.hasNext()) {
			Answer currentAnswer = itAns.next();
			for (String ans : userAnswers) {
				if (ans.equals(currentAnswer.getAnswer())) {
					correctAnswersCount++;

				}

			}
			
		}
		System.out.println("your score :"+ correctAnswersCount +"/"+ansList.size() );
	    }
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 
		
	  
}

}