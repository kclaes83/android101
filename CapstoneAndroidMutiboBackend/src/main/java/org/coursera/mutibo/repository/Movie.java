package org.coursera.mutibo.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

@Entity
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String title;
	
	// Hibernate requires default constructor
	public Movie() {
	}
	
	public Movie(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Movie [title=" + title + "]";
	}

	/**
	 * Two Movies will generate the same hashcode if they have exactly the same
	 * values for their title.
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(title);
	}

	/**
	 * Two Movies are considered equal if they have exactly the same values for
	 * their title.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Movie) {
			Movie other = (Movie) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(title, other.title);
//					&& Objects.equal(url, other.url)
//					&& duration == other.duration;
		} else {
			return false;
		}
	}
}
