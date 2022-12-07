package innovitics.azimut.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import io.jsonwebtoken.JwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired 
	private JwtUtil jwtUtil;
	@Autowired
	private HmacUtility hmacUtility;
	@Autowired ConfigProperties configProperties;
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String SIGNATURE_HEADER = "Signature";
	private static final String CONTENT_TYPE = "Content-Type";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			
			ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
			ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
			long startTime = System.currentTimeMillis();

			final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
			final String signatureHeader = request.getHeader(SIGNATURE_HEADER);
			final String contentTypeHeader = request.getHeader(CONTENT_TYPE);
			
			
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

			
			this.logger.info("authorization header:::"+authorizationHeader);    
			this.logger.info("signature header:::"+signatureHeader);    
			this.logger.info("content type:::"+contentTypeHeader);
			//filterChain.doFilter(request, response);			  
			filterChain.doFilter(requestWrapper, responseWrapper);

			this.logger.info("Method:::"+requestWrapper.getMethod());
			
			this.logger.info("URL:::"+requestWrapper.getRequestURI());
			
		    String requestBody = StringUtility.getStringValue(requestWrapper.getContentAsByteArray(),
					request.getCharacterEncoding());
			this.logger.info("REQUEST:::"+requestBody);	
			
			String responseBody = StringUtility.getStringValue(responseWrapper.getContentAsByteArray(),
					response.getCharacterEncoding());
			long timeTaken = System.currentTimeMillis() - startTime;
			this.logger.info("RESPONSE:::"+responseBody);
			responseWrapper.copyBodyToResponse();

			
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
	
	
	String generateHmacSignature(String signatureHeader,String payload,HttpServletRequest request)
	{
		String hashedPayload="";
		if (StringUtility.isStringPopulated(signatureHeader)) 
		{
			this.logger.info("Signature header:::"+signatureHeader);
			this.logger.info("Payload:::"+payload);
			try 
			{
				hashedPayload = this.hmacUtility.generateHmac256(payload,this.configProperties.getPaytabsServerKey());
				this.logger.info("Payload:::"+payload);
				this.logger.info("hashedPayload:::"+hashedPayload);
				request.setAttribute("hashedPayload",hashedPayload);
			} 
			catch (InvalidKeyException | NoSuchAlgorithmException e) 
			{
				this.logger.info("Could not generate the hmac signature");
				e.printStackTrace();
				return hashedPayload;
			}
			
		}
		return signatureHeader;
	}
}