package cn.gingost.system.rest;

import cn.gingost.annotation.AnonymousAccess;
import cn.gingost.base.BaseQuery;
import cn.gingost.system.dto.req.UserReqDto;
import cn.gingost.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    @PreAuthorize("@ad.check('user:add')")
    @PostMapping("list")
    public ResponseEntity saveUser(@Validated(UserReqDto.Create.class) @RequestBody UserReqDto reqDto){
        userService.saveUser(reqDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("用户列表")
    @PreAuthorize("@ad.check('user:list')")
    @GetMapping("list")
    public ResponseEntity getUserList(BaseQuery baseQuery, @PageableDefault(value = 15,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity(userService.getUserList(baseQuery,pageable),HttpStatus.OK);
    }

    @ApiOperation("导出用户列表")
    @PreAuthorize("@ad.check('user:list')")
    @GetMapping("download")
    public void download(BaseQuery baseQuery, HttpServletResponse response){
        userService.download(baseQuery,response);
    }

    @ApiOperation("修改用户")
    public ResponseEntity changeUser(UserReqDto reqDto){
        userService.changeUser(reqDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
