package com.bit.microservices.mitra.configuration;

import com.bit.microservices.mitra.filter.UserContextData;
import com.bit.microservices.mitra.filter.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

		UserData userData = UserContextData.getUserData();
		String name = userData.getUserName();
		String userId = userData.getUserId();

		return Optional.of(name+" ("+userId+")");
//    	return ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .map(Authentication::getPrincipal)
//                .map(Jwt.class::cast)
//    	        .map(Jwt::getClaims)
//    			.map(claims -> {
//					return claims.get("userEmail").toString();
//				})
//    			.blockOptional();
    }

}
