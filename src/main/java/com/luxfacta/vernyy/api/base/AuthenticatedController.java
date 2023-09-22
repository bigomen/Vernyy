package com.luxfacta.vernyy.api.base;

import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.jwt.JWTAuthentication;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@SecurityRequirement(name = "bearerAuth")
public abstract class AuthenticatedController extends BaseController {
   
    @Autowired
    protected Environment config;

	protected final Mapper mapper = new Mapper();

    protected boolean hasPermission(String permission) {
        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
    	for (GrantedAuthority ga : jwtAuth.getAuthorities()) {
    		if (permission.equals(ga.getAuthority()))
    			return true;
    	}
    	return false;
    }
    
    protected Long[] getIdCondominio() {
        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return jwtAuth.getIdCondominio();
    }

    protected Long[] getIdApartamento() {
        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return jwtAuth.getIdApartamento();
    }

    
    protected Long getIdUsuario() {
        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return jwtAuth.getIdUsuario();
    }

    protected String getLoginUsuario() {
        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return jwtAuth.getPrincipal().toString();
    }

    
    protected boolean checkApartamento(Long idCheck) throws BusinessRuleException {
    	if (idCheck == null)
        	throw new BusinessRuleException("O apartamento deve ser informado");

    	if (hasPermission(Constantes.ROLE_ADMIN) || hasPermission(Constantes.ROLE_CENTRAL) )
    		return true;
    	
    	for (Long id : getIdApartamento()) {
    		if (id.equals(idCheck))
    			return true;
    	}
    	throw new BusinessRuleException("Usuário não tem acesso a esse apartamento");
    }
    
}