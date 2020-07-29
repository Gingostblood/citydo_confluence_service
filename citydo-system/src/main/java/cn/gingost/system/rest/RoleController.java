package cn.gingost.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lezzy
 * @Date:2020/7/29 10:44
 */

@RestController
@RequestMapping("api/role/")
@Api(tags = "系统：角色管理")
public class RoleController {

    @ApiOperation("查询角色列表")
    @GetMapping("fku")
    public ResponseEntity findRoleList(){
        return new ResponseEntity("role", HttpStatus.OK);
    }
}
