package com.lhstack.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.util.Properties;

public class FastdfsUtils {

    private TrackerClient trackerClient = null;

    private TrackerServer trackerServer = null;

    private StorageClient1 storageClient1 = null;

    private StorageServer storageServer = null;


    private Properties properties = ClassPathResourceUtils.getProperties("fastdfs.conf");

    public FastdfsUtils(){
        // 初始化配置文件
        try {
            ClientGlobal.init("fastdfs.conf"); // 创建跟踪器客户端对象
            trackerClient = new TrackerClient();
            // 获取跟踪器连接
            trackerServer = trackerClient.getConnection();
            // 获取存储器客户端对象
            storageClient1 = new StorageClient1(trackerServer, storageServer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

    }

    public String upload(byte[] bytes,String extName) throws IOException, MyException {
        String url = storageClient1.upload_file1(bytes, extName, null);
        String web_url = properties.getProperty("web_url", "http://192.168.73.129:8888");
        return web_url + url;
    }


    public byte[] download(String fileName){
        try {
            // 上传文件，返回文件标识
            /*"image/M00/00/00/wKhJgV1vJPCAQB95AAALGOg_juc062.txt"*/
            byte[] bytes = storageClient1.download_file1(fileName);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
