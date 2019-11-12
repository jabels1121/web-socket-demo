package com.jaybe.websocketdemo.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"blocks"})
@ToString(exclude = {"blocks"})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
                    joinColumns = @JoinColumn(name = "USER_ID"),
                    inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> authorities;

    @OneToMany(mappedBy = "appUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Block> blocks;

    public AppUser addRole(Role role) {
        if (null == this.authorities) {
            authorities = new HashSet<>();
        }
        authorities.add(role);
        return this;
    }
    public AppUser addBlock(Block block) {
        if (null == this.blocks) {
            blocks = new HashSet<>();
        }
        this.blocks.add(block);
        return this;
    }
}
