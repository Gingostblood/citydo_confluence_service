package cn.gingost.system.mapper;

import cn.gingost.base.BaseMapper;
import cn.gingost.system.dto.req.UserReqDto;
import cn.gingost.system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Lezzy
 * @description
 * @data 2020/8/1 23:40
 **/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserReqDto, User> {
}
