package data.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

import data.Answer;
import data.MCQchoice;
import data.MCQquestion;
import data.Question;

public class MCQquestionDAO {
	public Connection getdbConnection() throws SQLException {
		
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123");
		return connection;
	}
	
	public void  Create(MCQquestion mcqquestion, String topicStr,List<MCQchoice> testChoiceList) throws SQLException {
		Connection connection = getdbConnection();
		
		
		String query = "INSERT INTO mcq_questions (question,difficulty,topic) VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, mcqquestion.getQuestion());
		preparedStatement.setInt(2, mcqquestion.getDifficulty());
		preparedStatement.setString(3, topicStr);
		
		int  insertedRow = preparedStatement.executeUpdate();
		if(insertedRow>0) {
			System.out.println("A new MCQ question inserted....");
		}
		
		String choiceSelectQuery = "select mcq_id from mcq_questions where question=?";
		PreparedStatement preparedStatement1 = connection.prepareStatement(choiceSelectQuery);
		preparedStatement1.setString(1, mcqquestion.getQuestion());
		ResultSet resultSet = preparedStatement1.executeQuery();
		
		int id=0;
		while(resultSet.next()){
			id = resultSet.getInt("mcq_id");
		}
		System.out.println("result "+ id);
		
		
		
		for(MCQchoice mc: testChoiceList) {
			String insertChoiceQuery = "INSERT INTO mcq_choices (mcq_id,choice,is_valid) VALUES (?,?,?)";
			PreparedStatement preparedStatementChoice = connection.prepareStatement(insertChoiceQuery);
			preparedStatementChoice.setInt(1, id);
			preparedStatementChoice.setString(2, mc.getChoice());
			preparedStatementChoice.setBoolean(3, mc.isValid());
			int insertedRow1 = preparedStatementChoice.executeUpdate();
			if(insertedRow1>0) {
				System.out.println("A new MCQ question inserted....");
			}
					
		}
		
		
				
		
}
	
	public List<MCQquestion> Read() throws SQLException {
		Connection connection = getdbConnection();
		//String query = "select m.mcq_id,m.question,m.difficulty,m.topic,c.choice_id,c.choice,c.is_valid from mcq_choices as c inner join mcq_questions as m on m.mcq_id=c.mcq_id";
		String queryQuestions = "select * from mcq_questions";
		PreparedStatement preparedStatement = connection.prepareStatement(queryQuestions);
		ResultSet resultsQuestions = preparedStatement.executeQuery();
		
		String queryChoices = "select * from mcq_choices";
		PreparedStatement preparedStatement1 = connection.prepareStatement(queryChoices);
		ResultSet resultsChoices = preparedStatement1.executeQuery();
		
		List<MCQquestion> MCQquestions = new ArrayList<>();
		List<MCQchoice> choicesList = new ArrayList<>();
	
			while (resultsQuestions.next()) {
			
				MCQquestion mcq = new MCQquestion();
				
				String question = resultsQuestions.getString("question");
				int difficulty = resultsQuestions.getInt("difficulty");
				int id = resultsQuestions.getInt("mcq_id");
				String topic = resultsQuestions.getString("topic");
				String[] topicArr = {topic};
				mcq.setDifficulty(difficulty);
				mcq.setId(id);
				mcq.setQuestion(question);
				mcq.setTopics(topicArr);
				MCQquestions.add(mcq);	
		};
		
		while( resultsChoices.next()) {
			MCQchoice mcqC = new MCQchoice();
			    int id_choice = resultsChoices.getInt("mcq_id"); 
				String choice = resultsChoices.getString("choice"); 
				boolean is_valid = resultsChoices.getBoolean("is_valid");
				mcqC.setChoice(choice);
				mcqC.setValid(is_valid);
				mcqC.setId(id_choice);
				choicesList.add(mcqC);
		}
		
		for(int i=0;i< MCQquestions.size();i++) {
			 System.out.println(MCQquestions.get(i).getQuestion());
			for(int j=0;j< choicesList.size();j++) {
				if(MCQquestions.get(i).getId()==choicesList.get(j).getId()) {
					 System.out.println(choicesList.get(j).getChoice() + ":"+ choicesList.get(j).isValid());
				}
				
			}
			 System.out.println();
		}
				
		return MCQquestions;
				
	}
	
	
		
}
