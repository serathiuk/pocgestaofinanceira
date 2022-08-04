package com.serathiuk.erp.integracaobancaria;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SecurityFilter implements Filter {

	@Value("${integradorbancario.key:}")
	private String chaveAutorizacao;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		var httpRequest = (HttpServletRequest) request;
		var httpResp = (HttpServletResponse) response;
		var token = httpRequest.getHeader("Authorization");
		
		httpResp.setHeader("Access-Control-Allow-Origin", "*");
        httpResp.setHeader("Access-Control-Allow-Methods", "*");
        httpResp.setHeader("Access-Control-Max-Age", "3600");
        httpResp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
		
		// Funciona com qualquer chave 
		if(!StringUtils.isEmpty(token)) {
			chain.doFilter(request, response);
		} else {
			httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
		}
	}

}
