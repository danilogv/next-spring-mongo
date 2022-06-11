package br.com.springboot.mongodb.configuracao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Component
public class JWT {

    @Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(this.secretKey)).parseClaimsJws(token).getBody();
        }
        catch (Exception ex) {
            claims = null;
        }

        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        }
        catch (Exception ex) {
            username = null;
        }

        return username;
    }

    public String generateToken(String username) throws InvalidKeySpecException,NoSuchAlgorithmException {
        return Jwts.builder()
                .setIssuer(this.appName)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM,this.secretKey)
                .compact()
        ;
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + this.expiresIn * 1000L);
    }

    public Boolean validateToken(String token,UserDetails usuario) {
        final String username = getUsernameFromToken(token);
        if (username != null && username.equals(usuario.getUsername()) && !isTokenExpired(token))
            return true;
        return false;
    }

    public Boolean isTokenExpired(String token) {
        Date expiredDate = getExpirationDate(token);
        return expiredDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        Date expireDate;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        }
        catch (Exception ex) {
            expireDate = null;
        }

        return expireDate;
    }

    public Date getIssuedDateFromToken(String token) {
        Date issueAt;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        }
        catch (Exception ex) {
            issueAt = null;
        }

        return issueAt;
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);

        if (authHeader != null && authHeader.startsWith("Bearer "))
            return authHeader.substring(7);

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
