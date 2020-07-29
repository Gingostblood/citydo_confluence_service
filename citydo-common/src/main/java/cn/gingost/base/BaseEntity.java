package cn.gingost.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author:lezzy
 * @Date:2020/7/24 17:54
 */

@Getter
@Setter
//1.@MappedSuperclass注解使用在父类上面，是用来标识父类的
//
//2.@MappedSuperclass标识的类表示其不能映射到数据库表，因为其不是一个完整的实体类，但是它所拥有的属性能够隐射在其子类对用的数据库表中
//
//3.@MappedSuperclass标识得嘞不能再有@Entity或@Table注解
@MappedSuperclass
public class BaseEntity {
    //@Null  被注释的元素必须为null
    //@NotNull  被注释的元素不能为null
    //@AssertTrue  被注释的元素必须为true
    //@AssertFalse  被注释的元素必须为false
    //@Min(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
    //@Max(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
    //@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
    //@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
    //@Size(max,min)  被注释的元素的大小必须在指定的范围内。
    //@Digits(integer,fraction)  被注释的元素必须是一个数字，其值必须在可接受的范围内
    //@Past  被注释的元素必须是一个过去的日期
    //@Future  被注释的元素必须是一个将来的日期
    //@Pattern(value) 被注释的元素必须符合指定的正则表达式。
    //@Email 被注释的元素必须是电子邮件地址
    //@Length 被注释的字符串的大小必须在指定的范围内
    //@NotEmpty  被注释的字符串必须非空
    //@Range  被注释的元素必须在合适的范围内
    @Id
    //产生主键，通过strategy属性指定，默认情况下，JPA 自动选择一个最适合底层数据库的主键生成策略：SqlServer对应identity，MySQL 对应 auto increment
    //–IDENTITY：采用数据库ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
    //–AUTO： JPA自动选择合适的策略，是默认选项；
    //–SEQUENCE：通过序列产生主键，通过@SequenceGenerator 注解指定序列名，MySql不支持这种方式
    //–TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //用来标识实体类中属性与数据表中字段的对应关系
    @Column(name = "id", columnDefinition = "BIGINT(20) comment '主键id'")
    //group标识分组校验
    @NotNull(message = "主键不能为空", groups = Update.class)
    private Long id;

    @Column(name = "create_time")
    //格式化时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //使用该注解可以让Hibernate在插入时针对注解的属性对应的日期类型创建默认值。
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //自动设置更新时间
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "operator")
    private String operator=getOperator();

    @Column(name = "is_delete")
    private Boolean isDelete=false;

    private String getOperator(){
        return "lezzy";
    }
    public String getRespOperator(){
        return this.operator;
    }

    public @interface Update {
    }

    public @interface Create {
    }
}
