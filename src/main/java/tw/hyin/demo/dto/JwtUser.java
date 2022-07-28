package tw.hyin.demo.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 請求 token 允許之名單 (不符合拒絕訪問)
 *
 * @author H-yin
 */
public class JwtUser {

    @Getter
    private static final Map<String, String> user = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("test", "testPassword");
        }
    };

}
