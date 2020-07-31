package cn.gingost.system.service;

import cn.gingost.system.dto.req.DeptReqDto;
import cn.gingost.system.dto.resp.Tree;

import java.util.List;
import java.util.Set;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:54
 */
public interface DeptService {

    List<Tree> getDeptTree(Long id);

    void saveDept(DeptReqDto deptReqDto);

    void changeDept(DeptReqDto deptReqDto);

    void delDept(Set<Long> id);
}
