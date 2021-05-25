package webserver.controller;

import io.swagger.annotations.ApiOperation;
import webserver.model.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webserver.repository.UserRepository;
import webserver.util.PasswordUtil;
import webserver.util.TokenUtil;
import webserver.vo.request.LoginReqVo;
import webserver.vo.response.R;

import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginReqVo loginReqVo) throws Exception {
        String username = loginReqVo.getUsername();
        List<User> userList = userRepository.findByUsername(username);
        R response = R.error("用户名或密码错误");
        User user;
        if (userList.size() == 0) {
            response = R.error("用户名不存在");
        } else if (userList.size() != 1) {
            response = R.error("存在重名用户");
        } else if (PasswordUtil.passwordJudge(loginReqVo.getPassword(), (user = userList.get(0)).getPassword())){
            final String token = TokenUtil.sign(user);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", loginReqVo.getUsername());
            map.put("token", token);
            map.put("id", user.getId() + "");
            response = R.ok().put("user", map);
        }
        return response;
    }

    @ApiOperation("刷新Token")
    @GetMapping("/refreshToken")
    public R refreshToken(@RequestParam("token") String token) {
        boolean verify = TokenUtil.verify(token);
        if (verify) {
            User userEntity = TokenUtil.getUser(token);
            String sign = TokenUtil.sign(userEntity);
            return R.ok().put("token", sign);

        }
        return R.error();
    }


}
