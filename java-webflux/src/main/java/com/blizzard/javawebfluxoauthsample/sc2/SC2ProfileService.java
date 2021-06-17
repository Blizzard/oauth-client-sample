package com.blizzard.javawebfluxoauthsample.sc2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.blizzard.javawebfluxoauthsample.sc2.model.AccountResponse;
import com.blizzard.javawebfluxoauthsample.sc2.model.ProfileResponse;

import reactor.core.publisher.Mono;

/**
 * Simple service for interacting with the StarCraft 2 Community APIs.
 *
 * @author tygregory
 * @since 6/17/2021
 */
@Service
public class SC2ProfileService {

	private final WebClient sc2ProfileWebClient;

	@Autowired
	public SC2ProfileService(
			@Qualifier("oauth-webclient-builder") WebClient.Builder webClientBuilder,
			@Value("${blizzard.api.host}") String host
	) {
		this.sc2ProfileWebClient = webClientBuilder.baseUrl(host).build();
	}

	/**
	 * Retrieves the profile for the specified ID, realm, and region.
	 */
	public Mono<ProfileResponse> getProfile(String profileId, String realmId, String regionId) {
		return sc2ProfileWebClient.get()
				.uri("/sc2/profile/{regionId}/{realmId}/{profileId}", regionId, realmId, profileId)
				.retrieve()
				.bodyToMono(ProfileResponse.class);
	}

	/**
	 * Retrieves the account information for the specified account ID. API returns a list of accounts, but for
	 * simplicity, we will only return the first of these.
	 */
	public Mono<AccountResponse> getAccount(String accountId) {
		return sc2ProfileWebClient.get()
				.uri("/sc2/player/{accountId}", accountId)
				.retrieve()
				.bodyToFlux(AccountResponse.class)
				.single();
	}

}
