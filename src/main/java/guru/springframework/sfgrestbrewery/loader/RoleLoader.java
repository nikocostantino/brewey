package guru.springframework.sfgrestbrewery.loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgrestbrewery.model.ERole;
import guru.springframework.sfgrestbrewery.model.Role;
import guru.springframework.sfgrestbrewery.repository.RoleRepository;

@Component
public class RoleLoader implements CommandLineRunner{
	
	private RoleRepository roleRepository;
	
	public RoleLoader(RoleRepository repository) {
		this.roleRepository = repository;
	}
	
	@Override
    public void run(String... args) throws Exception {
        loadRoleObjects();
    }
	
	private synchronized void loadRoleObjects() {
		
		if(roleRepository.count() == 0) {
			
			roleRepository.save(Role.builder()
					.name(ERole.USER)
					.build());
			
			roleRepository.save(Role.builder()
					.name(ERole.ADMIN)
					.build());
		}
	}

}
