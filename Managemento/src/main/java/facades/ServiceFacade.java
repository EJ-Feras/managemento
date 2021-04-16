package facades;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Module;
import model.Project;
import model.Sprint;
import model.Student;
import model.Meeting;
import model.Topic;
import model.URL;
import service.ModuleService;
import service.ProjectService;
import service.ReminderService;
import service.SprintService;
import service.StatisticsService;
import service.StudentService;
import service.TopicService;
import service.UrlService;

public class ServiceFacade {

	/***********************************************************
	 * 
	 * Statistiken
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * Gibt eine Liste mit den eindeutigen Namen aller Themen zurueck.
	 * 
	 * @return List<Topic>
	 * @author Feras
	 */
	public static List<String> findDistinctTopics() {
		return StatisticsService.findDistinctTopics();
	}

	/**
	 * Gibt eine <String, Long> HashMap zuruckk, mit String fur den Themennamen und
	 * Long fuer die Haeufigkeit
	 * 
	 * @return HashMap <String,Long>
	 * @author Feras
	 */
	public static Map<String, Long> findTopicsFrequency() {
		return StatisticsService.findTopicsFrequency();
	}

	/**
	 * Gibt eine <Double, Long> HashMap zuruckk, mit Double fur den Noten und Long
	 * fuer die Haeufigkeit der Note.
	 * 
	 * @return
	 * @author Feras
	 */
	public static Map<Double, Long> findGradesFrequency() {

		return StatisticsService.findGradesFrequency();
	}

	/***********************************************************
	 * 
	 * Module
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * ein Module speichern oder aktualisieren .
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void addModule(Module module) {
		ModuleService.addModule(module);
	}

	/**
	 * Gibt alle Module zurueck.
	 * 
	 * @return List<Module>
	 * @author Feras
	 */
	public static List<Module> findAllModules() {
		return ModuleService.findAllModules();

	}
	
	public static Module findModuleByName(String name) {
		return ModuleService.findModuleByName(name);
	}

	/**
	 * ein bestimmtes Module loeschen.
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void deleteModule(Module module) {
		ModuleService.deleteModule(module);
	}

	public static void updateModule(Module module) {
		ModuleService.updateModule(module);

	}

	/**
	 * 
	 * @param toBeUpdated
	 * @param name
	 * @author Feras
	 */
	public static void updateModule(Module toBeUpdated, String name) {
		ModuleService.updateModule(toBeUpdated, name);
	}

	public static void updateModule(Module toBeUpdated, Topic topic) {
		ModuleService.updateModule(toBeUpdated, topic);
	}

	/***********************************************************
	 * 
	 * Project
	 * 
	 * @author Feras
	 ***********************************************************/

	public static void saveORUpdate(Project project) {

		ProjectService.saveORUpdate(project);

	}

	public static void deleteProject(Project project) {

		ProjectService.deleteProject(project);
	}

	public static Project findProject(Project project) {

		return ProjectService.findProject(project);

	}

	public static List<Project> findAllProjects(Module module) {

		List<Project> modules = ProjectService.findAllProjects(module);
		return modules;
	}

	/***********************************************************
	 * 
	 * Topic
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * Gibt alle Themen zurueck , die in dem Module uebergebenen sind.
	 * 
	 * @param module
	 * @return List<Topic>
	 * @author Feras
	 */
	public static List<Topic> findAllTopics(Module module) {
		return TopicService.findAllTopics(module);

	}

	/**
	 * Gibt ein bestimmtes Topic zurueck.
	 * 
	 * @param module Das Module, zu dem das Topic gehört.
	 * @param tpoic
	 * @return Topic
	 * @author Feras
	 */
	public static Topic findTopic(Module module, Topic tpoic) {
		return TopicService.findTopic(module, tpoic);

	}

	public static void saveTopic(Topic thema) {
		TopicService.saveTopic(thema);

	}

	public static void updateTopic(Topic thema) {
		TopicService.updateTopic(thema);
	}

	/**
	 * das Topic loeschen, ohne eine andere Entitaet zu beeinflussen.
	 * 
	 * @param topic
	 * @author Feras
	 */
	public static void deleteTopic(Topic topic) {
		TopicService.deleteTopic(topic);
	}

	/***********************************************************
	 * 
	 * Sprint
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * Gibt eine Liste mit allen Sprints eines bestimmten Projektes.
	 * 
	 * @param project
	 * @return Liste
	 * @author Feras
	 */
	public static List<Sprint> findAllSprints(Project project) {
		return SprintService.findAllSprints(project);
	}

	public static List<Sprint> findSortedSprints(Project project) {
		return SprintService.findSortedSprints(project);
	}

	public static Integer findSprintsNumber(Project project) {
		return SprintService.findSprintsNumber(project);

	}

	public static void saveSprint(Sprint sprint) {

		SprintService.saveSprint(sprint);

	}

	public static void updateSprint(Sprint sprint) {

		SprintService.updateSprint(sprint);

	}

	public static void deleteSprint(Sprint sprint) {

		SprintService.deleteSprint(sprint);
	}

	/***********************************************************
	 * 
	 * URL
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<URL> findURLs(Project project) {

		return UrlService.findURLs(project);
	}

	public static void addURL(URL url) {

		UrlService.addURL(url);

	}

	public static void updateURL(URL toBeUpdatedURL) {
		UrlService.updateURL(toBeUpdatedURL);

	}

	public static void deleteURL(URL url) {
		UrlService.deleteURL(url);
	}

	/***********************************************************
	 * 
	 * Meeting
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<Meeting> findAllReminders() {

		ObservableList<Meeting> allReminders = FXCollections.observableList(ReminderService.findAllReminders());
		return allReminders;

	}

	public static Meeting findReminder(Meeting rem) {

		return ReminderService.findReminder(rem);
	}

	public static void addReminder(Meeting reminder) {
		ReminderService.addReminder(reminder);

	}

	public static void updateReminder(Meeting oldRem) {
		ReminderService.updateReminder(oldRem);
	}

	public static void updateReminderStatus(Meeting oldRem) {
		ReminderService.updateReminderStatus(oldRem);
	}

	public static void deleteReminder(Meeting reminder) {
		ReminderService.deleteReminder(reminder);
	}

	/***********************************************************
	 * 
	 * Student
	 * 
	 * 
	 ***********************************************************/

	/**
	 * Erfragt von der Datenbank alle Studenten, die aktuell an irgendeinem Project
	 * teilnehmen
	 * 
	 * @return Liste der Studenten
	 */
	public static List<Student> findStudents() {
		return StudentService.findStudents();
	}

	/**
	 * Erfragt von der Datenbank einen Student.
	 * 
	 * @return Student
	 */
	public static Student findStudent(int matrikelnummer) {
		return StudentService.findStudent(matrikelnummer);
	}

	public static boolean exists(Student student) {
		return StudentService.exists(student.getMatriculationNumber());
	}

	/**
	 * Erfragt von der Datenbank alle Studenten, die aktuell an einem bestimmten
	 * Project teilnehmen
	 * 
	 * @return Liste der Studenten
	 * @author Feras
	 */
	public static List<Student> findStudents(Project project) {
		List<Student> students = StudentService.findStudents(project);
		return students;
	}

	public static void deleteStudent(Student student) {
		StudentService.deleteStudent(student);
	}

	/**
	 * Fuegt in der Datenbank einen neuen Studenten hinzu
	 * 
	 * @return true, wenn erfolgreich eingefuegt
	 */
	public static boolean addStudent(Student student) {
		return StudentService.addStudent(student);
	}

	public static void updateStudent(Student student) {
		StudentService.updateStudent(student);
	}
}
