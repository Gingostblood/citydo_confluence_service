package cn.gingost.system.rest;

import cn.gingost.annotation.AnonymousAccess;
import cn.gingost.base.BaseQuery;
import cn.gingost.system.dto.req.DeptReqDto;
import cn.gingost.system.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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
    public ResponseEntity saveDept(@Validated(DeptReqDto.Create.class) @RequestBody DeptReqDto deptReqDto){
        deptService.saveDept(deptReqDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("list")
    @PreAuthorize("@ad.check()")
    @ApiOperation("修改部门信息")
    public ResponseEntity changeDept(@Validated(DeptReqDto.Update.class) @RequestBody DeptReqDto deptReqDto){
        deptService.changeDept(deptReqDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("list")
    @PreAuthorize("@ad.check()")
    @ApiOperation("删除部门")
    public ResponseEntity delDept(@RequestBody Set<Long> ids){
        deptService.delDept(ids);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("query")
    //@PreAuthorize("@ad.check()")
    @AnonymousAccess
    @ApiOperation("模糊查询")
    public ResponseEntity query(String name){
        return new ResponseEntity(deptService.query(name),HttpStatus.OK);
    }

    @PostMapping("download")
    @AnonymousAccess
    @ApiOperation("导出部门列表")
    public ResponseEntity download(BaseQuery baseQuery,HttpServletResponse response){
        deptService.download(baseQuery,response);
        return new ResponseEntity(HttpStatus.OK);
    }
}
