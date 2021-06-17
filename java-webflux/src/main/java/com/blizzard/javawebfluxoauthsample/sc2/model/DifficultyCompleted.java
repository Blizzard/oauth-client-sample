package com.blizzard.javawebfluxoauthsample.sc2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tygregory
 * @since 6/17/2021
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DifficultyCompleted(@JsonProperty("wings-of-liberty") String wingsOfLiberty,
                                  @JsonProperty("heart-of-the-swarm") String heartOfTheSwarm) {

}
