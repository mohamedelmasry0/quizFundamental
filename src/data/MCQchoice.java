package data;

public class MCQchoice {
	String choice;
	boolean valid;
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public MCQchoice() {
	}
	public MCQchoice(String choice, boolean valid,int id) {
		this.choice = choice;
		this.valid = valid;
		this.id =id;
	}

}
