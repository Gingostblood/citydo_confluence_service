package cn.gingost.system.entity;

import cn.gingost.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author:lezzy
 * @Date:2020/7/27 17:51
 */

@Getter
@Setter
@Table(name = "job")
@Entity
@NoArgsConstructor
public class Job extends BaseEntity {

    @Column(name = "nick_name")
    private String nickName;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private Set<User> users;

    public Job (Long id){
        this.setId(id);
    }
}
