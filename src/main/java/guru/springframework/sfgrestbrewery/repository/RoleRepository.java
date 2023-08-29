package guru.springframework.sfgrestbrewery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.springframework.sfgrestbrewery.model.ERole;
import guru.springframework.sfgrestbrewery.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByName(ERole name);
	
	@Query("select r.id from Role r where r.name = :name")
	Integer findIdByName(@Param("name")ERole role);
	
	@Query("select r from Role r where r.name = :name")
	Iterable<Role> getAllRolesByName(@Param("name")ERole name);
}
