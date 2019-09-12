import com.lhstack.common.activemq.ActivemqTemplate;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.User;
import com.lhstack.service.IUserService;
import com.lhstack.service.impl.UserServiceImpl;
import com.lhstack.utils.JwtUtils;
import net.minidev.json.JSONObject;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TestUpload {

    private TrackerClient trackerClient = null;

    private TrackerServer trackerServer = null;

    private StorageClient1 storageClient1 = null;

    private StorageServer storageServer = null;


    @Test
    public void testJwtGetToken(){
        IUserService userService = new UserServiceImpl();
        User user = userService.findByPhone("13668309572");
        String token = JwtUtils.accessToken(user);
        System.out.println(token);
    }

    @Test
    public void validToken(){

    }


    @Test
    public void testValid(){
        Random random = new Random();
        int validCode = random.nextInt(9999 - 1000) + 1000;
        System.out.println(validCode);
    }

    @Test
    public void testPage(){
        IUserService userService = new UserServiceImpl();
        Page<User> pages = userService.findByUserPage(1L, 2L);
        System.out.println(pages);
    }


    @Test
    public void testPhone(){
        IUserService userService = new UserServiceImpl();
        User admin = userService.findByPhone("13668309572");
        System.out.println(admin);
    }


    @Test
    public void testUser(){
        IUserService userService = new UserServiceImpl();
        User admin = userService.findByName("admin");
        System.out.println(admin);
    }


    @Test
    public void testJms(){
        JmsTemplate jmsTemplate = ActivemqTemplate.getActiveMQTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("key","test");
        map.put("value","123456");
        jmsTemplate.convertAndSend("sx_application_sms_queue",map);
    }


    @Test
    public void upload() {
        try {
            // 初始化配置文件
            ClientGlobal.init("fastdfs.conf");
            // 创建跟踪器客户端对象
            trackerClient = new TrackerClient();
            // 获取跟踪器连接
            trackerServer = trackerClient.getConnection();
            // 获取存储器客户端对象
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 上传文件，返回文件标识
            String index = storageClient1.upload_file1("C:\\Users\\hp\\Desktop\\timg.jpg", "jpg", null);
            // 查看标识
            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void download(){
        try {
            // 初始化配置文件
            ClientGlobal.init("fastdfs.conf");
            // 创建跟踪器客户端对象
            trackerClient = new TrackerClient();
            // 获取跟踪器连接
            trackerServer = trackerClient.getConnection();
            // 获取存储器客户端对象
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 上传文件，返回文件标识
            byte[] bytes = storageClient1.download_file1("image/M00/00/00/wKhJgV1vJPCAQB95AAALGOg_juc062.txt");
            System.out.println(bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
