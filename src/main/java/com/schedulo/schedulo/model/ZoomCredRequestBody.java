package com.schedulo.schedulo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZoomCredRequestBody {

    @JsonProperty(value = "grant_type")
    private String grantType;

    @JsonProperty(value = "account_id")
    private String accountId;
}
