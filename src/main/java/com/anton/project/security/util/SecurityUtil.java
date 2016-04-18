package com.anton.project.security.util;

import java.security.Principal;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static String getUsername() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null) {
	        throw new AuthenticationCredentialsNotFoundException("Missing authentication object.");
	    }
	    
	    return authentication.getName();
	}
}
