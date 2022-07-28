
import java.security.PrivateKey;

import org.junit.jupiter.api.Test;
import tw.hyin.java.utils.security.JwtUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author H-yin on 2021.
 */
public class JwtTest {

    @Test
    public void generateToken() {
        try {
            PrivateKey privateKey = RSAUtil.loadPrivateKey("privateKey.jks");
            System.out.println(JwtUtil.generateToken("testJwtUser", privateKey, 60000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
