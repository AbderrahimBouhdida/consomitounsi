package tn.esprit.consomitounsi.sec;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.managedbeans.LoginBean;


@WebFilter("/account/*")
public class SecureAccountFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		LoginBean lb = (LoginBean) req.getSession().getAttribute("loginBean");
		if (lb != null && lb.getLoggedIn()) {
		
			try {
				
				// Validate the token
				Claims claims = LoginToken.decodeJWT(lb.getToken());
				String role = claims.get("Role", String.class);
				if (!Roles.valueOf(role).equals(Roles.GUEST)) {
					chain.doFilter(req, res);
					return;
				}

			} catch (Exception e) {
				System.out.println(e.toString());
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

		}
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		return;

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
