package org.techforumist.jwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numb;

    private String street;
    private String reserve;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AppUser appUser;


    public UserProfile() {
        this.street = "empty";
        this.reserve = "empty";
    }


    public Long getNumb() {
        return numb;
    }

    public void setNumb(Long numb) {
        this.numb = numb;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    @JsonIgnore
    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }


    @Override
    public String toString() {
        return "UserProfile{" +
                "street='" + street + '\'' +
                ", reserve='" + reserve + '\'' +
                '}';
    }
}