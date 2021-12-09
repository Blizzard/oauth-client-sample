package com.blizzard.javawebmvcoauthsample;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Customization of Spring Security, enabling OAuth2 login for all requests and providing a logout handler to ensure
 * proper session destruction.
 *
 * @author Tyler Gregory
 * @since 6/16/2021
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Creates a logout handler to invoke the Battle.net logout endpoint. This will ensure the user is actually logged
	 * out of their Battle.net account before returning to the website. If this is not done, Battle.net login will still
	 * have an active session for the user, and they will be auto logged into that account.
	 */
	LogoutSuccessHandler logoutSuccessHandler() {
		return (request, response, authentication) -> {
			final UriComponents logoutRedirect = UriComponentsBuilder.fromHttpUrl("https://battle.net")
					.pathSegment("login", "logout")
					.queryParam("ref", "http://localhost:8080") // replace this with the actual endpoint of your service
					.build();
			response.sendRedirect(logoutRedirect.toString());
		};
	}

	/**
	 * Ensures all requests are authorized. You may want to have a home page where the user isn't asked to login.
	 * Configures OAuth 2.0 Client support. Leverages OAuth 2.0 Authorization Code Grant Flow for login. And uses the
	 * logout handler created above for redirecting users after a successful logout.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorizeRequestsSpec -> authorizeRequestsSpec.anyRequest().authenticated())
				.oauth2Client()
				.and().oauth2Login()
				.and().logout(logoutSpec -> logoutSpec.logoutSuccessHandler(logoutSuccessHandler()));
	}

}
