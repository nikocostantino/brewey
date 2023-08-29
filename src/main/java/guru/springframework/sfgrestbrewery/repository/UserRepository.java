package guru.springframework.sfgrestbrewery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.springframework.sfgrestbrewery.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.username = :username")
	User getUserByUsername(@Param("username")String username);
	
	Boolean existsByUsername(String username);
	
	@Query(value = "SELECT u.user_id, u.password, u.username, r.role_id, r.name "
			+ "FROM users u "
			+ "INNER JOIN user_roles ur "
			+ "ON u.user_id = ur.user_id "
			+ "INNER JOIN roles r "
			+ "ON r.role_id = ur.role_id "
			+ "WHERE r.name = :role", 
			nativeQuery = true)
	List<User> getUserByRole(@Param("role") String role);

}
