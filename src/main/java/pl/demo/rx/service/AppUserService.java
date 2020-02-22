package pl.demo.rx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.demo.rx.model.AppUser;
import pl.demo.rx.repository.AppUserRepository;
import reactor.core.publisher.Mono;

@Service
public class AppUserService {
	
	@Autowired
	AppUserRepository repository;

//	public AppUser addAppUser(AppUser user) {
//		return repository.save(user);
//	}
//	
//	public List<AppUser> getAll() {
//		return repository.findAll();
//	}
	
	public Mono<AppUser> getByName(String userName) {
		return repository.findByName(userName);
	}
	
}
