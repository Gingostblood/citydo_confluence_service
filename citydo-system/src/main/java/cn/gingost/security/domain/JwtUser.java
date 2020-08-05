package cn.gingost.security.domain;

import cn.gingost.security.domain.dto.SmallDeptDto;
import cn.gingost.security.domain.dto.SmallJobDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author:lezzy
 * @Date:2020/7/27 17:34
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements UserDetails {
    @JsonIgnore
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String sex;
    private String type;
    private String phone;
    private String email;
    private String card;
    private SmallDeptDto dept;
    //private SmallJobDto job;
   // private Set<String> role;
    @JsonIgnore
    private Collection<GrantedAuthority> authorities;


    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public Collection getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
