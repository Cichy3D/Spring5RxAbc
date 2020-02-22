package pl.demo.rx.view;

import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import pl.demo.rx.model.AppUser;
import pl.demo.rx.repository.AppUserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

@RestController
@RequestMapping("/demo")
public class HelloController {

	@Autowired
	AppUserRepository userRepo;
	
	@GetMapping("/outside")
	public Flux<AppUser> getUsersFormOutThere(){
		
		WebClient wc = WebClient
				  .builder()
				    .baseUrl("http://localhost:8081")
				    // .defaultCookie("cookieKey", "cookieValue")
				    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				    //.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081"))
				  .build();
		
		return wc.get()
				.uri("/users")
				.exchange()
			    .flatMapMany(response -> response.bodyToFlux(AppUser.class));
	}
	
	@GetMapping("/hello")
	public Mono<String> getHello(){
		
		return Mono.just("Hello!");
		
	}
	
	@GetMapping("/lista")
	public Flux<String> getLista(){
		
//		Flux.just("Hello!")
//			.subscribe(x -> System.out.println(x));
		
		return Flux
				.just("Hello!", "123", "xyz")
				.map(s -> s + "; ");
		
	}
	
	@GetMapping("/user/{id}")
	public Mono<AppUser> getUser(@PathVariable long id){
		return userRepo.findById(id);
	}
	
	@GetMapping("/user/name/{name}")
	public Mono<AppUser> getUser(@PathVariable String name){
		return userRepo.findByName(name);
	}
	
	@GetMapping(value="/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<AppUser> getUser(){
		return userRepo.findAll().delayElements(Duration.ofMillis(1000));
	}
	
	
	@GetMapping("/logout") 
	public String logout(ServerHttpRequest request){ //HttpServerRequest request

//		SecurityContextLogoutHandler 
//		HttpServletRequest request;
		//request.get
		new SecurityContextLogoutHandler().setClearAuthentication(true);
		
		return "redirect:/logout";
	}
}
