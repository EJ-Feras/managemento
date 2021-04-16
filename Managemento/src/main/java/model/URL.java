package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class URL implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String url;
	@ManyToOne()
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public URL(String url, Project project) {
		setUrl(url);
		setProject(project);
	}

	public URL() {
	}

}
