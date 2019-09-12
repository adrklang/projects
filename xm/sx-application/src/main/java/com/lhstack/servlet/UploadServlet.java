package com.lhstack.servlet;

import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.pojo.res.Resp;
import com.lhstack.utils.FastdfsUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.csource.common.MyException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload/*")
public class UploadServlet extends AbstractServlet {

    FastdfsUtils fastdfsUtils = new FastdfsUtils();

    @RequestMapping(value = "file",method = RequestMapping.RequestConst.METHOD_POST)
    public void uploadImage(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        try {
            List<FileItem> list=upload.parseRequest(req);
            FileItem fileItem = list.get(0);
            String name = fileItem.getName();
            name = name.substring(name.lastIndexOf("." ) + 1);
            if(name.equalsIgnoreCase("jpg") || name.equalsIgnoreCase("jpeg") || name.equalsIgnoreCase("gif") || name.equalsIgnoreCase("png") || name.equalsIgnoreCase("webp")){
                if(fileItem.getSize() < (1024*1024*5)){
                    String url = fastdfsUtils.upload(fileItem.get(), name);
                    out(response,Resp.builder().setStatus(200).setState(true).setMessage(url));
                }else{
                    out(response,Resp.builder().setStatus(500).setState(false).setMessage("文件上传失败，文件过大"));
                }
            }else{
                out(response,Resp.builder().setStatus(500).setState(false).setMessage("文件上传失败，文件类型不匹配"));
            }
        } catch (FileUploadException e) {
            e.printStackTrace();

        } catch (MyException e) {
            e.printStackTrace();
        }
    }


}
