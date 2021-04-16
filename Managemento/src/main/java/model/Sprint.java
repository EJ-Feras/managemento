package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

import java.time.LocalDate;



@Entity
@Data
public class Sprint implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int number;
	private String description;
	private String note;
	private LocalDate start;
	private LocalDate end;
	@ManyToOne()
	@JoinColumn(name = "Project_id", nullable = false)
	private Project project;

	/**
	 * Konstruktor Sprint
	 * 
	 * @param idSprints
	 * @param description
	 * @param noteen
	 * @param Start
	 * @param End
	 */

	public Sprint(int id, String description, String note, LocalDate start, LocalDate end) {
		setId(id);
		setDescription(description);
		setNote(note);
		setStart(start);
		setEnd(end);
	}

	public Sprint() {
	}

}
