package cn.gingost.system.mapper;

import cn.gingost.base.BaseMapper;
import cn.gingost.system.dto.req.DeptReqDto;
import cn.gingost.system.entity.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * @author:lezzy
 * @Date:2020/7/30 16:27
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends BaseMapper<DeptReqDto, Dept> {

    @Mappings({
            @Mapping(source = "name",target = "nickName"),
            @Mapping(source = "userId",target = "uid"),
            @Mapping(source = "deptPid",target = "pid"),
    })
    Dept toEntity(DeptReqDto reqDto);
}
