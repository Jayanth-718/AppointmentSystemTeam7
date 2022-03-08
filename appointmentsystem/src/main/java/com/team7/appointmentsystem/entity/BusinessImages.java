package com.team7.appointmentsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class BusinessImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "businessid")
    private Business business;

    @Column(name = "imagepath")
    private String imagePath;

    @Column(name = "isblocked", columnDefinition = "boolean default false")
    private boolean isBlocked;

    public BusinessImages() {
    }

    public BusinessImages(Business business, String imagePath) {
        this.business = business;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "BusinessImages{" +
                "id=" + id +
                ", business=" + business +
                ", imagePath='" + imagePath +
                ", isBlocked='" + isBlocked + '\'' +
                '}';
    }
}
