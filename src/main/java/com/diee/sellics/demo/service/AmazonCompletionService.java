package com.diee.sellics.demo.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmazonCompletionService {

    public static final String APPLICATION_JSON = "application/json";
    private String baseURL = "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public AmazonCompletionService() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", APPLICATION_JSON);
        headers.add("Accept", APPLICATION_JSON);
    }

    public JsonArray doGet(String query) {

        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(baseURL + "&q=" + query + "", HttpMethod.GET, requestEntity, String.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return new JsonParser().parse(responseEntity.getBody()).getAsJsonArray().get(1).getAsJsonArray();
        }
        return null;
    }


}