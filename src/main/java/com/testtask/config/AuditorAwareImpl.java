package com.testtask.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            UserDetails UserDetails = ((UserDetails) principal);
            return Optional.of(UserDetails.getUsername());
        } catch (Exception e) {
            return Optional.of(principal.toString());
        }
    }
}