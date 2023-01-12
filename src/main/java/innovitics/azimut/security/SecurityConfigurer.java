package innovitics.azimut.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter 
{
	@Autowired 
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired 
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception 
	{
		authenticationManagerBuilder.parentAuthenticationManager(authenticationManagerBean())
	        .userDetailsService(myUserDetailsService);
	}

	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
	return super.authenticationManagerBean();	
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.csrf().disable()
		.authorizeRequests().antMatchers("/api/authenticate","/api/forgotPassword","/api/user/getByUserPhone","/api/saveUserTemporarily","/api/azimut/user/getAzimutLookUpData",
				"/api/azimut/trading/incrementUserBlockage",
				"/api/azimut/trading/getUserBlockage",
				"/api/paytabs/callback",
				"/api/paytabs/instantCallback",
				"/api/user/addUserInteraction",
				"/api/azimut/information/**",
				"/admin/authenticate"
				
				).permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	
	
	
	@Bean
	public  PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
}
