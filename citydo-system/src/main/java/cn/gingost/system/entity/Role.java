package cn.gingost.system.entity;

import cn.gingost.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * @author:lezzy
 * @Date:2020/7/27 13:51
 */

@Entity
@Getter
@Setter
@Table(name = "role")
@Where(clause = "is_delete=0")
@NoArgsConstructor
public class Role extends BaseEntity {

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "role_menu",joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")})
    private Set<Menu> menus;

    public Role (Long id){
        this.setId(id);
    }
}
