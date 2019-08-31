package com.sms.zuulgateway.security;

import com.sms.zuulgateway.bean.auth.JwtToken;
import com.sms.zuulgateway.bean.auth.RoleName;
import com.sms.zuulgateway.bean.auth.DbUserDetails;
import com.sms.zuulgateway.repository.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	private static final String AUTH="auth";
    private static final String AUTHORIZATION="Authorization";
    
    @Value("${app.jwtSecret}")
    private String secretKey="secret-key";
    
    @Value("${app.jwtExpirationInMs}")
    private long validityInMilliseconds;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> list) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTH,list);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token =  Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
        jwtTokenRepository.save(new JwtToken(token));
        return token;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        /*if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }*/
        if (bearerToken != null ) {
            return bearerToken;
        }
        return null;
    }
    
    public boolean validateToken(String token) throws JwtException,IllegalArgumentException{
        //Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        //return true;
    	 try {
             Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
             return true;
         } catch (SignatureException ex) {
             logger.error("Invalid JWT signature");
         } catch (MalformedJwtException ex) {
             logger.error("Invalid JWT token");
         } catch (ExpiredJwtException ex) {
             logger.error("Expired JWT token");
         } catch (UnsupportedJwtException ex) {
             logger.error("Unsupported JWT token");
         } catch (IllegalArgumentException ex) {
             logger.error("JWT claims string is empty.");
         }
         return false;
}
	public boolean isTokenPresentInDB (String token) {
	    return jwtTokenRepository.findById(token).isPresent();
	}
		//user details with out database hit
	public UserDetails getUserDetails(String token) {
	    String userName =  getUsername(token);
	    List<String> roleList = getRoleList(token);
	    UserDetails userDetails = new DbUserDetails(userName,roleList.toArray(new String[roleList.size()]));
	    return userDetails;
	}
	public List<String> getRoleList(String token) {
	    return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).
	            getBody().get(AUTH);
	}

	public String getUsername(String token) {
	    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public Authentication getAuthentication(String token) {
	    //using data base: uncomment when you want to fetch data from data base
	    //UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
	    //from token take user value. comment below line for changing it taking from data base
	    UserDetails userDetails = getUserDetails(token);
	    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
