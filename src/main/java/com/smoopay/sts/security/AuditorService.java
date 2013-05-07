package com.smoopay.sts.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuditorService implements AuditorAware<String> {

	public String getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		if (authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			return user.getUsername();
		} else {
			return (String) authentication.getPrincipal();
		}
	}
}