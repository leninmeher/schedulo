package com.schedulo.schedulo.utils.zoom;


import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

public class ZoomAuthentication {

    @Value("${zoom.clientId}")
    private String clientId;

    @Value("${zoom.clientSecret}")
    private String clientSecret;

    @Value("${zoom.accountId}")
    private String accountId;
}
