package cn.gingost.system.service.impl;

import cn.gingost.base.BaseQuery;
import cn.gingost.exception.BadRequestException;
import cn.gingost.exception.EntityExistException;
import cn.gingost.system.dto.req.DeptReqDto;
import cn.gingost.system.dto.resp.Tree;
import cn.gingost.system.entity.Dept;
import cn.gingost.system.entity.User;
import cn.gingost.system.mapper.DeptMapper;
import cn.gingost.system.repository.DeptRepository;
import cn.gingost.system.repository.UserRepository;
import cn.gingost.system.service.DeptService;
import cn.gingost.utils.FileUtil;
import cn.gingost.utils.QueryHelp;
import cn.gingost.utils.StringUtils;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:57
 */

@Service
@AllArgsConstructor
public class DeptServiceImpl implements DeptService {

    private DeptRepository deptRepository;
    private DeptMapper deptMapper;
    private UserRepository userRepository;

    @Override
    @Cacheable("#dept:list")
    public List<Tree> getDeptTree(Long id) {
        List<Tree> treeList = handlerEntityToDto(deptRepository.findDeptsByPid(id));
        for (Tree tree : treeList) {
            tree.setChildren(handlerEntityToDto(deptRepository.findDeptsByPid(tree.getId())));
            for (Tree tree1 : tree.getChildren()) {
                final List<Dept> deptsByPid = deptRepository.findDeptsByPid(tree1.getId());
                if (CollectionUtil.isNotEmpty(deptsByPid)) {
                    tree1.setChildren(handlerEntityToDto(deptRepository.findDeptsByPid(tree1.getId())));
                }
            }
        }
        return treeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "#dept:list", allEntries = true)
    public void saveDept(DeptReqDto deptReqDto) {
        if (StringUtils.isNotBlank(deptReqDto.getName())) {
            final Dept deptByNickName = deptRepository.findDeptByNickName(deptReqDto.getName());
            if (Objects.nonNull(deptByNickName)) {
                throw new EntityExistException(Dept.class, "name");
            } else {
                Dept dept = deptMapper.toEntity(deptReqDto);
                deptRepository.save(dept);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "#dept:list", allEntries = true)
    public void changeDept(DeptReqDto deptReqDto) {
        final Dept deptByNickName = deptRepository.findDeptByNickName(deptReqDto.getName());
        if (Objects.nonNull(deptByNickName)) {
            Dept dept = deptMapper.toEntity(deptReqDto);
            deptRepository.save(dept);
        } else
            throw new EntityExistException(Dept.class, "name");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delDept(Set<Long> ids) {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setIds(ids);
        List<Dept> all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        if (all.size() != ids.size()) {
            throw new BadRequestException("信息不匹配，请刷新后再试");
        }
        for (Dept dept : all) {
            List<Dept> deptsByPid = deptRepository.findDeptsByPid(dept.getId());
            if (CollectionUtil.isNotEmpty(deptsByPid)) {
                throw new BadRequestException("该部门下还存在其他子部门");
            }
        }
        try {
            deptRepository.deleteAll(all);
        } catch (Throwable e) {
            //有点小问题，没有捕获到，后面再看看
            throw new BadRequestException("该部门下还有关联员工");
        }

    }

    @Override
    public List<Dept> query(String name) {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setNickName(name);
        List<Dept> all = Lists.newArrayList();
        if (StringUtils.isNotBlank(name)) {
            all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        }else
            all=deptRepository.findAll();
        return all;
    }

    @Override
    public void download(BaseQuery baseQuery, HttpServletResponse response) {
        List<Dept> all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        List<Map<String,Object>> mapList=Lists.newArrayList();
        for (Dept dept:all){
            Map<String,Object> map=new LinkedHashMap<String,Object>(){{
                put("部门名称",dept.getNickName());
                put("创建时间",dept.getCreateTime());
                put("修改时间",dept.getUpdateTime());
                if (Objects.nonNull(dept.getUid())){
                    put("部门主管",userRepository.findById(dept.getUid()).orElseGet(User::new).getNickName());
                }
                if (dept.getPid()==0){
                    put("说明","一级部门");
                }else
                    put("说明","子部门");
            }};
            mapList.add(map);
        }
        try {
            FileUtil.downloadExcel(mapList,response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("网络繁忙#001");
        }
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
