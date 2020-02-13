package com.diee.sellics.demo.service;

import com.diee.sellics.demo.dto.EstimationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
/**
 * Class that brings services to calculate a Estimation
 */
public class EstimationService {

    private static final int MAX_ESTIMATION = 100;

    @Autowired
    AmazonCompletionService amazonCompletionService;

    /**
     * Method that estimates the score for a specific term received as a parameter.
     * 1- Creating a list of sub-keywords from the given keyword
     * 2-
     * @param keyword to be estimated inside the method
     * @return a object with information about the keyword and its calculated score
     */
    public EstimationDTO getEstimation(String keyword) {

        int keywordSize = keyword.length();
        boolean found = false;
        int counter = 0;

        // Create a list of sub-keywords
        List<String> substrings = IntStream.range(0, keyword.length())
                .mapToObj(i -> keyword.substring(0, i + 1)).collect(Collectors.toList());

        Iterator<String> keywordsIterator = substrings.iterator();
        while (!found && keywordsIterator.hasNext()) {
            String subKeyword = keywordsIterator.next();
            // get the keywords from Amazon Completion by a sub-keyword
            List<String> completedKeywords = amazonCompletionService.getCompletions(subKeyword);
            counter++;
            // search if the keyword we want to estimate is found in the results of Amazon Completion
            found = completedKeywords.stream().anyMatch(k -> keyword.equals(k));
        }
        // Automatically we return the 100 score because with the first letter of the given keyword,
        // the keyword was found in the top 10 amazon completion results
        if(counter == 1) return new EstimationDTO(keyword, MAX_ESTIMATION);
        else return new EstimationDTO(keyword, calculateEstimation(keywordSize, counter));
    }

    /**
     * Method that receiving two parameters and calculates the score with the following formula:
     * (N – i + 1) / N
     * Where:
     *      N: the size of the keyword
     *      i: the position of the current sub-keyword
     *      1: constant to accurate the result needed to exclude the first weight.
     *
     * @param keywordSize Size of the keyword
     * @param counter Number of the sub-keyword which found the keyword in the Amazon Completion
     * @return estimation of the score for the keyword
     */
    private Integer calculateEstimation(int keywordSize, int counter) {
        if(counter == keywordSize) return 0;
        // we add +1 because we need to take out the first case when the keyword is found in the first iteration.
        else return Math.round(((float)(keywordSize - counter + 1) / keywordSize) * 100);
    }
}
