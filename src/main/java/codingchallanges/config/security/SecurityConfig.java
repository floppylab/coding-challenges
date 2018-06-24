package codingchallanges.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private BasicUserDetailsService userDetailsService;

	@Configuration
	@Order(1)
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
			.authenticationProvider(userAuthenticationProvider())
			.antMatcher("/api/**")
			.authorizeRequests()
			.anyRequest().authenticated()
			.and().httpBasic();
		}
	}

	@Configuration
	public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/", "/challanges/**").permitAll()
	        .antMatchers("/registration").permitAll()
	        .antMatchers("/swagger-ui.html", "/swagger", "/webjars/**").permitAll()
	        .antMatchers("/swagger-resources/**").permitAll()
	        .antMatchers("/css/**").permitAll()
	        .antMatchers("/img/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .csrf().disable();
		}
	}
	
	@Bean
	public AuthenticationProvider userAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

}

