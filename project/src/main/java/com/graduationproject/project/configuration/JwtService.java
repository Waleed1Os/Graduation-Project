package com.graduationproject.project.configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    private Long tokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreTokenExpiration;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private Claims extractAllClaims(String jwt){
      return Jwts
      .parser()
      .setSigningKey(getSigningKEY())
      .build()
      .parseSignedClaims(jwt)
      .getPayload();
    }
    private <T> T extractClaim(String jwt,Function<Claims,T> claimExtractor){
        Claims claims= extractAllClaims(jwt);
        return claimExtractor.apply(claims);
    }

    public String extractUsername(String jwt){
        return extractClaim(jwt,Claims::getSubject);
    }
    private Date extractExiration(String jwt){
        return extractClaim(jwt, Claims::getExpiration);
    } 
    private boolean isTokenExpired(String jwt){
        return extractExiration(jwt).before(new Date());
    }
    public boolean isTokenValid(UserDetails user,String jwt){
      final String username=extractUsername(jwt);
      return !isTokenExpired(jwt)&&username.equals(user.getUsername());  
    }

    private String buildToken(UserDetails userDetails,Map<String,Object> extraClaims,long expiration){
      return Jwts
      .builder()
      .issuedAt(new Date())
      .subject(userDetails.getUsername())
      .claims(extraClaims)
      .expiration(new Date(System.currentTimeMillis()+expiration)) 
      .signWith(getSigningKEY())
      .compact();
    }
    public String generateToken(UserDetails userDetails,Map<String,Object> extraClaims){
        return buildToken(userDetails, extraClaims,this.tokenExpiration);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, null);
    }
    public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken( userDetails,new HashMap<>(),this.refreTokenExpiration);
  }

private Key getSigningKEY(){
final byte[] keyBytes=Decoders.BASE64.decode(secretKey);    
return Keys.hmacShaKeyFor(keyBytes);
}

}
