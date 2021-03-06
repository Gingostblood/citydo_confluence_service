package cn.gingost.system.repository;

import cn.gingost.system.entity.Dept;
import com.google.common.collect.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author:lezzy
 * @Date:2020/7/27 11:57
 */
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept> {

    List<Dept> findDeptsByPid(Long pid);

    Dept findDeptByNickName(String nickName);
}
