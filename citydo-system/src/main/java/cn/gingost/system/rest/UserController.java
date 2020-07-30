package cn.gingost.system.rest;

import cn.gingost.annotation.AnonymousAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author:lezzy
 * @Date:2020/7/27 15:58
 */

@RestController
@RequestMapping("/api/user/")
@Api(tags = "系统：用户管理")
public class UserController {

    @ApiOperation("带 get")
    @GetMapping("findUser")
    @AnonymousAccess
    public ResponseEntity findUser() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @ApiOperation("不带 del")
    @DeleteMapping("findUser")
    public ResponseEntity delUser() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @ApiOperation("不带 put")
    @PutMapping("putUser")
    public ResponseEntity putUser() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @ApiOperation("不带 post")
    @PostMapping("findUser")
    public ResponseEntity postUser() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @ApiOperation("不带 other")
    @GetMapping("test")
    public ResponseEntity test() {
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
