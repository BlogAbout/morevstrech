package ru.morevstrech.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone")
})
public class UserInfo {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "about", length = 500)
    private String about;

    @Column(name = "interest", length = 500)
    private String interest;

    @Column(name = "slogan", length = 500)
    private String slogan;

    @Column(name = "address")
    private String address;

    @Column(name = "sex", length = 20)
    private String sex;

    @Column(name = "hair", length = 20)
    private String hair;

    @Column(name = "body", length = 20)
    private String body;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "balance")
    private double balance;

    @Column(name = "generosity")
    private int generosity;

    @Column(name = "popularity")
    private int popularity;

    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDate;

    @Transient
    private Long age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getGenerosity() {
        return generosity;
    }

    public void setGenerosity(int generosity) {
        this.generosity = generosity;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Long getAge() {
        if (getBirthDate() == null)
            return -1L;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime birth = getBirthDate();
        Duration duration = Duration.between(birth, now);

        return duration.toDays();
    }
}