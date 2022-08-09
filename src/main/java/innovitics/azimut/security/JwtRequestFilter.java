package innovitics.azimut.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired 
	private JwtUtil jwtUtil;
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
			String username = null;
			String jwt = null;

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
				username = jwtUtil.extractUsername(jwt);
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

				if (jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			
			
			    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			    response.setHeader("Access-Control-Allow-Credentials", "true");
			    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			    response.setHeader("Access-Control-Max-Age", "3600");
			    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

			    
			filterChain.doFilter(request, response);
		} 
		
		catch (JwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
	}

	public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
		/*
		 * response.setStatus(status.value());
		 * response.setContentType("application/json"); try {
		 * response.sendError(ErrorCode.FAILED_TO_VALIDATE_TOKEN.getCode()); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		response.setStatus(status.value());
		response.setContentType("application/json");
        try {
            String json = StringUtility.convertToJson(ErrorCode.FAILED_TO_VALIDATE_TOKEN);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}