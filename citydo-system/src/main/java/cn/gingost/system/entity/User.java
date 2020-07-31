package cn.gingost.system.entity;

import cn.gingost.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
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
@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_delete=0")
public class User extends BaseEntity implements Serializable {

    @Column(name = "nick_name")
    @NotBlank(message = "用户名不能为空",groups = {Create.class, Update.class})
    private String nickName;

    @NotBlank
    @Column(name = "sex")
    private String sex;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "phone")
    private String phone;

    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "card")
    private String card;

    @NotNull
    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    @JsonIgnore
    private Dept dept;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", card='" + card + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
