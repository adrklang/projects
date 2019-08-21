package com.leyou.gateway.handler;

import com.lh.auto.validcode.model.ValidCode;
import com.lh.auto.validcode.service.ValidCodeService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ValidCodeHandler implements HandlerFunction<ServerResponse> {

    private final ValidCodeService validCodeService;
    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        ValidCode validCode = null;
        try {
            validCode = validCodeService.resultValidCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_GIF)
                .header("Pragma","no-cache")
                .header("Cache-Control","no-cache")
                .header("Expires","0")
                .body(BodyInserters.fromResource(new ByteArrayResource(validCode.getValidSteam().toByteArray())));
    }
}
