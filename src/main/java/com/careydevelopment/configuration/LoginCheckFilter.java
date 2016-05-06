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
		String uri = ((HttpServletRequest)request).getRequestURI();
		if (uri == null) uri = "";

		int lastSlash = uri.lastIndexOf("/");
		
		if (lastSlash > -1) {
			//looking for a page URL not an image or javascript
			if (uri.indexOf(".",lastSlash) == -1) {				
				//see if they have the login key
				String loginKey = (String)((HttpServletRequest)request).getSession().getAttribute(Constants.LOGIN_KEY);
				
				if (loginKey == null && !uri.endsWith("/notLoggedIn") && uri.indexOf("/assets/") == -1 && uri.indexOf("/images/") == -1) {
					LOGGER.warn("USER NOT LOGGED IN!");
					request.getRequestDispatcher("/notLoggedIn").forward(request, response);
				} else {
					chain.doFilter(request, response);	
				}	
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}
}
