package topblok.tiger;

public class Student {

	// initialize all student variables
	private final int studentId;
	private String major;
	private char gender;
	private boolean test_retake = false;
	private int test_score = -1;
	
	// student constructor
	public Student(int studentId, String major, char gender) {
		this.studentId = studentId;
		this.major = major;
		this.gender = gender;
	}
	
	// setter for test score
	public void setTestScore(int score) {
		// record if they re took the test
		if(test_score >= 0) test_retake = true;
		// set test score to maximum score
		this.test_score = Math.max(test_score, score);
	}
	
	// getters for rest of student variables
	public int getTestScore() {
		return this.test_score;
	}
	
	public char getGender() {
		return this.gender;
	}
	
	public String getMajor() {
		return this.major;
	}
	
	public int getStudentId() {
		return this.studentId;
	}
	
	public boolean isRetake() {
		return this.test_retake;
	}
}
