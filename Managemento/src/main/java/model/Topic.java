package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String note;
	@OneToMany(mappedBy = "thema")
	private Set<Project> projectsList = new HashSet<>();
	@ManyToMany(mappedBy = "topics", fetch = FetchType.LAZY)
	private Set<Module> modules = new HashSet<>();

	public Topic(String name, String description) {
		setName(name);
		setDescription(description);
	}

	public Topic(String name, String note, Long id) {
		setName(name);
		setNote(note);
		setId(id);
	}

	public Topic() {
	}

	public void addModule(Module module) {
		this.modules.add(module);
		module.addTopic(this);
	}

	public void removeModule(Module module) {
		this.modules.remove(module);
		module.removeTopic(this);
	}

	public void addProject(Project project) {
		projectsList.add(project);
		project.setTopic(this);
	}

	public void removeProject(Project project) {
		projectsList.remove(project);
		project.setTopic(null);
	}

}
