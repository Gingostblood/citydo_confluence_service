package cn.gingost.system.service.impl;

import cn.gingost.system.dto.req.DeptReqDto;
import cn.gingost.system.dto.resp.Tree;
import cn.gingost.system.entity.Dept;
import cn.gingost.system.repository.DeptRepository;
import cn.gingost.system.service.DeptService;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:57
 */

@Service
@AllArgsConstructor
public class DeptServiceImpl implements DeptService {
    private DeptRepository deptRepository;

    @Override
    public List<Tree> getDeptTree(Long id) {

        List<Tree> treeList = handlerEntityToDto(deptRepository.findDeptsByPid(id));
        for (Tree tree : treeList) {
            tree.setChildren(handlerEntityToDto(deptRepository.findDeptsByPid(tree.getId())));

            for (Tree tree1 : tree.getChildren()) {
                final List<Dept> deptsByPid = deptRepository.findDeptsByPid(tree1.getId());
                if (CollectionUtil.isNotEmpty(deptsByPid)){
                    tree1.setChildren(handlerEntityToDto(deptRepository.findDeptsByPid(tree1.getId())));
                }
            }

        }
        return treeList;
    }

    private List<Tree> handlerEntityToDto(List<Dept> deptList) {

        List<Tree> treeList = Lists.newArrayList();
        for (Dept dept : deptList) {
            Tree tree = new Tree();
            tree.setType(false);
            tree.setName(dept.getNickName());
            tree.setId(dept.getId());
            tree.setPid(dept.getPid());
            treeList.add(tree);
        }
        return treeList;
    }

}
