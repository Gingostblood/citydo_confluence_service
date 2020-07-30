package cn.gingost.system.repository;

import cn.gingost.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:44
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
}
