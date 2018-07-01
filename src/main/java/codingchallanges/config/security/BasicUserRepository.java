package codingchallanges.config.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepository extends JpaRepository<BasicUser, String> {

    /**
     * Finds the user by its name.
     * 
     * @param username name of user
     * @return user selected by its name
     */
    BasicUser findByUsername(String username);

}
