package model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String comment;
	private String date;
	private LocalTime time;
	private boolean finished;

	public Meeting(long id, String comment, LocalTime time) {
		setId(id);
		setComment(comment);
		setTime(time);
	}

	public Meeting() {
	}

}
