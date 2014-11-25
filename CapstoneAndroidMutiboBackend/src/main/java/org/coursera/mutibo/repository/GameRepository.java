package org.coursera.mutibo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "game")
public interface GameRepository extends CrudRepository<Game, Long> {
	
}
