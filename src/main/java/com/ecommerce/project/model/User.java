package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
    })
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @NotBlank
    @Size(max=20)
    @Column(name="username")
    private String userName;

    @NotBlank
    @Size(max=120)
    @Column(name="password")
    private String password;

    @NotBlank
    @Size(max=50)
    @Email
    @Column(name="email")
    private String email;

    public User(String userName,  String email,String password) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Setter
    @Getter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE} // All the changes or order user do should reflect everywhere
            ,fetch = FetchType.EAGER) // Eager for when customer accessing the web its role should also be loaded
    @JoinTable(name="user_role",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();// standard accessor that will allow to set roles

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
//    @JoinTable(name="user_address",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name="address_id"))
    private List<Address>addresses=new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    orphanRemoval = true) // orphanRemoval -> It will remove the orphan items which donot have owner
    private Set<Product>products;

    @ToString.Exclude
    @OneToOne(mappedBy="user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private Cart cart;


}
