package cn.gingost.security.rest;

import cn.gingost.annotation.AnonymousAccess;
import cn.gingost.exception.BadRequestException;
import cn.gingost.security.domain.AuthUser;
import cn.gingost.security.domain.JwtProperties;
import cn.gingost.security.domain.JwtUser;
import cn.gingost.security.service.OnlineUserService;
import cn.gingost.security.token.TokenProvider;
import cn.gingost.utils.RedisUtils;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author:lezzy
 * @Date:2020/7/29 14:10
 */

@Api(tags ="系统：授权管理")
@RequestMapping("/api/auth/")
@AllArgsConstructor
@RestController
public class AuthController {


    private RedisUtils redisUtils;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private TokenProvider tokenProvider;
    private OnlineUserService onlineUserService;
    private JwtProperties jwtProperties;


    @ApiOperation("获取验证码")
    @AnonymousAccess
    @GetMapping("/captcha")
    public ResponseEntity getCaptcha() throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        //设置验证码格式为 数字+字母
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        //设置验证码字体
        specCaptcha.setFont(Captcha.FONT_4);
        //获取结果值
        String code = specCaptcha.text().toLowerCase();
        //生成唯一id
        String uuid= UUID.randomUUID().toString();
        redisUtils.set(jwtProperties.getCodeKey().concat(uuid),code,60);
        Map<String,Object> map=new HashMap<String,Object>(){{
            put("uuid",uuid);
            put("img",specCaptcha.toBase64());
            put("res",code);
        }};
        return new ResponseEntity(map,HttpStatus.OK);
    }

    @ApiOperation("用户登录")
    @AnonymousAccess
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request){
        String code= (String) redisUtils.get(jwtProperties.getCodeKey().concat(authUser.getUuid()));
        redisUtils.del(jwtProperties.getCodeKey().concat(authUser.getUuid()));
        Map<String,Object> map=new HashMap<>();
        if (StringUtils.isEmpty(code)){
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isEmpty(authUser.getCode())||!code.equalsIgnoreCase(code)){
            throw new BadRequestException("验证码错误");
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(authUser.getUsername(),authUser.getPassword());
            Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token=tokenProvider.createToken(authenticate);
            JwtUser jwtUser= (JwtUser) authenticate.getPrincipal();
            onlineUserService.save(jwtUser,token,request);
            map.put("token",jwtProperties.getTokenStartWith()+token);
            map.put("user",jwtUser);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new BadRequestException("网络繁忙#001");
        }
        return new ResponseEntity(map,HttpStatus.OK);
    }

}
