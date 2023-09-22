
package com.luxfacta.vernyy.api.jwt;

import com.luxfacta.vernyy.api.constants.Constantes;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {
    
    private final long EXPIRATION_TIME;
    private final long REFRESH_EXPIRATION_TIME;
    private final long EXPIRATION_TIME_APP;
    private final long REFRESH_EXPIRATION_TIME_APP;
    private final String SECRET;
    public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_REFRESH = "Refresh";
	public static final String BEARER = "Bearer ";
    
    public TokenAuthenticationService(Environment env) {
        this.EXPIRATION_TIME = Long.parseLong(env.getProperty("security.expiration"));
        this.REFRESH_EXPIRATION_TIME = Long.parseLong(env.getProperty("security.refresh.expiration"));
        this.EXPIRATION_TIME_APP = Long.parseLong(env.getProperty("security.expiration_app"));
        this.REFRESH_EXPIRATION_TIME_APP = Long.parseLong(env.getProperty("security.refresh.expiration_app"));
        this.SECRET = env.getProperty("security.secret"); 
    }

 
    public Authentication getRefreshAuthentication(HttpServletRequest request,	HttpServletResponse response)
	{
		String token = request.getHeader(HEADER_AUTHORIZATION);
        try {
			if (token != null) {

                SignedJWT jwsObject = SignedJWT.parse( token.replace(BEARER, "") );
                JWSVerifier verifier = new MACVerifier( SECRET.getBytes() );
                if ( jwsObject.verify( verifier )) {
                    Date expirationTime = jwsObject.getJWTClaimsSet().getExpirationTime();
                    if (expirationTime.before(new Date())) {
                        return null;
                    }



                    JWTClaimsSet claim =  JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    			
    				String email = claim.getSubject();
                    List<String> role = claim.getStringListClaim("role");
                    List<String> condominio = claim.getStringListClaim("condominio");
                    List<String> apartamento = claim.getStringListClaim("apartamento");
                    String nomeUsuario = claim.getStringClaim("name");
                    Long usuario = claim.getLongClaim("usuario");

                    Long[]  condLongList = new Long[condominio.size()];
                    for (int i = 0; i < condominio.size(); i++) {
                        condLongList[i] = Long.valueOf(condominio.get(i));
                    }
                    
                    Long[] aptoLongList = new Long[apartamento.size()];
                    for (int i = 0; i < apartamento.size(); i++) {
                    	aptoLongList[i] = Long.valueOf(apartamento.get(i));
                    }

                    if (!Constantes.ROLE_MORADOR.equals(role)) {
	                    response.addHeader(HEADER_AUTHORIZATION, generateToken(email, role, condLongList, aptoLongList, nomeUsuario, usuario, EXPIRATION_TIME, SECRET));
	                    response.addHeader(HEADER_REFRESH, generateToken(email, role, condLongList, aptoLongList, nomeUsuario, usuario, REFRESH_EXPIRATION_TIME, SECRET));
                    } else {
	                    response.addHeader(HEADER_AUTHORIZATION, generateToken(email, role, condLongList, aptoLongList, nomeUsuario, usuario, EXPIRATION_TIME_APP, SECRET));
	                    response.addHeader(HEADER_REFRESH, generateToken(email, role, condLongList, aptoLongList, nomeUsuario, usuario, REFRESH_EXPIRATION_TIME_APP, SECRET));
                    }

                    return new JWTAuthentication(email, getGrantedAuthorities(role), condLongList, aptoLongList, usuario);
                }
            }
        } catch (Exception e) {
            return null;
        }
		return null;
	}
    
    


	public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);

        try {
            if (token != null) {
                
                SignedJWT jwsObject = SignedJWT.parse( token.replace(BEARER, "") );
                JWSVerifier verifier = new MACVerifier( SECRET.getBytes() );
                if ( jwsObject.verify( verifier )) {
                    
                    Date expirationTime = jwsObject.getJWTClaimsSet().getExpirationTime();
                    if (expirationTime.before(new Date())) {
                        return null;
                    }
                    
                    JWTClaimsSet claim =  JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    			
    				String email = claim.getSubject();
                    List<String> role = claim.getStringListClaim("role");
                    List<String> condominio = claim.getStringListClaim("condominio");
                    List<String> apartamento = claim.getStringListClaim("apartamento");
                    Long usuario = claim.getLongClaim("usuario");

                    Long[] condLongList = new Long[condominio.size()];
                    for (int i = 0; i < condominio.size(); i++) {
                        condLongList[i] = Long.valueOf(condominio.get(i));
                    }
                    
                    Long[] aptoLongList = new Long[apartamento.size()];
                    for (int i = 0; i < apartamento.size(); i++) {
                    	aptoLongList[i] = Long.valueOf(apartamento.get(i));
                    }
                
                    return new JWTAuthentication(email, getGrantedAuthorities(role), condLongList, aptoLongList, usuario);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    
    private List<GrantedAuthority> getGrantedAuthorities(List<String> role) {
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String s : role)
            authorities.add(new SimpleGrantedAuthority(s));
        return authorities;
    }

    public String generateToken(String email, List<String> rolesGrupo, 
    		Long[] condominio, Long[] apartamento, String nomeUsuario, Long idUsuario, long expirationTime, String secret)
	{
        try {
        	
            String[] condStringList = new String[condominio.length];
            for (int i = 0; i < condominio.length; i++) {
            	condStringList[i] = condominio[i].toString();
            }

            String[] aptoStringList = new String[apartamento.length];
            for (int i = 0; i < apartamento.length; i++) {
            	aptoStringList[i] = apartamento[i].toString();
            }

            
            JWSSigner signer = new MACSigner(secret.getBytes());
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(email)
                    .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                    .claim("role", rolesGrupo)
                    .claim("condominio", condStringList)
                    .claim("apartamento", aptoStringList)
                    .claim("name", nomeUsuario)
                    .claim("usuario", idUsuario)
                    .build();

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claims.toJSONObject()));

            
            
            jwsObject.sign(signer);
            return "Bearer " + jwsObject.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

    
    public void updateContext(String email, List<String> rolesGrupo,Long[] condominio, Long[] apartamento, Long idUsuario) {
        SecurityContextHolder.getContext().setAuthentication(new JWTAuthentication(email, getGrantedAuthorities(rolesGrupo), condominio, apartamento, idUsuario));
    }

}