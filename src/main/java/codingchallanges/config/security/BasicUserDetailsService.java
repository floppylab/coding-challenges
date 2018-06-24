package codingchallanges.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    @Autowired
    private BasicUserRepository userRepository;

    @Override
    public BasicUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BasicUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new BasicUserDetails(user);
    }
}