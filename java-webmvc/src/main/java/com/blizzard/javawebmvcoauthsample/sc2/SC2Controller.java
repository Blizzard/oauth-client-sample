package com.blizzard.javawebmvcoauthsample.sc2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blizzard.javawebmvcoauthsample.sc2.model.AccountResponse;
import com.blizzard.javawebmvcoauthsample.sc2.model.ProfileResponse;

/**
 * Controller that gathers information about the logged in account's StarCraft 2 profile, adds the necessary values to
 * the model, then renders the sc2 template. Obtains the account ID from the logged-in user in order to fetch the
 * StarCraft 2 profile data.
 *
 * @author tygregory
 * @since 6/17/2021
 */
@Controller
@RequestMapping("/sc2")
public class SC2Controller {

	@Autowired
	private SC2ProfileService sc2ProfileService;

	@GetMapping
	public String getProfile(
			Model model,
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User
	) {
		final String accountId = authorizedClient.getPrincipalName();
		final AccountResponse account = sc2ProfileService.getAccount(accountId);
		final ProfileResponse profile = sc2ProfileService.getProfile(account.profileId(), account.realmId(), account.regionId());
		model.addAttribute("userName", oauth2User.getName());
		model.addAttribute("displayName", profile.summary().displayName());
		model.addAttribute("totalAchievementPoints", profile.summary().totalAchievementPoints());
		model.addAttribute("wolDifficulty", profile.campaign().difficultyCompleted().wingsOfLiberty());
		model.addAttribute("hotsDifficulty", profile.campaign().difficultyCompleted().heartOfTheSwarm());
		return "sc2";
	}

}
