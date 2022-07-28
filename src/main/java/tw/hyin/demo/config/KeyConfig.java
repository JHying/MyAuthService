package tw.hyin.demo.config;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author rita6 on 2021.
 */
@Configuration
public class KeyConfig {

    public static PrivateKey privateKey = null;
    public static PublicKey publicKey = null;
    public static SecretKey secretKey = null;

    static {
        try {
            //取得金鑰 (local)
//            privateKey = RSAUtil.loadPrivateKey("privateKey.jks");
//            publicKey = RSAUtil.loadPublicKey("publicKey.jks");
//            secretKey = AESUtil.loadSecretKey("secretKey.jks");
            //取得金鑰 (jar)
            privateKey = RSAUtil.loadPrivateKeyFromJAR("privateKey.jks");
            publicKey = RSAUtil.loadPublicKeyFromJAR("publicKey.jks");
            secretKey = AESUtil.loadSecretKeyFromJAR("secretKey.jks");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
