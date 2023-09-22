
package com.luxfacta.vernyy.api.jwt;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthentication extends UsernamePasswordAuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Long[] idCondominio;
	private final Long[] idApartamento;
    private final Long idUsuario;

    public JWTAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities, Long[] idCondominio, Long[] idApartamento, Long idUsuario) {
        super(principal, null, authorities);
        this.idCondominio = idCondominio;
        this.idApartamento= idApartamento;
        this.idUsuario = idUsuario;
    }

   
    public Long[] getIdApartamento() {
		return idApartamento;
	}
	public Long[] getIdCondominio() {
        return this.idCondominio;
    }
    public Long getIdUsuario() {
        return this.idUsuario;
    }
    
}
