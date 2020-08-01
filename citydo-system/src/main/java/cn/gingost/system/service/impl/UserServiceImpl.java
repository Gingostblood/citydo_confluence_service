package cn.gingost.system.service.impl;

import cn.gingost.base.BaseQuery;
import cn.gingost.exception.EntityExistException;
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
import cn.gingost.utils.QueryHelp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByNickName(username);
    }

    @Override
    public void saveUser(UserReqDto reqDto) {
        User userByName = findUserByName(reqDto.getNickName());
        if (Objects.nonNull(userByName)){
            User user = userMapper.toEntity(reqDto);
            if (Objects.nonNull(reqDto.getDeptId())){
                user.setDept(deptRepository.findById(reqDto.getDeptId()).orElseGet(Dept::new));
            }
            if (Objects.nonNull(reqDto.getRoleId())){
                BaseQuery baseQuery=new BaseQuery();
                baseQuery.setIds(reqDto.getRoleId());
                final Set<Role> roles = roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder)).stream().collect(Collectors.toSet());
                user.setRoles(roles);
            }
            if (Objects.nonNull(reqDto.getJobId())){
                user.setJob(jobRepository.findById(reqDto.getJobId()).orElseGet(Job::new));
            }
            userRepository.save(user);
        }else {
            throw new EntityExistException(User.class,"name");
        }

    }
}
