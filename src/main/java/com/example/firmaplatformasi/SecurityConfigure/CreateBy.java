package com.example.firmaplatformasi.SecurityConfigure;

import com.example.firmaplatformasi.Entity.Users;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class CreateBy implements AuditorAware {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            Users user = (Users) authentication.getPrincipal();
            return Optional.of(user.getId());
        }

        return Optional.empty();
    }
}
