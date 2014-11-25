package org.coursera.mutibo.repository;

import java.util.List;

import org.coursera.mutibo.client.MutiboServiceApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "player")
public interface PlayerRepository extends CrudRepository<Player, Long> {
	
	List<Player> findByUsername(@Param(MutiboServiceApi.PARAM_USERNAME) String username);

}
