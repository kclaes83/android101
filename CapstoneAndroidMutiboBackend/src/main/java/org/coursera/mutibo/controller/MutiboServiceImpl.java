package org.coursera.mutibo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.coursera.mutibo.client.MutiboServiceApi;
import org.coursera.mutibo.repository.Answer;
import org.coursera.mutibo.repository.Game;
import org.coursera.mutibo.repository.GameRepository;
import org.coursera.mutibo.repository.Movieset;
import org.coursera.mutibo.repository.MoviesetRepository;
import org.coursera.mutibo.repository.Player;
import org.coursera.mutibo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

@Controller
public class MutiboServiceImpl { //implements MutiboServiceApi {
	
	@Autowired
	private MoviesetRepository movieSetRepository;

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@RequestMapping(value = MutiboServiceApi.MOVIESET_FIRST_PATH, method = RequestMethod.GET)
	public @ResponseBody Movieset getFirstMoviesetForGame() {
		return movieSetRepository.findAll().iterator().next();
	}
	
	@RequestMapping(value = MutiboServiceApi.MOVIESET_FINDRANDOM_PATH, method = RequestMethod.GET)
	public @ResponseBody Movieset getNextMoviesetForGame(@RequestParam(MutiboServiceApi.PARAM_PLAYED_MOVIESET_IDS) List<Long> playedMovieSetIds) {
//		StringUtils.join(playedMovieSetIds, ",")	
		return movieSetRepository.getNextMovieset(playedMovieSetIds);
	}
	
	@RequestMapping(value = MutiboServiceApi.GAME_PATH, method = RequestMethod.POST)
	public @ResponseBody Void saveGame(@RequestBody Game game) {
		long scoreForGame = 0;
		for (Answer answer : game.getAnswers()) {
			if (answer.isCorrect()) {
				scoreForGame++;
			}
		}
		Player player = game.getPlayer();
		player.setPoints(player.getPoints() + scoreForGame);
		
		gameRepository.save(game);
		playerRepository.save(player);
		
		return null;
	}
	
	@RequestMapping(value = MutiboServiceApi.PLAYER_PATH, method = RequestMethod.POST)
	public @ResponseBody Player addPlayer(@RequestBody Player player) {
		return playerRepository.save(player);
	}
	
	@RequestMapping(value = MutiboServiceApi.PLAYER_PATH, method = RequestMethod.GET)
	public @ResponseBody Player findPlayerByUsername(@RequestParam(MutiboServiceApi.PARAM_USERNAME) String username, HttpServletResponse response) {
		List<Player> playerList = playerRepository.findByUsername(username);
		
		if (playerList.isEmpty()) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		} else {			
			return playerList.get(0); 
		}
	}

}
