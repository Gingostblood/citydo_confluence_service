package cn.gingost.security.service;

import cn.gingost.security.domain.JwtProperties;
import cn.gingost.security.domain.JwtUser;
import cn.gingost.security.domain.OnlineUser;
import cn.gingost.utils.RedisUtils;
import cn.gingost.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author:lezzy
 * @Date:2020/7/27 15:40
 */
@Service
@AllArgsConstructor
public class OnlineUserService {
    private final JwtProperties jwtProperties;
    private RedisUtils redisUtils;

    public void save(JwtUser jwtUser, String token, HttpServletRequest request) {
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String cityinfo=StringUtils.getCityInfo(ip);
        OnlineUser onlineUser=new OnlineUser(jwtUser.getUsername(),jwtUser.getDept().getName(),browser,ip,cityinfo,token,new Date());
        redisUtils.set(jwtProperties.getOnlineKey().concat(onlineUser.getUsername()),onlineUser,jwtProperties.getTokenValidityInSeconds()/1000);
    }
}
