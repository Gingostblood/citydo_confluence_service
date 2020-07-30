package cn.gingost.system.rest;

import cn.gingost.annotation.AnonymousAccess;
import cn.gingost.system.dto.req.DeptReqDto;
import cn.gingost.system.service.DeptService;
import cn.gingost.system.service.impl.DeptServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:55
 */

@RestController
@RequestMapping("/api/sys/dept/")
@Api(tags = "系统：部门管理")
@AllArgsConstructor
public class DeptController {
    private DeptService deptService;

    @GetMapping("list")
    @PreAuthorize("@ad.check('dept:list')")
    @ApiOperation("部门列表")
    public ResponseEntity getDeptTree(@RequestParam(defaultValue = "0") Long id){
        return new ResponseEntity(deptService.getDeptTree(id), HttpStatus.OK);
    }

    @PostMapping("list")
    @PreAuthorize("@ad.check()")
    @ApiOperation("新增部门")
    public ResponseEntity saveDept(@Validated @RequestBody DeptReqDto deptReqDto){
        deptService.saveDept(deptReqDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
