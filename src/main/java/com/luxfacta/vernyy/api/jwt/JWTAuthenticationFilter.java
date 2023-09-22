
package com.luxfacta.vernyy.api.jwt;

import java.io.IOException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

    final Environment env;
    
    public JWTAuthenticationFilter(Environment env) 
    {
        super();
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        TokenAuthenticationService tks = new TokenAuthenticationService(env);
        
		Authentication authentication = null;
		
		if(isRefreshToken(req))
		{
			authentication = tks.getRefreshAuthentication((HttpServletRequest)req, (HttpServletResponse)resp);
		} else {
			authentication = tks.getAuthentication((HttpServletRequest)req);
		}
		
		if (authentication != null) {
			if(isCheck(req)) {
				((HttpServletResponse)resp).setStatus(HttpStatus.SC_OK);
				((HttpServletResponse)resp).getWriter().print("{\"status\":\"OK\"}");
				resp.flushBuffer();
			} else {
		        SecurityContextHolder.getContext().setAuthentication(authentication);
		        filterChain.doFilter(req, resp);
			}
	        
		} else {
			if (isRefreshToken(req)) {
				((HttpServletResponse)resp).setStatus(HttpStatus.SC_FORBIDDEN);
				resp.flushBuffer();
			} else if (!isPublic(req)) {
				((HttpServletResponse)resp).setStatus(HttpStatus.SC_UNAUTHORIZED);
				resp.flushBuffer();
			} else {
		        filterChain.doFilter(req, resp);
			}
		}
        
    }
    
    private boolean isRefreshToken(ServletRequest request)
    {
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest)request).getRequestURI().contains("/refresh");
        }
        return false;
    }

    private boolean isCheck(ServletRequest request)
    {
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest)request).getRequestURI().contains("/checkToken");
        }
        return false;
    }

    
    private boolean isPublic(ServletRequest request)
    {
    	if(request instanceof HttpServletRequest && (
        		   ((HttpServletRequest)request).getRequestURI().indexOf("/publico") == 0 
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/forgot") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/reset") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/error") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/login") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/redefinirSenha") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/sync") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/definirSenha") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/primeiroAcesso") == 0
				|| ((HttpServletRequest)request).getRequestURI().indexOf("/loginMorador") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/loginFuncionario") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/forgotMorador") == 0
    			|| ((HttpServletRequest)request).getRequestURI().indexOf("/swagger-ui") == 0
				|| ((HttpServletRequest)request).getRequestURI().indexOf("/swagger-resources") == 0
				|| ((HttpServletRequest)request).getRequestURI().indexOf("/swagger-ui.html") == 0
				|| ((HttpServletRequest)request).getRequestURI().indexOf("/v3/api-docs") == 0
				|| ((HttpServletRequest)request).getRequestURI().indexOf("/webjars") == 0
                )
    		)
    		return true;
    	return false;
    }
}