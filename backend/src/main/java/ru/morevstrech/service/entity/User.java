package ru.morevstrech.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.morevstrech.service.dto.ERole;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "account", length = 20)
    private String account;

    @ManyToOne
    @JoinColumn(name = "parent_user_id")
    private User parentUser;

    @Column(name = "registration")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registration;

    @Column(name = "last_visit")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;

    @ElementCollection(targetClass = ERole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInfo userInfo;

    @Column(name = "validation_code")
    private int validationCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>??????????????????, ???????????????? ???? ?????????????? ???????????? ????????????????????????.</p>
     * <p>???? ???????????????? ?????????????? ???????????? ???? ?????????? ???????? ??????????????????????????????????.</p>
     * @return true, ???????? ?????????????? ???????????? ??????????????, false - ???????? ???? ??????????????.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public LocalDateTime getRegistration() {
        return registration;
    }

    /**
     * <p>?????????????????????????? ???????? ?????????????????????? ????????????????????????.</p>
     * <p>???????? ?????????????????????? ???? ???????????????????? ???? ???????????????????? ?????????? ?????????????????????????? ????????????????????????.</p>
     * @param registration ???????? ??????????????????????
     */
    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    /**
     * <p>?????????????????????????? ???????? ???????????????????? ???????????? ????????????????????????.</p>
     * @param lastVisit ???????? ????????????????????
     */
    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }

    public int getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    /**
     * <p>??????????????????, ?????????? ???? ???????? ???????????????? ?????????????? ???????????? ????????????????????????.</p>
     * <p>?????????????? ???????????? ?? ???????????????? ???????????? ???????????????? ???? ?????????? ???????? ??????????????????????????????????.</p>
     * @return true, ???????? ?????????????? ???????????? ??????????????????????????, false - ???????? ???????? ???????????????? ??????????.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * <p>??????????????????, ?????????????????????????? ???? ?????????????? ???????????? ????????????????????????.</p>
     * <p>?????????????????????????????? ?????????????? ???????????? ???? ?????????? ???????? ??????????????????????????????????.</p>
     * @return true, ???????? ?????????????? ???????????? ???? ??????????????????????????, false - ???????? ??????????????????????????.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * <p>??????????????????, ?????????? ???? ???????? ???????????????? ?????????????? ???????????? ????????????????????????.</p>
     * <p>?????????????? ???????????? ?? ???????????????? ???????????? ???????????????? ?????????????? ???????????? ???? ?????????? ???????? ??????????????????????????????????.</p>
     * @return true, ???????? ?????????????? ???????????? ??????????????????????????, false - ???????? ???????? ???????????????? ??????????.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        User other = (User) obj;

        if (id == null)
            return other.id == null;
        else
            return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", userInfo=" + userInfo +
                '}';
    }
}