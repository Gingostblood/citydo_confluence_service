package cn.gingost.system.entity;

import cn.gingost.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author:lezzy
 * @Date:2020/7/27 17:51
 */

@Getter
@Setter
@Table(name = "job")
@Entity
public class Job extends BaseEntity {

    @Column(name = "nick_name")
    private String nickName;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @OneToOne(mappedBy = "job")
    @JsonIgnore
    private User user;
}
