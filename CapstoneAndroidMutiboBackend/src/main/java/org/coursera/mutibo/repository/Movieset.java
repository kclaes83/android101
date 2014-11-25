package org.coursera.mutibo.repository;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Movieset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String relationshipExplanation;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "MOVIESET_ID")
	private Set<Movie> relatedMovies;
	
	@OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "MOVIESET_ID", nullable = false)
	private Movie unrelatedMovie;

	// Hibernate requires default constructor
	public Movieset() {
	}
	
	public Movieset(String relationshipExplanation, Set<Movie> relatedMovies,
			Movie unrelatedMovie) {
		this.relationshipExplanation = relationshipExplanation;
		this.relatedMovies = relatedMovies;
		this.unrelatedMovie = unrelatedMovie;
	}

	public String getRelationshipExplanation() {
		return relationshipExplanation;
	}

	public void setRelationshipExplanation(String relationshipExplanation) {
		this.relationshipExplanation = relationshipExplanation;
	}

	public Set<Movie> getRelatedMovies() {
		return relatedMovies;
	}

	public void setRelatedMovies(Set<Movie> relatedMovies) {
		this.relatedMovies = relatedMovies;
	}

	public Movie getUnrelatedMovie() {
		return unrelatedMovie;
	}

	public void setUnrelatedMovie(Movie unrelatedMovie) {
		this.unrelatedMovie = unrelatedMovie;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((relatedMovies == null) ? 0 : relatedMovies.hashCode());
		result = prime * result
				+ ((relationshipExplanation == null) ? 0 : relationshipExplanation.hashCode());
		result = prime * result + ((unrelatedMovie == null) ? 0 : unrelatedMovie.hashCode());
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
		Movieset other = (Movieset) obj;
		if (id != other.id)
			return false;
		if (relatedMovies == null) {
			if (other.relatedMovies != null)
				return false;
		} else if (!relatedMovies.equals(other.relatedMovies))
			return false;
		if (relationshipExplanation == null) {
			if (other.relationshipExplanation != null)
				return false;
		} else if (!relationshipExplanation.equals(other.relationshipExplanation))
			return false;
		if (unrelatedMovie == null) {
			if (other.unrelatedMovie != null)
				return false;
		} else if (!unrelatedMovie.equals(other.unrelatedMovie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movieset [id=" + id + ", relationshipExplanation=" + relationshipExplanation
				+ ", relatedMovies=" + relatedMovies + ", unrelatedMovie=" + unrelatedMovie + "]";
	}

}
