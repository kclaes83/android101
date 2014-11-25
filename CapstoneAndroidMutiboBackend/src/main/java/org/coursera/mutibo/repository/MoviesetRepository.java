package org.coursera.mutibo.repository;

import java.util.Collection;
import java.util.List;

import org.coursera.mutibo.client.MutiboServiceApi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the MovieSetRepository through a controller and map it to the 
// "/movieset" path. This automatically enables you to do the following:
//
// 1. List all MovieSets by sending a GET request to /movieset 
// 2. Add a video by sending a POST request to /movieset with the JSON for a MovieSet
// 3. Get a specific MovieSet by sending a GET request to /movieset/{moviesetId}
//    (e.g., /movieset/1 would return the JSON for the MovieSet with id=1)
// 4. Send search requests to our findByXYZ methods to /movieset/search/findByXYZ
//    (e.g., /movieset/search/findByName?title=Foo)
//
 */
@RepositoryRestResource(path = "movieset")
public interface MoviesetRepository extends CrudRepository<Movieset, Long> {

	@Query(value = "select * from Movieset ms where ms.id not in ( :ids ) limit 0, 1", nativeQuery = true)
	Movieset getNextMovieset(@Param("ids") List<Long> playedMovieSetIds);
	
}
