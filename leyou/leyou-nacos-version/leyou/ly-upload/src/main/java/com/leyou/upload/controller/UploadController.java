package com.leyou.upload.controller;

import com.leyou.api.UploadControllerApi;
import com.leyou.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController implements UploadControllerApi {

    @Autowired
    private IUploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @Override
    public ResponseEntity<String> uploadImage(MultipartFile file) {
        String url = uploadService.uploadImage(file);
        return ResponseEntity.ok(url);
    }

}
