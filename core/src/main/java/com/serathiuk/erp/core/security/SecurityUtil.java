package com.serathiuk.erp.core.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class SecurityUtil {

    public static String getIdUsuario() {
        var  securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            var principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
            if(principal.getName() != null) {
                return principal.getName();
            }
        }

        throw new AuthenticationCredentialsNotFoundException("Usuário inválido.");
    }

}
