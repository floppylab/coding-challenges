package codingchallanges.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    /**
     * Returns the authenticated user's username.
     * 
     * @return name of the authenticated user
     */
    public String username() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (name == "anonymousUser") {
            return null;
        }
        return name;
    }

}
