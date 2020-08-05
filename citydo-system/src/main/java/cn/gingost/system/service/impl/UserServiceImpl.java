package cn.gingost.system.service.impl;

import cn.gingost.base.BaseQuery;
import cn.gingost.exception.BadRequestException;
import cn.gingost.exception.EntityExistException;
import cn.gingost.security.domain.JwtProperties;
import cn.gingost.security.service.OnlineUserService;
import cn.gingost.system.dto.req.UserReqDto;
import cn.gingost.system.entity.Dept;
import cn.gingost.system.entity.Job;
import cn.gingost.system.entity.Role;
import cn.gingost.system.entity.User;
import cn.gingost.system.mapper.UserMapper;
import cn.gingost.system.repository.DeptRepository;
import cn.gingost.system.repository.JobRepository;
import cn.gingost.system.repository.RoleRepository;
import cn.gingost.system.repository.UserRepository;
import cn.gingost.system.service.UserService;
import cn.gingost.utils.*;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author:lezzy
 * @Date:2020/7/27 20:17
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DeptRepository deptRepository;
    private JobRepository jobRepository;
    private UserMapper userMapper;
    private OnlineUserService onlineUserService;
    private RedisUtils redisUtils;
    private JwtProperties jwtProperties;

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByNickName(username);
    }

    @Override
    public void saveUser(UserReqDto reqDto) {
        User userByName = findUserByName(reqDto.getNickName());
        if (!Objects.nonNull(userByName)) {
            User user = userMapper.toEntity(reqDto);
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            if (Objects.nonNull(reqDto.getDeptId())) {
                Dept dept = new Dept(reqDto.getDeptId());
                user.setDept(dept);
            }
            if (Objects.nonNull(reqDto.getRoleId())) {
                Set<Role> roles = Sets.newHashSet();
                reqDto.getRoleId().forEach(r ->
                {
                    Role role = new Role(r);
                    roles.add(role);
                });
                user.setRoles(roles);
            }
//            if (Objects.nonNull(reqDto.getJobId())) {
//                Job job = new Job(reqDto.getJobId());
//                user.setJob(job);
//            }
            userRepository.save(user);
        } else {
            throw new EntityExistException(User.class, "name");
        }

    }

    @Override
    public Map getUserList(BaseQuery baseQuery, Pageable pageable) {
        Page<User> all;
        if (Objects.nonNull(baseQuery)) {
            all = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder), pageable);
        } else {
            all = userRepository.findAll(pageable);
        }
        return PageUtils.toPage(all);
    }

    @Override
    public void download(BaseQuery baseQuery, HttpServletResponse response) {
        List<User> all = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        List<Map<String, Object>> list = Lists.newArrayList();
        all.forEach(u -> {
            Map<String, Object> map = new LinkedHashMap<String, Object>() {{
                put("姓名", u.getNickName());
                put("性别", u.getSex());
                put("工号", u.getCard());
                put("部门", u.getDept() == null ? "无" : u.getDept().getNickName());
                //put("职务", u.getJob() == null ? "无" : u.getJob().getNickName());
                if (StringUtils.isNotBlank(u.getPhone())) {
                    put("联系电话", u.getPhone());
                }
                if (StringUtils.isNotBlank(u.getEmail())) {
                    put("联系邮箱", u.getEmail());
                }
            }};
            list.add(map);
        });
        try {
            FileUtil.downloadExcel(list, response);
        } catch (Exception e) {
            throw new BadRequestException("网络繁忙#001");
        }

    }

    @Override
    public void changeUser(UserReqDto reqDto) {
        User user = userRepository.findById(reqDto.getId()).orElseGet(User::new);
        final Set<Long> oldRoleId = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        String oldname = null;
        if (!reqDto.getNickName().equals(user.getNickName())) {
            User userByNickName = userRepository.findUserByNickName(reqDto.getNickName());
            if (Objects.nonNull(userByNickName)) {
                throw new EntityExistException(User.class, "name");
            }
            oldname = user.getNickName();
            user.setNickName(reqDto.getNickName());
        }
        /**
         * 省略电话、邮箱判定
         */
        if (CollectionUtil.isNotEmpty(reqDto.getRoleId()) && reqDto.getDeptId() != null) {
            Boolean flag = false;
            if (reqDto.getRoleId().size() != user.getRoles().size()) {
                setNewRoles(user, reqDto.getRoleId());
                flag = true;
            }
            Collection<Long> intersection = CollectionUtil.intersection(oldRoleId, reqDto.getRoleId());
            if (!(CollectionUtil.isNotEmpty(intersection) && intersection.size() == oldRoleId.size())) {
                setNewRoles(user, reqDto.getRoleId());
                flag = true;
            }
            user.setDept(new Dept(reqDto.getDeptId()));
            //user.setJob(new Job(reqDto.getJobId()));
            user.setCard(reqDto.getCard());
            userRepository.save(user);
            if (StringUtils.isNotBlank(oldname)) {
                redisUtils.del(jwtProperties.getOnlineKey().concat(oldname));
            }
            if (flag) {
                redisUtils.del("user-rloe:" + oldname);
            }
        } else {
            throw new BadRequestException("请检查输入数据");
        }
    }

    private User setNewRoles(User user, Set<Long> roleId) {
        Set<Role> roles = new HashSet<>();
        roleId.forEach(id -> {
            Role role = new Role(id);
            roles.add(role);
        });
        user.setRoles(roles);
        return user;
    }
}
