package com.project.hero.bot.service;

import com.project.hero.bot.model.lostark.ArmoryProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class LostArkService {
    @Value("${lostark-api-key}")
    private String lostArk;
    private static final String REQUEST_URI = "https://developer-lostark.game.onstove.com/";
    private static String LOSTARK_API_KEY;

    @Value("${lostark-api-key}")
    public void setLostArk(String lostArk) {
        LostArkService.LOSTARK_API_KEY = lostArk;
    }

    // 유저 정보 조회
    public static ArmoryProfile getUserBasicStats(String name) {
        URI uri = UriComponentsBuilder
                .fromUriString(REQUEST_URI)
                .path("armories/characters/"+name+"/profiles")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + LOSTARK_API_KEY);

        HttpEntity request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArmoryProfile> response = restTemplate.exchange(uri, HttpMethod.GET, request, ArmoryProfile.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        return response.getBody();
    }
}
