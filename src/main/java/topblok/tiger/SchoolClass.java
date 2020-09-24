package topblok.tiger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SchoolClass {
    
	private Map<Integer, Student> students;
	private int average = 0;
	
	public SchoolClass() {
		students = DataParser.GatherStudents("Student Info.xlsx");
		DataParser.Apply_Test_Scores(students, "Test Scores.xlsx");
		DataParser.Apply_Test_Scores(students, "Test Retake Scores.xlsx");
		CalculateClassAverage();
	}
	
	public void CalculateClassAverage() {
		average = 0;
		students.forEach((id, student)->{
			average += student.getTestScore();
		});
		average = average/students.size();
	}
	
	public List<Student> getStudentsIDByMajorAndGender(String major, char gender) {
		List<Student> collected_students = new ArrayList<Student>();
		// gather all students
		students.forEach((id, student)->{
			if(student.getMajor().equals(major) && student.getGender() == gender)
				collected_students.add(student);
		});
		// sort students by id
		collected_students.sort(new Comparator<Student>() {

			public int compare(Student student1, Student student2) {
				return (int) (student1.getStudentId() - student2.getStudentId());
			}
			
		});
		return collected_students;
	}
	
	public static void main( String[] args ){
		SchoolClass school_class = new SchoolClass();
		List<Student> query = school_class.getStudentsIDByMajorAndGender("computer science", 'F');		
		
		// convert everything to JSON
		String json_list = "[\"" + Integer.toString(query.get(0).getStudentId());
		for(int i = 1; i < query.size(); i++) {
			json_list += "\", \"" + Integer.toString(query.get(i).getStudentId());
		}
		json_list += "\"]";
		
		String json = "{ \"id\": \"awaichunas@gmail.com\", \"name\": \"Alec Waichunas\", \"average\": " + school_class.average
						+ ", \"studentIds\": " + json_list + " }";
		System.out.println(json);
		// send post request
		try {
			URL url = new URL("http://54.90.99.192:5000/challenge");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			byte[] data_packet = json.getBytes(StandardCharsets.UTF_8);
			http.setFixedLengthStreamingMode(data_packet.length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			//send data
			OutputStream os = http.getOutputStream();
			os.write(data_packet);
			os.close();
			//check if incoming data?
			BufferedReader in_read = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String in;
			while((in = in_read.readLine()) != null)
				System.out.println(in);
			
			http.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
