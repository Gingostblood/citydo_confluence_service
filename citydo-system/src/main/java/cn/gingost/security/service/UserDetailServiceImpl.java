package cn.gingost.security.service;

import cn.gingost.exception.BadRequestException;
import cn.gingost.security.domain.JwtUser;
import cn.gingost.security.domain.dto.SmallDeptDto;
import cn.gingost.security.domain.dto.SmallJobDto;
import cn.gingost.system.entity.User;
import cn.gingost.system.service.RoleService;
import cn.gingost.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author:lezzy
 * @Date:2020/7/27 20:00
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private UserService userService;
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByName(username);
        if (user.getEnabled()==true){
            throw new BadRequestException("账号未激活，请联系管理员激活账号");
        }
        if (Objects.nonNull(user)){
            return createJwtUser(user);
        }else {
            throw new RuntimeException("账号不存在");
        }
    }

    private JwtUser createJwtUser(User user) {
        return new JwtUser(
                user.getId(),
                user.getNickName(),
                user.getPassword(),
                user.getSex(),
                null,
                user.getPhone(),
                user.getEmail(),
                user.getCard(),
                new SmallDeptDto(user.getDept().getId(),user.getDept().getNickName()),
                //new SmallJobDto(user.getJob().getId(),user.getJob().getNickName()),
                roleService.findAllPermission(user.getId(),user.getNickName())
        );
    }
}
