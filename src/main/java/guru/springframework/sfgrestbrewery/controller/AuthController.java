package guru.springframework.sfgrestbrewery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.sfgrestbrewery.model.ERole;
import guru.springframework.sfgrestbrewery.model.Role;
import guru.springframework.sfgrestbrewery.model.User;
import guru.springframework.sfgrestbrewery.payload.request.LoginRequest;
import guru.springframework.sfgrestbrewery.payload.request.SignupRequest;
import guru.springframework.sfgrestbrewery.payload.response.JwtResponse;
import guru.springframework.sfgrestbrewery.payload.response.MessageResponse;
import guru.springframework.sfgrestbrewery.repository.RoleRepository;
import guru.springframework.sfgrestbrewery.repository.UserRepository;
import guru.springframework.sfgrestbrewery.security.jwt.JwtUtils;
import guru.springframework.sfgrestbrewery.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		return ResponseEntity.ok(new JwtResponse(jwt, 
                userDetails.getId(), 
                userDetails.getUsername(),
                roles));
	}
	
	@PostMapping("/signup")
	  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	    if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Username is already taken!"));
	    }

	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(),
	               encoder.encode(signUpRequest.getPassword()));

	    Set<String> strRoles = signUpRequest.getRole();
	    List<Role> roles = new ArrayList<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.USER);
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {
	    	if(role.equals("admin")) {
	    		Role adminRole = roleRepository.findByName(ERole.ADMIN);
		          roles.add(adminRole);
	    	}
	    	else if(role.equals("user")) {
	    		Role userRole = roleRepository.findByName(ERole.USER);
		          roles.add(userRole);
	    	}else{
				//fallback
				Role userRole = roleRepository.findByName(ERole.USER);
				roles.add(userRole);
			}
	      });
	    }

	    user.setRoles(roles);
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }

}
