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
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int matriculationNumber;
	private String firstName;
	private String lastName;
	private double grade;
	@ManyToOne()
	@JoinColumn(name = "Project_id", nullable = false)
	private Project project;

	public Student(int matriculationNumber, String firstName, String lastName, double grade) {
		setMatriculationNumber(matriculationNumber);
		setFirstName(firstName);
		setLastName(lastName);
		setGrade(grade);
	}

	public Student(int matriculationNumber, String firstName, String lastName) {
		setMatriculationNumber(matriculationNumber);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public Student() {
	}

}
