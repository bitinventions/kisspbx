package org.kisspbx;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

	private static final Pattern changePattern = Pattern.compile("^.*/(save|update|delete)$");
	
	@Override
	public void destroy() {
				
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
		
		if (request instanceof HttpServletRequest) {
			if (changePattern.matcher(((HttpServletRequest)request).getServletPath()).matches() &&
					request.getAttribute("error") == null) {
				ConfigurationChangedMonitor.setChanged();
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
