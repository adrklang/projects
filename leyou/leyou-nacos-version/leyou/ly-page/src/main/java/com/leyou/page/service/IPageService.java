package com.leyou.page.service;

import java.util.Map;

public interface IPageService {
    Map<String, Object> loadModel(Long spuId);

    void createHtml(Long spuId);

    void deleteHtml(Long spuId);
}
