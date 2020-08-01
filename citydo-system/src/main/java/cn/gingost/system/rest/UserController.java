package cn.gingost.system.rest;

import cn.gingost.annotation.AnonymousAccess;
import cn.gingost.system.dto.req.UserReqDto;
import cn.gingost.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lezzy
 * @Date:2020/7/27 15:58
 */

@RestController
@RequestMapping("/api/sys/user/")
@Api(tags = "系统：用户管理")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @ApiOperation("新增用户")
    @AnonymousAccess
    @PostMapping("list")
    public ResponseEntity saveUser(@Validated(UserReqDto.Create.class) @RequestBody UserReqDto reqDto){
        userService.saveUser(reqDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
