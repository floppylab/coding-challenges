package codingchallanges.config.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import codingchallanges.exception.NonMatchingPasswordsException;
import codingchallanges.exception.UsernameExistsException;

@Service
public class BasicUserService {

	@Autowired
	private BasicUserRepository userRepository;

	public BasicUser createUserAccount(@Valid BasicUserDto userDto) {
		if(!userDto.getPassword().equals(userDto.getMatchingPassword())) {
			throw new NonMatchingPasswordsException("passwords do not match");
		}
		if (usernameExist(userDto.getUsername())) {   
            throw new UsernameExistsException("there is a user with that username:"  + userDto.getUsername());
        }
        BasicUser user = new BasicUser();    
        user.setUsername(userDto.getUsername());
        user.setBcrypthash(new BCryptPasswordEncoder(11).encode(userDto.getPassword()));
        return userRepository.save(user); 
	}

	private boolean usernameExist(String username) {
		BasicUser user = userRepository.findByUsername(username);
		if (user != null) {
			return true;
		}
		return false;
	}

}
