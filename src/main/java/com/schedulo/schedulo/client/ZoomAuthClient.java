package com.schedulo.schedulo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient(name = "zoomClientAuth", url = "https://zoom.us")
public interface ZoomAuthClient {

    @PostMapping(value="/oauth/token", consumes = "application/x-www-form-urlencoded")
    Map<String, String> getZoomCred(@RequestHeader HttpHeaders headers,
                                    @RequestBody Map<String,?> requestBody);
}
