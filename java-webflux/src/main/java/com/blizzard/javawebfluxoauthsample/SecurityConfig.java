package com.blizzard.javawebfluxoauthsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Customization of Spring Security, enabling OAuth2 login for all requests and providing a logout handler to ensure
 * proper session destruction.
 *
 * @author tygregory
 * @since 6/16/2021
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

	/**
	 * Creates a logout handler to invoke the Battle.net logout endpoint. This will ensure the user is actually logged
	 * out of their Battle.net account before returning to the website. If this is not done, Battle.net login will still
	 * have an active session for the user and they will be auto logged into that account.
	 */
	RedirectServerLogoutSuccessHandler logoutSuccessHandler() {
		RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
		final UriComponents logoutRedirect = UriComponentsBuilder.fromHttpUrl("https://battle.net")
				.pathSegment("login", "logout")
				.queryParam("ref", "http://localhost:8080") // replace this with the actual endpoint of your service
				.build();
		successHandler.setLogoutSuccessUrl(logoutRedirect.toUri());
		return successHandler;
	}

	/**
	 * Ensures all requests are authorized. You may want to have a home page where the user isn't asked to login.
	 * Configures OAuth 2.0 Client support. Leverages OAuth 2.0 Authorization Code Grant Flow for login. And uses the
	 * logout handler created above for redirecting users after a successful logout.
	 */
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
				.authorizeExchange().anyExchange().authenticated()
				.and().oauth2Client()
				.and().oauth2Login()
				.and().logout().logoutSuccessHandler(logoutSuccessHandler());
		return http.build();
	}

}
