package pl.demo.rx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.demo.rx.model.Principal;
import reactor.core.publisher.Mono;

@Service
public class PrincipalService implements ReactiveUserDetailsService {

	@Autowired
	AppUserService userService;

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userService.getByName(username)
				.map(Principal::new);
	}

}
