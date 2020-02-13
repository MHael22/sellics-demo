package com.diee.sellics.demo.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service that connects to the Amazon Completion Service in order to perform requests
 */
@Service
public class AmazonCompletionService {

    public static final String APPLICATION_JSON = "application/json";
    private static final String baseURL = "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    /**
     * Constructor: Initialize the Rest HTTP Connection
     */
    public AmazonCompletionService() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", APPLICATION_JSON);
        headers.add("Accept", APPLICATION_JSON);
    }

    /**
     * Method that performs a request to the Amazon Completion Service specifying a keyword to find matching keywords
     * @param keyword to find the keywords
     * @return a list with the keywords found by Amazon Completion
     */
    public List<String> getCompletions(String keyword) {

        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(baseURL + "&q=" + keyword + "", HttpMethod.GET, requestEntity, String.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK){

            JsonArray completedKeywords = new JsonParser().parse(responseEntity.getBody()).getAsJsonArray().get(1).getAsJsonArray();
            if(completedKeywords != null) return Arrays.asList((new Gson().fromJson(completedKeywords, String[].class)));
        }
        return new ArrayList<>();
    }


}
