package webserver.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import webserver.model.dao.User;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TokenUtil {

    //默认失效时间12个小时
    private static final long EXPIRE_TIME = (60 * 60 * 1000 * 12);
    private static final String TOKEN_SECRET = "token123";  //密钥盐

    public static void main(String[] args) {
        User user = new User();
        user.setPassword("122");
        user.setId(1L);
        verify(sign(user));
    }

    /**
     * 签名生成
     *
     * @param user
     * @return
     */
    public static String sign(User user) {

//        .withClaim("userId", user.getUserId()).withClaim("username", user.getUsername())
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("user", JSON.toJSONString(user))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;

    }


    /**
     * 签名验证
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            log.debug("认证通过：");
            log.debug("issuer: " + jwt.getIssuer());
            log.debug("user: " + jwt.getClaim("user").asString());
            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            Date at = jwt.getExpiresAt();
            log.debug("过期时间：      " + sdf.format(at));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中解析用户
     *
     * @param token
     * @return
     */
    public static User getUser(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            String user = jwt.getClaim("user").asString();
            User userEntity = JSON.parseObject(user, User.class);
            return userEntity;
        } catch (Exception e) {
            return null;
        }
    }

}
