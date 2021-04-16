package service;

import java.util.List;

import dao.StudentDao;
import dao.TopicDao;
import model.Project;
import model.Student;
import model.Topic;

public class StudentService {

	/**
	 * Uebergibt die Werte an die Datenbank zum hinzufuegen.
	 * 
	 * @return true, wenn erfolgreich eingefuegt
	 */
	public static boolean addStudent(Student student) {
		if (!exists(student.getMatriculationNumber())) {
			StudentDao.addStudent(student);
			return true;
		}
		return false;
	}
	
	/**
	 * einen Student aktualisieren.
	 * 
	 * @param thema
	 * @author Feras
	 */
	public static void updateStudent(Student student) {
		StudentDao.updateStudent(student);
	}


	/**
	 * Uebergibt die Werte an die Datenbank zum hinzufuegen.
	 * 
	 * @return true, wenn erfolgreich eingefuegt
	 */
	public static Student findStudent(int matrikelnummer) {

		if (exists(matrikelnummer)) {
			Student student = StudentDao.findStudent(matrikelnummer);
			return student;
		}

		return null;
	}

	/**
	 *
	 * 
	 * @return Liste aller Studenten.
	 */
	public static List<Student> findStudents() {
		List<Student> student = StudentDao.findStudents();
		return student;
	}

	public static void deleteStudent(Student student) {
		StudentDao.deleteStudent(student);
	}

	/**
	 *
	 * 
	 * @return Liste aller Studenten eines Projektes.
	 */
	public static List<Student> findStudents(Project project) {
		List<Student> student = StudentDao.findStudents(project);
		return student;
	}

	/**
	 * Ueberpruft,ob die Matrikelnummer in der Datenbank vorhanden ist.
	 * 
	 * @param matrikelnummer
	 * @return
	 */
	public static boolean exists(int matrikelnummer) {
		return StudentDao.exists(matrikelnummer);
	}

}
