package com.diee.sellics.demo.controller;

import com.diee.sellics.demo.dto.EstimationDTO;
import com.diee.sellics.demo.service.EstimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that exposes Estimation HTTP services
 */
@RestController
public class EstimationController {

    @Autowired
    EstimationService estimationService;

    /**
     * Method that expose a HTTP service to estimate an score for a give keyword received as a parameter
     * @param keyword to estimate a score.
     * @return
     */
    @GetMapping("/estimate")
    public EstimationDTO getEstimation(@RequestParam(name = "keyword") String keyword) {
        EstimationDTO estimationDTO = estimationService.getEstimation(keyword);
        return estimationDTO;

    }
}
