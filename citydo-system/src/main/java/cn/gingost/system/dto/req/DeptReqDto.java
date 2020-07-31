package cn.gingost.system.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:lezzy
 * @Date:2020/7/30 11:01
 */

@Data
@ApiModel("部门信息")
public class DeptReqDto {
    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空",groups = Update.class)
    private Long id;

    @ApiModelProperty("部门名")
    @NotBlank(message = "部门名不能为空",groups ={Update.class,Create.class})
    private String name;

    @ApiModelProperty("部门主管id")
    private Long userId;

    @ApiModelProperty("上级部门id")
    @NotNull(message = "上级部门不能为空",groups = {Update.class,Create.class})
    private Long deptPid;

    public @interface Update{};
    public @interface Create{};
}
