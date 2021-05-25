package webserver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import webserver.model.dao.Organization;
import webserver.model.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import webserver.model.dto.UserRegisterDto;
import webserver.repository.GroupRepository;
import webserver.repository.UserRepository;
import webserver.util.CodeUtil;
import webserver.util.PasswordUtil;
import util.StringUtil;
import webserver.vo.response.R;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;


    @ApiOperation("注册")
    @PostMapping("/register")
    @Transactional
    public R register(@RequestBody UserRegisterDto userRegisterDto) throws Exception{
        R response = R.error();
        if (!StringUtil.isNotEmpty(userRegisterDto.getPassword())) {
            response =  R.error("密码为空");
        }
        String secretPassword = PasswordUtil.passwordEncode(userRegisterDto.getPassword());
        // 使用邀请码
        if (userRegisterDto.getUseGroupCode()) {
            List<Organization> groupList = groupRepository.findByInvitationCode(
                    userRegisterDto.getGroupCode());
            if (groupList.size() == 0) {
                response = R.error("群组不存在");
            } else {
                Long groupId = groupList.get(0).getId();
                User user = new User(userRegisterDto.getUsername(),
                        userRegisterDto.getNickname(),
                        userRegisterDto.getTelephone(),
                        secretPassword,
                        0,
                        groupId
                        );
                userRepository.save(user);
                response = R.ok();
            }
        } else {
            // 创建用户时同时创建新团队
            User user = new User(userRegisterDto.getUsername(),
                    userRegisterDto.getNickname(),
                    userRegisterDto.getTelephone(),
                    secretPassword
            );
            user = userRepository.saveAndFlush(user);
            Organization group = new Organization(userRegisterDto.getGroupName(),
                    CodeUtil.getUUID_16(),
                    user.getId()
                    );
            group = groupRepository.saveAndFlush(group);
            user.setRole(1);
            user.setGroupId(group.getId());
            userRepository.save(user);
            response = R.ok();
        }
        return response;
    }

    @GetMapping("/getUserFullInfo")
    public R getUserFullInfo(@RequestParam("id") Long userId) {
        R response = R.ok();
        User user = userRepository.findById(userId).get();
        response.put("user",user);
        Organization group = groupRepository.findById(user.getGroupId()).get();
        response.put("group",group);
        List<User> userList = userRepository.findByGroupId(user.getGroupId());
        response.put("member",userList);
        return response;
    }


}
