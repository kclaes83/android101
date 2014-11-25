package org.coursera.mutibo.repository;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import com.google.common.collect.Lists;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
//	@RestResource(exported = false)
	@OneToOne(optional = false)
	@JoinColumn(name = "PLAYER_ID", nullable = false)
	private Player player;
	
//	@RestResource(exported = false)
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "GAME_ID")	
	private List<Answer> answers;
	
//	@RestResource(exported = false)
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "GAME_ID")
	private List<Rating> ratings;
	
	// Hibernate requires default constructor
	@SuppressWarnings("unused")
	private Game() {
	}
	
	public Game(Player player) {
		this.player = player;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
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
		Game other = (Game) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", player=" + player + ", answers=" + answers + ", ratings="
				+ ratings + "]";
	}

	public void addAnswer(Answer answer) {
		if (answers == null) {
			answers = Lists.newArrayList();
		}
		answers.add(answer);
	}

	public void addRating(Rating rating) {
		if (ratings == null) {
			ratings = Lists.newArrayList();
		}
		ratings.add(rating);
	}
}
