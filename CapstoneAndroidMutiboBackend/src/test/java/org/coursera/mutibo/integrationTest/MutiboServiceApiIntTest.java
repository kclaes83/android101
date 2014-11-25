package org.coursera.mutibo.integrationTest;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.UUID;

import org.coursera.mutibo.client.MutiboServiceApi;
import org.coursera.mutibo.client.SecuredRestBuilder;
import org.coursera.mutibo.client.SecuredRestException;
import org.coursera.mutibo.repository.Answer;
import org.coursera.mutibo.repository.Game;
import org.coursera.mutibo.repository.Movie;
import org.coursera.mutibo.repository.Movieset;
import org.coursera.mutibo.repository.Player;
import org.coursera.mutibo.repository.Rating;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import retrofit.RetrofitError;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public class MutiboServiceApiIntTest {
	
	private final String USERNAME = "admin";
	private final String PASSWORD = "pass";
	private final String CLIENT_ID = "mobile";
	private final String READ_ONLY_CLIENT_ID = "mobileReader";

	private final String TEST_URL = "https://localhost:8443";

	private MutiboServiceApi mutiboService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + MutiboServiceApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(MutiboServiceApi.class);

	private MutiboServiceApi readOnlyMutiboService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + MutiboServiceApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(READ_ONLY_CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(MutiboServiceApi.class);

	private MutiboServiceApi invalidClientMutiboService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + MutiboServiceApi.TOKEN_PATH)
			.setUsername(UUID.randomUUID().toString())
			.setPassword(UUID.randomUUID().toString())
			.setClientId(UUID.randomUUID().toString())
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(MutiboServiceApi.class);

	/**
	 * This test ensures that clients with invalid credentials cannot get
	 * access to movie sets.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAccessDeniedWithIncorrectCredentials() throws Exception {
		Movieset movieSet = new Movieset(
				"relationshipExplanation", 
				Sets.newHashSet(
						new Movie("Die hard"),
						new Movie("Die hard 2"),
						new Movie("Die harder")), 
				new Movie("Snow White"));
		
		try {
			// Add the movie set
			invalidClientMutiboService.addMovieset(movieSet);

			fail("The server should have prevented the client from adding a movie set"
					+ " because it presented invalid client/user credentials");
		} catch (RetrofitError e) {
			assert (e.getCause() instanceof SecuredRestException);
		}
	}
	
	@Test
	public void testAddMovieset() {
		Movieset movieSet = new Movieset(
				"relationshipExplanation", 
				Sets.newHashSet(
						new Movie("Die hard"),
						new Movie("Die hard 2"),
						new Movie("Die harder")), 
				new Movie("Snow White"));
		mutiboService.addMovieset(movieSet);
		
		Collection<Movieset> movieSetList = mutiboService.getMoviesetList();
		assertTrue(movieSetList.contains(movieSet));
	}
	
	@Test
	public void testGetNextMovieset() {
		System.out.println("\n\n BEFORE " + mutiboService.getMoviesetList() + "\n\n " );
		
		
		Movieset movieSet = new Movieset(
				"relationshipExplanation 2", 
				Sets.newHashSet(
						new Movie("Dead Snow"),
						new Movie("The Thing"),
						new Movie("The Shining")), 
				new Movie("Bambi"));
		mutiboService.addMovieset(movieSet);
		
		System.out.println("\n\n AFTER " + mutiboService.getMoviesetList() + "\n\n " );
		
		Movieset nextMovieSet = mutiboService.getNextMoviesetForGame(Lists.newArrayList(1l,2l));
		
		System.out.println("\n\n The next: " + nextMovieSet);
	}
	
	@Test
	public void testSaveAndGetPlayer() {
		System.out.println("\n\n BEFORE \n\n " );
		
		Player jef = mutiboService.addPlayer(new Player("Jef", "password"));
		
		System.out.println("\n\n AFTER "+ jef +"\n\n " );
		
		Player player = mutiboService.findPlayerByUsername("Jef");
		
		assertNotNull(player);
		assertEquals("Jef", player.getUsername());
	}
	
	@Test
	public void testSaveGame() {
		Movieset movieSet = new Movieset(
				"relationshipExplanation ... ???", 
				Sets.newHashSet(
						new Movie("Dead Snow"),
						new Movie("The Thing"),
						new Movie("The Shining")), 
				new Movie("Bambi"));
		mutiboService.addMovieset(movieSet);
		
		Player octaaf = null;
		try {
			octaaf = mutiboService.findPlayerByUsername("Octaaf");
		} catch (RetrofitError e) {
			assertEquals("404 Not Found", e.getMessage());
			octaaf = mutiboService.addPlayer(new Player("Octaaf", "password"));
		}
		Game game = new Game(octaaf);
		
		// Game start
		Movieset firstMovieSet = mutiboService.getFirstMoviesetForGame();
		
		Answer answer = new Answer(firstMovieSet.getRelatedMovies().iterator().next(), true);
		game.addAnswer(answer);
		Rating rating = new Rating(firstMovieSet, 5);
		game.addRating(rating);
		
		mutiboService.saveGame(game);
		
//		Player nieuweScore = mutiboService.findPlayerByUsername("octaaf");
		assertEquals(1l, octaaf.getPoints());
	}
	
	@Test
	public void testSaveGame_2() {
		Player octaaf = null;
		try {
			octaaf = mutiboService.findPlayerByUsername("Octaaf");
		} catch (RetrofitError e) {
			assertEquals("404 Not Found", e.getMessage());
			octaaf = mutiboService.addPlayer(new Player("Octaaf", "password"));
		}
		
		assertEquals(1l, octaaf.getPoints());
	}

}
