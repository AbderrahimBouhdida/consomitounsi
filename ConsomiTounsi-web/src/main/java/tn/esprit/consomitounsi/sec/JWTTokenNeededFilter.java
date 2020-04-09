package tn.esprit.consomitounsi.sec;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
import tn.esprit.consomitounsi.entities.Roles;


@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {
	@Context
	ResourceInfo resourceInfo;
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

    	
    	// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            //logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        System.out.println(token);
        try {
        	//getting values
        	Method resourceMethod = resourceInfo.getResourceMethod();
            JWTTokenNeeded methodAnnot = resourceMethod.getAnnotation(JWTTokenNeeded.class);
            //if (methodAnnot != null) {
              Roles[] roles = methodAnnot.roles();
            //}
        	// Validate the token
        	Claims claims = LoginToken.decodeJWT(token);
        	System.out.println("getting claims...");
        	String role = claims.get("Role",String.class);
        	System.out.println("Claims taken..");
        	List<Roles> lRoles = Arrays.asList(roles);
        	System.out.println("Role = "+role);
        	for (Roles roles1 : lRoles) {
				System.out.println("r : "+roles1);
			}
        	if(!lRoles.contains(Roles.valueOf(role))&& !role.equals(Roles.ADMIN.toString())&&!lRoles.contains(Roles.ALL))
                throw new NotAuthorizedException("You are not authorized");
            System.out.println("valid");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}