package org.coursera.mutibo.client;

import java.util.Collection;
import java.util.List;

import org.coursera.mutibo.repository.Game;
import org.coursera.mutibo.repository.Movieset;
import org.coursera.mutibo.repository.Player;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * The interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the
 */
public interface MutiboServiceApi {
	
	public static final String MUTIBO_SERVICE_PATH = "/mutibo";
	
	public static final String PLAYER_PATH = MUTIBO_SERVICE_PATH + "/player";

	public static final String MOVIESET_PATH = MUTIBO_SERVICE_PATH + "/movieset";
	public static final String MOVIESET_FIRST_PATH = MOVIESET_PATH + "/firstmoviesetofgame";
	public static final String MOVIESET_FINDRANDOM_PATH = MOVIESET_PATH + "/findrandom";
	
	public static final String GAME_PATH = MUTIBO_SERVICE_PATH + "/game";
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_PLAYED_MOVIESET_IDS = "playedMoviesetIds";
	public static final String PARAM_MOVIESET_ID = "moviesetId";
	
	
	// PLAYER DOMAIN
	
	@POST(PLAYER_PATH)
	public Player addPlayer(@Body Player player);
	
	@GET(PLAYER_PATH)
	public Player findPlayerByUsername(@Query(PARAM_USERNAME) String username);
	
	
	// GAME DOMAIN

	@GET(MOVIESET_FIRST_PATH)
	public Movieset getFirstMoviesetForGame();
	
	@GET(MOVIESET_FINDRANDOM_PATH)
	public Movieset getNextMoviesetForGame(@Query(PARAM_PLAYED_MOVIESET_IDS) List<Long> playedMovieSetIds);
	
	@POST(GAME_PATH)
	public Void saveGame(@Body Game game);
	
	
	
	
	@POST(MOVIESET_PATH)
	public Void addMovieset(@Body Movieset movieset);
	
	
	
	
	// TODO other Entity
	
	
	// TO TEST
	@GET(MOVIESET_PATH)
	public Collection<Movieset> getMoviesetList();


	
	

}
