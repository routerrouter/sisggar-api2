package dev.router.sisggar.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		//String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		return Optional.ofNullable("Router").filter(s -> !s.isEmpty()); // current user logged
	}
}
