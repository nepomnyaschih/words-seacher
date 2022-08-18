package com.example.demo.search;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService implements ISearchService {

    @Override
    public Map<String, Integer> call(String text, List<String> words, Boolean ignoreCase) {

        var s = clearString(text);
        var parseWords = s.split("\\s+");

        Map<String, Integer> result = new HashMap<>(words.size());
        for (var a : words) {
            result.put(ignoreCase ? a.toLowerCase() : a, 0);
        }

        for (String parseWord : parseWords) {
            var c = ignoreCase ? parseWord.toLowerCase() : parseWord;
            if (result.containsKey(c)) {
                var count = result.get(c);
                count++;
                result.put(c, count);
            }
        }

        return result;
    }

    private String clearString(String s) {
        return s.replaceAll("[^A-z ]", "");
    }
}
