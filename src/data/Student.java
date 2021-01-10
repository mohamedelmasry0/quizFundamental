package data;

public class Student {
private static final String User_name ="ME";
private static final int ID = 123;
String userName;

public Student() {
	
}
public Student(String userName, int id) {
	this.userName = userName;
	this.id = id;
}

int id;
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public boolean authentication(String name, int id ) {
	if(name.equals(name) && getId()==id ) {
	return true;
} else {
	return false;
}

}
}


