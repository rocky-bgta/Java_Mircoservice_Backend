package nybsys.tillboxweb.token.manipulator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nybsys.tillboxweb.Core;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JWTFilter extends Core {

    //Sample method to construct a JWT
    public static String createJWT(long tokenDurationInMillisecond, TokenModel tokenModel) {


        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.


        //The JWT signature algorithm we will be using to sign the token
        //SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(/*apiKey.getSecret()*/ "secret");
        //Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .claim("tokenModel", tokenModel)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, "secret");

        //if it has been specified, let's add the expiration
        if (tokenDurationInMillisecond >= 0) {
            long expMillis = nowMillis + tokenDurationInMillisecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    //Sample method to validate and read the JWT
    public static void parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(/*apiKey.getSecret()*/ "secret"))
                .parseClaimsJws(jwt).getBody();
        System.out.println("TokenModel " + claims.get("tokenModel"));
        System.out.println("Expiration: " + claims.getExpiration());
    }

}
