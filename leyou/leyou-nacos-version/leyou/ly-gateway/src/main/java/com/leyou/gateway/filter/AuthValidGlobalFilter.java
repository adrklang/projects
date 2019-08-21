package com.leyou.gateway.filter;

import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JwtUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@EnableConfigurationProperties({FilterProperties.class, JwtProperties.class})
public class AuthValidGlobalFilter implements GlobalFilter , Ordered {
    
    @Autowired
    private JwtProperties jwtProperties;
    
    @Autowired
    private FilterProperties filterProperties;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        if(isAllowPath(uri.getPath())){
            return chain.filter(exchange);
        }
        List<HttpCookie> httpCookies = exchange.getRequest().getCookies().get(jwtProperties.getCookieName());
        if(CollectionUtils.isEmpty(httpCookies)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response.setComplete();
        }
        String value = httpCookies.get(0).getValue();
        try {
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(value, jwtProperties.getPublicKey());
        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isAllowPath(String path) {
        List<String> allowPaths = filterProperties.getAllowPaths();
        for (String allowPath : allowPaths) {
            if (path.startsWith(allowPath)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
