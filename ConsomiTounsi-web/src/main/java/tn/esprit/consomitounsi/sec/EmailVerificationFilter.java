package tn.esprit.consomitounsi.sec;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tn.esprit.consomitounsi.services.impl.UserServices;




@WebFilter("/verify-account.jsf")
public class EmailVerificationFilter implements Filter {

	@EJB
	UserServices userServices;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String token = ((HttpServletRequest) request).getParameter("token");
		if (userServices.verifyEmail(token)) {
			chain.doFilter(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}
	
	@Override
	public void destroy() {

	}
}