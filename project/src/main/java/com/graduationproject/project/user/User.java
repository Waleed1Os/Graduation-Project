package com.graduationproject.project.user;

import com.graduationproject.project.customersupport.SupportSession;
import com.graduationproject.project.feedback.Feedback;
import com.graduationproject.project.project.Project;
import com.graduationproject.project.subscription.Subscription;
import com.graduationproject.project.token.Token;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName,lastName;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(unique = true,nullable = false,updatable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user",targetEntity = Project.class)
    private List<Project> projects; 
    @Enumerated(EnumType.STRING)
    private Role role;
    private Date whenCreated;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "admin",targetEntity = BannedUser.class)
    private List<BannedUser> bannedUsers;
    //TODO: Decide on whether or not making this a Date or boolean
    private Date premium;
    @OneToMany(mappedBy = "user",targetEntity = Subscription.class)
    private List<Subscription> subscriptions;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<SupportSession> supportSessions;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",targetEntity = Feedback.class)
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "user",targetEntity = Token.class)
    private List<Token> tokens;
    @Default
    private boolean accountNonLocked=true;//Whether or not a user is banned if it is false then the user is banned
    public boolean clearHistory(){
        if(!this.projects.isEmpty()){
        this.projects.clear();
        return true;}
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return this.role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.username;
    } 
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       return true;
    }

   
    
}