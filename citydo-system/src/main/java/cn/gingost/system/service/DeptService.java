package cn.gingost.system.service;

import cn.gingost.system.dto.resp.Tree;

import java.util.List;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:54
 */
public interface DeptService {

    List<Tree> getDeptTree(Long id);
}
