package com.careydevelopment.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.careydevelopment.twitterautomation.util.Constants;

@Component
public class LoginCheckFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginCheckFilter.class);
	
	@Override
	public void destroy() {
		// ...
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("Initializing logincheck filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		String loginKey = (String)((HttpServletRequest)request).getSession().getAttribute(Constants.LOGIN_KEY);
		if (loginKey == null) {
			LOGGER.warn("USER NOT LOGGED IN!");
		}
		
		chain.doFilter(request, response);	
	}
}
