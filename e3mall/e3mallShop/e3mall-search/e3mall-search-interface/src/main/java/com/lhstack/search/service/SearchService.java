package com.lhstack.search.service;


import com.lhstack.common.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String keyword, int page, int rows)  throws Exception;
}
