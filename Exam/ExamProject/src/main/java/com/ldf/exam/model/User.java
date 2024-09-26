package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//javax.persistence
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class")
@DiscriminatorValue("Users")

//Lombok
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class User extends IdentityIntId implements UserDetails {

    /*implements Serializable{
	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;*/

    @Column
    @NotNull
    @Size(min = 2, max = 50) 
    private String firstname;

    @Column
    @Size(max = 50)
    private String surname1;

    @Column
    @Size(max = 50)
    private String surname2;

    @Column
    @NotNull
    @Size(min = 9, max = 9)
    private String dni;

    @Column(unique=true)
    @NotNull
    @Size(min = 6, max = 20)
    private String username;
    
    @Column
    private byte[] photo;

    @Column
    @NotNull
    @Size(min = 2, max = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //jwt. prevent the password text to be included in json rsponse
    private String password; 
    
    @JsonInclude()
    @Transient
    private String type;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("None"));
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Override
    public boolean isEnabled() {
        return true;
    }
 
}
