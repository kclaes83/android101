package org.coursera.mutibo.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
//	@RestResource(exported = false)
	@OneToOne(optional = false)
	@JoinColumn(name = "MOVIESET_ID", nullable = false)
	private Movieset movieset;
	
	private int value;
	
	private String comment;
	
	// Hibernate requires default constructor
	@SuppressWarnings("unused")
	private Rating() {
	}

	public Rating(Movieset movieset, int value) {
		this.movieset = movieset;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Movieset getMovieset() {
		return movieset;
	}

	public void setMovieset(Movieset movieset) {
		this.movieset = movieset;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", movieset=" + movieset + ", value=" + value + ", comment="
				+ comment + "]";
	}
}
