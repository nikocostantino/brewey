package guru.springframework.sfgrestbrewery.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignupRequest {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	private Set<String> role;

}
