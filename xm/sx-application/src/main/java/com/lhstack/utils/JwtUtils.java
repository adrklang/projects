package com.lhstack.utils;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.lhstack.pojo.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static Log log = LogFactory.get(JwtUtils.class);
    private static final JWSHeader HEADER = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    /**
     * 30分钟
     */
    private static final Long TIME = 30*60*1000L;

    private static final Long TESTTIME = 60 * 1000L;
    /**
     * 公共密钥
     */
    private static final byte[] SECRET = "1234567890qwertyuiopasdfghjklzxcvbnm".getBytes();
    public static String accessToken(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("ext",System.currentTimeMillis() + TIME);
        map.put("user",user);
        user.setPassword(null);
        user.setSalt(null);
        JWSObject jwsObject = new JWSObject(HEADER,new Payload(GsonUtils.createGson().toJson(map)));
        try {
            jwsObject.sign(new MACSigner(SECRET));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("签名失败");
            e.printStackTrace();
        }
        return null;
    }

    public static User validToken(String token){
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            // palload就是JWT构成的第二部分不过这里自定义的是私有声明(标准中注册的声明, 公共的声明)
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);
            if(jwsObject.verify(verifier)) {
                JSONObject jsonObject = payload.toJSONObject();
                // 若payload包含ext字段，则校验是否过期
                if(jsonObject.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonObject.get("ext").toString());
                    long curTime = System.currentTimeMillis();
                    // 过期了
                    if(curTime > extTime) {
                       return null;
                    }else{
                        String user = jsonObject.get("user").toString();
                        return GsonUtils.createGson().fromJson(user,User.class);
                    }
                }
                return GsonUtils.createGson().fromJson(jsonObject.get("user").toString(),User.class);
            } else {
                // 检验失败
                return null;
            }
        } catch (Exception e) {
            log.error("token 不合法");
            e.printStackTrace();
        }
        return null;
    }
}
