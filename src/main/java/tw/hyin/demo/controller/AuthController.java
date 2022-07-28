package tw.hyin.demo.controller;

import lombok.SneakyThrows;
import tw.hyin.demo.dto.LoginInfo;
import tw.hyin.demo.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.hyin.java.utils.Log;
import tw.hyin.java.utils.http.BaseController;
import tw.hyin.java.utils.http.ResponseObj;

import java.util.List;

@RestController
@ResponseBody
public class AuthController extends BaseController {


    private final AuthorityService authorityService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.redis.data.expireTime}")
    private String expireTime;

    @Autowired
    public AuthController(RedisTemplate<String, Object> redisTemplate, AuthorityService authorityService) {
        this.authorityService = authorityService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping(value = "/authlist/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObj<List<String>>> getAuthList(@RequestBody LoginInfo loginInfo) {
        return super.sendSuccessRsp(this.authorityService.getMyAuthUrls(loginInfo));
    }

    @SneakyThrows
    @GetMapping(value = "/rolepages/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObj<ResponseObj.RspMsg>> userRefresh() {
        this.authorityService.saveRoleResource(redisTemplate);
        Log.info("save role resource to Redis Complete.");
        return super.sendSuccessRsp(ResponseObj.RspMsg.SUCCESS);
    }


}
