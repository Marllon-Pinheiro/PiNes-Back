package br.com.pines.dev.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.pines.dev.repository.RoleRepository;


@Entity
@Table(name = "TB_USER")
public class Users implements UserDetails {

    private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 14)
    private String username;

    @NotEmpty
    @NotNull
    private String password;
    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_USERS_ROLES",
    joinColumns = @JoinColumn(name = "username"),
    inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Role> roles = new ArrayList<>();

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
        
    }

    public Users(){
    }

    public Users(Users users) {
        this.username = users.getUsername();
        this.password = users.getPassword();
        
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public void updatePasswordEncoder(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void roleDefaultAdd(RoleRepository roleRepository){
        this.roles.add(roleRepository.getReferenceById(2L));
    }

    public void updateAdmin(RoleRepository roleRepository) {
        this.roles.removeAll(roles);
        this.roles.add(roleRepository.findById(1L).get());
    }
}
