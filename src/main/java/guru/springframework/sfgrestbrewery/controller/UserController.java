package guru.springframework.sfgrestbrewery.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.sfgrestbrewery.exception.UserNotFoundException;
import guru.springframework.sfgrestbrewery.model.ERole;
import guru.springframework.sfgrestbrewery.model.User;
import guru.springframework.sfgrestbrewery.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1/")
@RestController
@ResponseBody
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "users")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public CollectionModel<User> getUsers(){
		log.info("Getting all users from the Database");
		final Iterable<User> userIterable = userService.getAllUsers();
		final List<User> users = StreamSupport
				.stream(userIterable.spliterator(), false)
				.toList();
		
		for(User user:  users) {
			Link userIdLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UserController.class)
							.getUserById(user.getId()))
					.withRel("user-id");
			Link userRoleLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UserController.class)
							.getUserFromRole(user.getRoles().get(0).getName()))
					.withRel("user-role");
			Link deleteUserLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UserController.class)
							.deleteUserById(user.getId()))
					.withRel("delete-user");
			user.add(userIdLink);
			user.add(userRoleLink);
			user.add(deleteUserLink);
		}
		
		Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUsers()).withSelfRel();
		return CollectionModel.of(users, link);
	}
	
	@DeleteMapping(value = "/users/user/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteUserById(@PathVariable(name = "id") Integer userId){
		log.info("Deleting user with userId {} from the Database", userId);
		userService.deleteUserById(userId);
		log.info("Record with userId {} has been deleted", userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/user/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable(name = "id") final Integer userId){
		log.info("Getting user with userId {} from the Database.", userId);
		final User user = userService.getUserById(userId)
				.orElseThrow(() -> new UserNotFoundException
					("User with userId " + userId 
							+ " not found in the Database"));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/user-from-role")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<User>> getUserFromRole(@RequestParam("role") ERole role){
		final List<User> users = userService.getUserByRole(role);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
