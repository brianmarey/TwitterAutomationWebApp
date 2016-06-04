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

public class ErrorHandlerFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerFilter.class);
	
	@Override
	public void destroy() {
		// ...
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("Initializing filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		String uri = ((HttpServletRequest)request).getRequestURI();
		if (uri == null) uri = "";
		LOGGER.info(uri);
		
		try {
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse)response); 
			int statusCode = wrapper.getStatus();
			LOGGER.info("" + statusCode);
			
			if (statusCode == 404 && !uri.endsWith("/404")) {
				LOGGER.info("Going to 404 " + uri);
				request.getRequestDispatcher("/404").forward(request, wrapper);
				//throw new RuntimeException("Here " + uri);
			} else {
				chain.doFilter(request, response);	
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			LOGGER.error("Caught exception on uri " + uri,ex);
			request.setAttribute("errorMessage", ex);
			//request.getRequestDispatcher("/404").forward(request, response);
		}
	}
}
