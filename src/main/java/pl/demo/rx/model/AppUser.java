package pl.demo.rx.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
	
	@Id
	Long id;
	
	String name;
	String password;
	String role;
	
}


//pakiet DTO - Dozer
//public class AppUserDTO {
//	
//	Long id;
//	String name;
//	
//	List<Long> docIds;
//	
//}
//
//class DocumentDTO {
//	Long id;
//	String name;
//	String content;
//	
//	Long creatorUserId;
//}
