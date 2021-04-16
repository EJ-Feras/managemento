package model;

import java.util.LinkedList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;


@Entity
@Data
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String semester;
	private String notes;
	@OneToMany(mappedBy = "modul", cascade = CascadeType.ALL)
	private List<Project> projects = new LinkedList<>();
	@ManyToMany()
	@JoinTable(name = "modul_thema", joinColumns = { @JoinColumn(name = "modul_id") }, inverseJoinColumns = {
			@JoinColumn(name = "projectthema_id") })
	private List<Topic> topics = new LinkedList<>();

	public Module(String name, String semester) {

		setName(name);
		setSemester(semester);

	}

	public Module() {
	}

	public void addProject(Project project) {
		this.projects.add(project);
		project.setModule(this);
	}

	public void removeProject(Project project) {
		this.projects.remove(project);
		project.setModule(null);
	}

	public void addTopic(Topic thema) {
		this.topics.add(thema);
		thema.getModules().add(this);
	}

	public void removeTopic(Topic thema) {
		this.topics.remove(thema);
		thema.getModules().remove(this);
	}

}
