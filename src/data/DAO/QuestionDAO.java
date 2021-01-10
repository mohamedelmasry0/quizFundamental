package data.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.Answer;
import data.Question;


public class QuestionDAO {
	public List<Answer> answersList = new ArrayList<>();
	

	public List<Answer> getAnswersList() {
		return answersList;
	}


	public void setAnswersList(List<Answer> answersList) {
		this.answersList = answersList;
	}


public Connection getdbConnection() throws SQLException {
	
	Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123");
	return connection;
}




	
public void  Create(Question question,String topicStr,Answer answer) throws SQLException {
	Connection connection = getdbConnection();
	String query = "Insert INTO questions (question,difficulty, id,topic,answer) VALUES (?,?,?,?,?)";
	PreparedStatement preparedStatement = connection.prepareStatement(query);
	preparedStatement.setString(1, question.getQuestion());
	preparedStatement.setInt(2, question.getDifficulty());
	preparedStatement.setInt(3, question.getId());
	preparedStatement.setString(4, topicStr);
	preparedStatement.setString(5, answer.getAnswer());
	int  insertedRow = preparedStatement.executeUpdate();
	if(insertedRow>0) {
		System.out.println("A new question inserted....");
	}
	
	
	}
public List<Question> Read() throws SQLException {
	Connection connection = getdbConnection();
	String query = "select * from questions";
	PreparedStatement preparedStatement = connection.prepareStatement(query);
	ResultSet results = preparedStatement.executeQuery();
	List<Question> questions = new ArrayList<Question>();

	
	
	while (results.next()) {
		Answer answer = new Answer();
		String questionTitle = results.getString("question");
		String topic = results.getString("topic");
		int difficulty = results.getInt("difficulty");
		int id = results.getInt("id");
		String ans = results.getString("answer");
		Question question = new Question();
		question.setQuestion(questionTitle);
		question.setDifficulty(difficulty);
		question.topicsToStringArr(topic);
		question.setId(id);
		answer.setAnswer(ans);
		questions.add(question);
		answersList.add(answer);
		
		
	}
	return questions;
			
}
public void Update(Question questionUpdated) throws SQLException {
	Connection connection = getdbConnection();
	String query ="Update questions set question =? ,difficulty= ?  where id=?";
	PreparedStatement preparedStatement = connection.prepareStatement(query);
	preparedStatement.setString(1, questionUpdated.getQuestion());
	preparedStatement.setInt(2, questionUpdated.getDifficulty());
	preparedStatement.setInt(3, questionUpdated.getId());

	int  updatedRow  = preparedStatement.executeUpdate();
	if(updatedRow>0) {
		System.out.println("Question updated successfully");
	}
	
	
	
}

public void Delete(int questionId) throws SQLException {
	Connection connection = getdbConnection();
	String deleteQuery ="DELETE FROM questions Where" + "(id=?)";
	PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
	preparedStatement.setInt(1, questionId);
    preparedStatement.executeUpdate();
    System.out.println("The question is deleted successfully");
	
	
}


public List<Question> searchTopics(String[] topics,int difficulty) throws SQLException{
	
	Connection connection = getdbConnection();
	List<Question> questions = new ArrayList<Question>();
	
	String inParenthesis = "(?";
	for (int i = 1; i < topics.length; i++) {
		inParenthesis += ",?";
	}
	inParenthesis += ")";
	
	String query = "SELECT * FROM questions WHERE topic in " + inParenthesis + " and (?=-1 or difficulty = ?)";
	PreparedStatement prepareStatement = connection.prepareStatement(query);

	for (int i = 0; i < topics.length; i++) {
		prepareStatement.setString((i + 1), topics[i]);
	}
	
	
	prepareStatement.setInt((topics.length + 1), difficulty);
	prepareStatement.setInt((topics.length + 2), difficulty);
	ResultSet results = prepareStatement.executeQuery();

	while (results.next()) {
		Answer answer = new Answer();
		String questionTitle = results.getString("question");
		String topic = results.getString("topic");
		int diffi = results.getInt("difficulty");
		int id = results.getInt("id");
		String ans = results.getString("answer");
		Question question = new Question();
		question.setQuestion(questionTitle);
		question.setDifficulty(diffi);
		question.topicsToStringArr(topic);
		question.setId(id);
		answer.setAnswer(ans);
		questions.add(question);
		answersList.add(answer);
		
		
	}	
	//??????????????????????????????????????????????????????????????????????
return questions;
	
}
	public List<Question> search(Question questionSearch) throws SQLException {
	 	
			Connection connection = getdbConnection();
		 
			String query = "SELECT question,difficulty FROM questions WHERE "
			+ "(? is null or question LIKE ?)"
			+ "and (?=-1 or difficulty = ?) " + "or (topics = ?)" ;
			
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, "%" +questionSearch.getQuestion() + "%");
			prepareStatement.setString(2,"%"+questionSearch.getQuestion() + "%");
			prepareStatement.setInt(3, questionSearch.getDifficulty());
			prepareStatement.setInt(4, questionSearch.getDifficulty());
			ResultSet results = prepareStatement.executeQuery();
			
			List<Question> questions = new ArrayList<Question>();
			
			while (results.next()) {
				String questionTitle = results.getString("question");
				int difficulty = results.getInt("difficulty");
				Question question = new Question();
				question.setQuestion(questionTitle);
				question.setDifficulty(difficulty);
				System.out.println("question " +question.toString());
				questions.add(question);
				
			}
			
			connection.close();
			return questions;
	}
	
	
}


