package org.coursera.mutibo.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "MOVIE_ID", nullable = false)
	private Movie movie;
	
	private boolean isCorrect;
	
	// Hibernate requires default constructor
	@SuppressWarnings("unused")
	private Answer() {
	}
	
	public Answer(Movie movie, boolean isCorrect) {
		this.movie = movie;
		this.isCorrect = isCorrect;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public boolean getIsCorrect() {
		return isCorrect;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", movie=" + movie + ", isCorrect=" + isCorrect + "]";
	}
	
}
