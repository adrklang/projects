package com.leyou.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 现在增删改操作
 */
@Component
public class EditValidGatewayFilterFactory extends AbstractGatewayFilterFactory<EditValidGatewayFilterFactory.Config> {
    private static final String KEY = "methodsName";
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }
    public EditValidGatewayFilterFactory(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpMethod method = exchange.getRequest().getMethod();
            ServerHttpResponse response = exchange.getResponse();
            boolean b = config.getMethodsName().stream().anyMatch(methodName -> {
                return method.matches(methodName);
            });
            if(b){
                HttpHeaders headers = exchange.getRequest().getHeaders();
                List<String> editHeader = headers.get("editHeader");
                if(CollectionUtils.isEmpty(editHeader)){
                    return commonResponse(response);
                }
                if(editHeader.contains(method.name())){
                    return chain.filter(exchange);
                }
                return commonResponse(response);
            }
            return chain.filter(exchange);
        };

    }
    public  Mono<Void> commonResponse(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            return response.writeWith(Mono.just(response.bufferFactory().wrap(objectMapper.writeValueAsBytes("异常"))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response.setComplete();
    }
    @Data
    @Accessors(chain = true)
    public static class Config{
        private List<String> methodsName;
    }
}
