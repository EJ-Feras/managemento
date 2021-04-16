package model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.Data;

import javax.persistence.CascadeType;

@Entity
@Data
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String note;
	private byte semester;
	private String mainUrl;
	private boolean isActive;
	private Double mark;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "projecttopic_id", nullable = true, updatable = true)
	private Topic topic;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectmodul_id", nullable = false, updatable = true)
	private Module module;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)

	private Set<Student> students = new HashSet<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<Sprint> sprints = new HashSet<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<URL> urls = new HashSet<>();

	/**
	 * Konstruktor project
	 * 
	 * @param name
	 * @param note
	 * @param semester
	 * @param isActive
	 * 
	 * @author Rajoub
	 */
	public Project(String name, String note, byte semester, boolean isActive, Module module) {
		setName(name);
		setNote(note);
		setSemester(semester);
		setIsActive(isActive);
		setId(id);
		setModule(module);
	}

	public Project() {
	}

	public void setIsActive(boolean isActive) {
		if (isActive) {
			this.isActive = true;
		} else {
			this.isActive = false;
		}
	}

	public void addUrl(URL url) {
		this.urls.add(url);
		url.setProject(this);
	}

	public void removeUrl(URL url) {
		this.urls.remove(url);
		url.setProject(null);
	}

	public void addSprint(Sprint sprint) {
		this.sprints.add(sprint);
		sprint.setProject(this);
	}

	public void removeSprint(Sprint sprint) {
		this.sprints.remove(sprint);
		sprint.setProject(null);
	}

	public void addStudent(Student student) {
		students.add(student);
		student.setProject(this);
	}

	public void removeStudent(Student student) {
		students.remove(student);
		student.setProject(null);
	}

	public void removeTopic(Topic topic) {
		topic.removeProject(this);
		this.setTopic(null);
	}

	public void removeModule(Module module) {
		module.removeProject(this);
		this.setModule(null);
	}

}
