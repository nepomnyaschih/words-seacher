package com.example.demo.search;

import java.util.List;
import java.util.Map;

public interface ISearchService {
    Map<String, Integer> call(String text, List<String> words, Boolean ignoreCase);
}
