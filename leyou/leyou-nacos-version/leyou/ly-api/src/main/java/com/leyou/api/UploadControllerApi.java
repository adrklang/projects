package com.leyou.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "文件上传",description = "图片上传")
public interface UploadControllerApi {

    @ApiOperation("图片上传接口")
    @PostMapping("image")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file);
}
