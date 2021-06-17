package com.blizzard.javawebmvcoauthsample.sc2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author tygregory
 * @since 6/17/2021
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Campaign(DifficultyCompleted difficultyCompleted) {

}
