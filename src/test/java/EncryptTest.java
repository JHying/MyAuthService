
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
            //?????? AES ??????
            System.out.println("-------------AES test start.--------------");
            SecretKey key = AESUtil.loadSecretKey("secretKey.jks");
            System.out.println("AES Key: " + AESUtil.keyToString(key));
            //?????? RSA ????????????
            String encodeKey = RSAUtil.encrypt(AESUtil.keyToString(key).getBytes(), RSAUtil.loadPublicKey("publicKey.jks"));
            System.out.println("RSA encodeKey: " + encodeKey);
            //?????? RSA ????????????
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
            //??????
            String plainText = "test123";
            //?????? AES ??????
            SecretKey key = AESUtil.loadSecretKey("secretKey.jks");
            System.out.println("plainText: " + plainText);
            System.out.println("Key: " + AESUtil.keyToString(key));
            //?????? AES ????????????
            String encodeText = AESUtil.encrypt(plainText, key);
            System.out.println("AES encodeText: " + encodeText);
            //?????? RSA ????????????
            String encodeKey = RSAUtil.encrypt(AESUtil.keyToString(key).getBytes(), RSAUtil.loadPublicKey("publicKey.jks"));
            System.out.println("RSA encodeKey: " + encodeKey);
            System.out.println("-------------encrypt test finished.--------------");
            //?????? RSA ????????????
            String decodeKey = RSAUtil.decrypt(encodeKey.getBytes(), RSAUtil.loadPrivateKey("privateKey.jks"));
            System.out.println("RSA decodeKey: " + decodeKey);
            //?????? AES ????????????
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
            //?????? RSA ????????????
            String encodeKey = RSAUtil.encrypt("1qaz@WSX3edc$RFV".getBytes(), RSAUtil.loadPublicKey("publicKey.jks"));
            System.out.println("RSA encodeKey: " + encodeKey);
            System.out.println("-------------encrypt test finished.--------------");
            //?????? RSA ????????????
            String decodeKey = RSAUtil.decrypt(encodeKey.getBytes(), RSAUtil.loadPrivateKey("privateKey.jks"));
            System.out.println("RSA decodeKey: " + decodeKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
