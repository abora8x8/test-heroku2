package com.x8.tokengenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenClaims {
    String id;
    String name;
    String avatar;
    String email;
    boolean moderator;
    List<Permissions> permissions;
}