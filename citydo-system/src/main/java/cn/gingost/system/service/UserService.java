package cn.gingost.system.service;

import cn.gingost.base.BaseQuery;
import cn.gingost.system.dto.req.UserReqDto;
import cn.gingost.system.entity.User;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author:lezzy
 * @Date:2020/7/27 20:15
 */
public interface UserService {
    User findUserByName(String username);

    void saveUser(UserReqDto reqDto);

    Map getUserList(BaseQuery baseQuery, Pageable pageable);

    void download(BaseQuery baseQuery, HttpServletResponse response);

    void changeUser(UserReqDto reqDto);
}
