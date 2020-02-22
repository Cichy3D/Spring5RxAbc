package pl.demo.rx.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pl.demo.rx.model.AppUser;
import reactor.core.publisher.Mono;

@Repository
public interface AppUserRepository extends ReactiveCrudRepository<AppUser, Long> {

	@Query("SELECT * FROM app_user WHERE name = :userName")
	Mono<AppUser> findByName(String userName);
	
}
