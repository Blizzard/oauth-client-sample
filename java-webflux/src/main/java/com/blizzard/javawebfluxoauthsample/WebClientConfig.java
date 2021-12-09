package com.blizzard.javawebfluxoauthsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for the HTTP client, used to interact with REST APIs. The {@code ServerOAuth2AuthorizedClientExchangeFilterFunction}
 * will discover the currently logged-in user and add an Authorization header to the request with a bearer token.
 * Increasing the buffer size is required in order to interface with some APIs.
 *
 * @author tygregory
 * @since 6/17/2021
 */
@Configuration
public class WebClientConfig {

	@Bean("oauth-webclient-builder")
	public WebClient.Builder builder(
			ReactiveClientRegistrationRepository clientRegistrations,
			ServerOAuth2AuthorizedClientRepository authorizedClients
	) {
		final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
				new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
		oauth.setDefaultOAuth2AuthorizedClient(true);
		return WebClient.builder()
				.codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
						.maxInMemorySize(16 * 1024 * 1024)) // the size of the sc2 profile data exceeds the default max memory size
				.filter(oauth);
	}

}
