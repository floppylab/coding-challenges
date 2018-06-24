package codingchallanges.config.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepository extends JpaRepository<BasicUser, String> {

	BasicUser findByUsername(String username);

}
