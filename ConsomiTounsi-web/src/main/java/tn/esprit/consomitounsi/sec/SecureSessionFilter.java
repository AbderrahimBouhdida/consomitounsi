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

import tn.esprit.consomitounsi.entities.LostPass;
import tn.esprit.consomitounsi.services.impl.UserServices;


/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

@WebFilter("/password.jsf")
public class SecureSessionFilter implements Filter {

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
		String token = ((HttpServletRequest) request).getParameter("vtoken");
		LostPass lp = userServices.findRequestPass(token);
		if (lp == null) {
			System.out.println("no valid request");
			 response
             .sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else {
			long diff = (System.currentTimeMillis() - lp.getTimestamp().getTime()) / 60000;
			System.out.println("diff   " + diff);

			if (diff > 30) {
				System.out.println("expired");
				userServices.removeRequest(lp);
				response
	             .sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}else {
			   
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		
	}
}