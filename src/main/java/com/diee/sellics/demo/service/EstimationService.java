package com.diee.sellics.demo.service;

import com.diee.sellics.demo.dto.EstimationDTO;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimationService {

    @Autowired
    AmazonCompletionService amazonCompletionService;

    public EstimationDTO getEstimation(String keyword) {
        JsonArray result = amazonCompletionService.doGet(keyword);

        System.out.println(result.toString());
        return new EstimationDTO("Cargador", 19);
    }
}
