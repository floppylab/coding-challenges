package codingchallanges.config.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class BasicUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String bCryptHash;

    public BasicUserDetails(BasicUser user) {
    	this.username = user.getUsername();
        this.bCryptHash = user.getBcrypthash();
    }

    @Override
    public String getPassword() {
        return bCryptHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(Arrays.asList(new SimpleGrantedAuthority("BASIC_USER")));
    }

	@Override
	public String getUsername() {
		return username;
	}
}
