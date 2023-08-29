package guru.springframework.sfgrestbrewery.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Integer id;
	private String username;
	private List<String> roles;
	
	public JwtResponse(String token, Integer id, String username, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
	
	
}
