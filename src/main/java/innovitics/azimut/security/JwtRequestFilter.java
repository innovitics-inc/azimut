package innovitics.azimut.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.BusinessUserInterface;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.threading.CurrentRequestHolder;
import innovitics.azimut.utilities.threading.MyThread;
import io.jsonwebtoken.JwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private MyAdminDetailsService myAdminDetailsService;
	@Autowired 
	private JwtUtil jwtUtil;
	@Autowired ConfigProperties configProperties;
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String ADMIN_HEADER = "Is-Admin";
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
						
			final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
			final String adminHeader=request.getHeader(ADMIN_HEADER);
			final boolean isAdmin=StringUtility.isStringPopulated(adminHeader)&&StringUtility.TRUE.contains(adminHeader);
			
		    long start = System.nanoTime();
		    String username = null;
			String jwt = null;
			String transaction=DateUtility.getCurrentNanoSecond();
			Thread.currentThread().setName(transaction);
			BusinessUserInterface businessUser=isAdmin?new BusinessAdminUser():new BusinessUser();

			this.prepareToken(isAdmin?myAdminDetailsService:myUserDetailsService, authorizationHeader, jwt, username, businessUser, request);
			
			    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			    response.setHeader("Access-Control-Allow-Credentials", "true");
			    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			    response.setHeader("Access-Control-Max-Age", "3600");
			    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

			businessUser.setSystemTrx(transaction);
			//CurrentRequestHolder.set(businessUser);
			MyLogger.info("authorization header:::"+authorizationHeader);
			MyLogger.info("Used profile::::"+this.configProperties.getUsedProfile());
			filterChain.doFilter(request, response);
			
			
	        long end = System.nanoTime();

	        long elapsedTime = end - start;

	        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;

			
			MyLogger.info("Request Done in :::" + (elapsedTimeInSecond)+" seconds");
			//CurrentRequestHolder.clear();
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
		response.setStatus(status.value());
		response.setContentType("application/json");
        try {
            String json = StringUtility.convertToJson(ErrorCode.FAILED_TO_VALIDATE_TOKEN);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	void prepareToken(UserDetailsService userDetailsService,String authorizationHeader,String jwt,String username,BusinessUserInterface businessUser,HttpServletRequest request)
	{
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
		{
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}


		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			BusinessUserHolder userDetails = (BusinessUserHolder) userDetailsService.loadUserByUsername(username);
		  if (jwtUtil.validateToken(jwt, userDetails)) 
			{
			  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			  businessUser=userDetails.getBusinessUser();		
			}
		}

	}

	
}