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
 *
 */
public class EstimationService {

    private static final int MAX_ESTIMATION = 100;
    @Autowired
    AmazonCompletionService amazonCompletionService;

    /**
     *
     * @param keyword
     * @return
     */
    public EstimationDTO getEstimation(String keyword) {


        List<String> substrings = IntStream.range(0, keyword.length())
                .mapToObj(i -> keyword.substring(0, i + 1)).collect(Collectors.toList());
        int keywordSize = keyword.length();
        boolean found = false;
        int counter = 0;
        Iterator<String> keywordsIterator = substrings.iterator();
        while (!found && keywordsIterator.hasNext()) {
            String subKeyword = keywordsIterator.next();
            List<String> completedKeywords = amazonCompletionService.getAutocompletions(subKeyword);
            counter++;
            found = completedKeywords.stream().anyMatch(k -> keyword.equals(k));
        }
        // Automatically we return the 100 score because with the first letter of the keyword,
        // the keyword was found in the top 10 amazon completion results

        if(counter == 1) return new EstimationDTO(keyword, MAX_ESTIMATION);
        else return new EstimationDTO(keyword, calculateEstimation(keywordSize, counter));
    }

    /**
     *
     * @param keywordSize
     * @param counter
     * @return
     */
    private Integer calculateEstimation(int keywordSize, int counter) {
        if(counter == keywordSize) return 0;
        else return Math.round(((float)(keywordSize - counter) / keywordSize) * 100);
    }
}
