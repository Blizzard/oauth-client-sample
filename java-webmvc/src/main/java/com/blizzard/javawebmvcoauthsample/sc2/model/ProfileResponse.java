package com.blizzard.javawebmvcoauthsample.sc2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Root response object from the SC2 Profile API
 *
 * @author tygregory
 * @since 6/17/2021
 * @link https://develop.battle.net/documentation/starcraft-2/community-apis
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileResponse(Summary summary, Campaign campaign) {

}
