package com.example.springnativejdbctemplate.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
//@Table(name = "user")
public class User {
    @ManyToOne
    @JsonIgnore
    public Realm realm;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;

    private String name_last;

    private String name_first;

    private String email;

    private String phone;

    private String region;
    @Enumerated(EnumType.STRING)
    private UserRole user_role;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    // Allows dd/MM/yyyy date to be passed into GET request in JSON
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;


    @JsonIgnore
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonFormat(pattern = "dd/MM/yyyy")
    // Allows dd/MM/yyyy date to be passed into GET request in JSON
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;

    public User(){}

    public User( Long UID, String name_first, String name_last, String phone, String email, String region,String realmID) {
        this.UID = UID;
        this.name_last = name_last;
        this.name_first = name_first;
        this.email = email;
        this.phone = phone;
        this.region = region;
    }

    //uid,user_role,name_first,name_last,email,phone,region,realm_id,created_at
    //users.add(new User(Long.parseLong(data[0]), data[1], data[2], data[3], data[4], data[5], data[6]));


    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public UserRole getUserRole() {
        return user_role;
    }

    public void setUserRole(UserRole user_role) {
        this.user_role = user_role;
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long userID) {
        this.UID = userID;
    }

    public String getName_last() {
        return name_last;
    }

    public void setName_last(String name_last) {
        this.name_last = name_last;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName_first() {
        return name_first;
    }

    public void setName_first(String name_first) {
        this.name_first = name_first;
    }

    public String getFirstName() {
        return name_first;
    }

    public void setFirstName(String name) {
        this.name_first = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailId) {
        this.email = emailId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
