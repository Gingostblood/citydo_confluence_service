package cn.gingost.system.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:lezzy
 * @Date:2020/7/30 11:01
 */

@Data
@ApiModel("部门信息")
public class DeptReqDto {
    @ApiModelProperty("部门id")
    private Long id;

    @ApiModelProperty("部门名")
    private String name;

    @ApiModelProperty("部门主管id")
    private Long userId;

    @ApiModelProperty("上级部门id")
    private Long deptPid;
}
