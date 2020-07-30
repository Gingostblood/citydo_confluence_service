package cn.gingost.system.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author:lezzy
 * @Date:2020/7/30 11:09
 */
@Getter
@AllArgsConstructor
public enum DeptType {
    ZERO(0, "一级部门"),
    ONE(1, "二级部门"),
    TWO(2, "三级部门");


    private int value;
    private String desc;
}
