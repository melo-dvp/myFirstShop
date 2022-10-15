package com.ates.myfirstshop.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyResponse{
    String realm;
    @JsonProperty("public_key")
    String publicKey;
    @JsonProperty("token-service")
    String tokenService;
    @JsonProperty("account-service")
    String accountService;
    @JsonProperty("tokens-not-before")
    Integer tokensNotBefore;
}
