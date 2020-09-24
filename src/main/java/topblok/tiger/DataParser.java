package topblok.tiger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataParser {

	public static final String PATH = "./res/";
			
	private static String GetExcelText(String file_name) {
		String text = null;
		try {
			File file = new File("./res/" + file_name);
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFExcelExtractor extractor = new XSSFExcelExtractor(wb);
			text = extractor.getText();
			extractor.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		
		return text;
	}
	
	// method will read excel sheet and collect basic data on students
	public static Map<Integer, Student> GatherStudents(String file_name) {
		Map<Integer, Student> students;
		// read excel files
		String text = DataParser.GetExcelText(file_name);
		String[] lines = text.split("\n");
		students = new HashMap<Integer, Student>(lines.length - 2);
		// parse individual data of student
		for(int i = 2; i < lines.length; i++) {
			String[] student_info = lines[i].split("\t");
			int id = Integer.parseInt(student_info[0]);
			students.put(id, new Student(id, student_info[1], student_info[2].charAt(0)));
		}
		return students;
	}
	
	public static void Apply_Test_Scores(Map<Integer, Student> students, String file_name) {
		// read excel files
		String text = DataParser.GetExcelText(file_name);
		String[] lines = text.split("\n");
		// parse individual data of student
		for(int i = 2; i < lines.length; i++) {
			String[] student_info = lines[i].split("\t");
			int id = Integer.parseInt(student_info[0]);
			students.get(id).setTestScore(Integer.parseInt(student_info[1]));
		}
	}
	
	
}
