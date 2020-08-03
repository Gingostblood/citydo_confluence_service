package cn.gingost.system.entity;

import cn.gingost.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author:lezzy
 * @Date:2020/7/24 17:48
 */

@Getter
@Setter
@Entity
@Table(name = "dept")
@Where(clause = "is_delete=0")
@AllArgsConstructor
@NoArgsConstructor
public class Dept extends BaseEntity implements Serializable {

    @Column(name = "nick_name")
    @NotBlank(message = "部门名不能为空", groups = {Create.class, Update.class})
    private String nickName;

    @Column(name = "p_id")
    @NotNull
    private Long pid;

    @OneToMany(mappedBy = "dept", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users;

    @Column(name = "u_id")
    @NotNull(message = "部门主管id不能为空", groups = {Create.class, Update.class})
    private Long uid;

    @OneToMany(mappedBy = "dept")
    @JsonIgnore
    private Set<Job> jobs;

    @Override
    public String toString() {
        return "Dept{" +
                "nickName='" + nickName + '\'' +
                ", pid=" + pid +
                '}';
    }

    public Dept(Long id){
        this.setId(id);
    }

}
