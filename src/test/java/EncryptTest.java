
import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.GenerateKeyUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author H-yin on 2021.
 */
public class EncryptTest {

    @Test
    public void generateKey() {
        try {
            System.out.println("-------------generateKey start.-------------");
            RSAUtil.generateKey("publicKey.jks", "privateKey.jks");
            AESUtil.generateKey("secretKey.jks");
            System.out.println("-------------generateKey finished.-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void printKey() {
        try {
        	RSAUtil.printPrivateKeyInfo(
                    RSAUtil.loadPrivateKey("privateKey.jks"));
            RSAUtil.printPublicKeyInfo(
                    RSAUtil.loadPublicKey("publicKey.jks"));
            SecretKey secretKey = AESUtil.loadSecretKey("secretKey.jks");
            System.out.println(AESUtil.keyToString(secretKey));
            System.out.println("custom Key: " + GenerateKeyUtil.KEY);
            System.out.println("-------------printKey finished.-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void convertKey() {
        try {
        	System.out.println("-------------convert public key start.-------------");
        	String publicKey = RSAUtil.keyToString(RSAUtil.loadPublicKey("publicKey.pem").getEncoded());
        	System.out.println("-------------STRING-------------");
        	System.out.println(publicKey);	
        	System.out.println("-------------KEY-------------");
        	RSAUtil.printPublicKeyInfo(RSAUtil.getPublicKey(publicKey));
        	System.out.println("-------------convert secrey key start.-------------");
        	String secretKey = AESUtil.keyToString(AESUtil.loadSecretKey("secretKey.pem"));
        	System.out.println("-------------STRING-------------");
        	System.out.println(secretKey);	
        	System.out.println("-------------KEY-------------");
        	System.out.println(AESUtil.keyToString(AESUtil.getSecretKey(secretKey)));
            System.out.println("-------------printKey finished.-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AESTest() {
        try {
            //取得 AES 金鑰
            System.out.println("-------------AES test start.--------------");
            SecretKey key = AESUtil.loadSecretKey("secretKey.jks");
            System.out.println("AES Key: " + AESUtil.keyToString(key));
            //使用 RSA 加密金鑰
            String encodeKey = RSAUtil.encrypt(AESUtil.keyToString(key).getBytes(), RSAUtil.loadPublicKey("publicKey.jks"));
            System.out.println("RSA encodeKey: " + encodeKey);
            //使用 RSA 解密金鑰
            String decodeKey = RSAUtil.decrypt(encodeKey.getBytes(), RSAUtil.loadPrivateKey("privateKey.pem"));
            System.out.println("RSA decodeKey: " + decodeKey);
            System.out.println("-------------AES test finished.--------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptTest() {
        try {
            //原文
            String plainText = "test123";
            //生成 AES 金鑰
            SecretKey key = AESUtil.loadSecretKey("secretKey.jks");
            System.out.println("plainText: " + plainText);
            System.out.println("Key: " + AESUtil.keyToString(key));
            //使用 AES 加密原文
            String encodeText = AESUtil.encrypt(plainText, key);
            System.out.println("AES encodeText: " + encodeText);
            //使用 RSA 加密金鑰
            String encodeKey = RSAUtil.encrypt(AESUtil.keyToString(key).getBytes(), RSAUtil.loadPublicKey("publicKey.jks"));
            System.out.println("RSA encodeKey: " + encodeKey);
            System.out.println("-------------encrypt test finished.--------------");
            //使用 RSA 解密金鑰
            String decodeKey = RSAUtil.decrypt(encodeKey.getBytes(), RSAUtil.loadPrivateKey("privateKey.jks"));
            System.out.println("RSA decodeKey: " + decodeKey);
            //使用 AES 金鑰解密
            String decodeText = AESUtil.decrypt(encodeText.getBytes(), AESUtil.getSecretKey(decodeKey));
            System.out.println("AES decodeText: " + decodeText);
            System.out.println("-------------decrypt test finished.--------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tokenPassExcrypt() {
        try {
            //使用 RSA 加密金鑰
            String encodeKey = RSAUtil.encrypt("1qaz@WSX3edc$RFV".getBytes(), RSAUtil.loadPublicKey("publicKey.jks"));
            System.out.println("RSA encodeKey: " + encodeKey);
            System.out.println("-------------encrypt test finished.--------------");
            //使用 RSA 解密金鑰
            String decodeKey = RSAUtil.decrypt(encodeKey.getBytes(), RSAUtil.loadPrivateKey("privateKey.jks"));
            System.out.println("RSA decodeKey: " + decodeKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
