package org.mycorp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "users")
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "nickname")
    private String nickname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Convert(converter = ConverterPassword.class)
    @Column(name = "password_hash", columnDefinition = "bytea")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CategoryDao> categoryDaoList;

    public UserDao() {
    }

    public UserDao(int id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.categoryDaoList = new ArrayList<CategoryDao>();
    }
    public UserDao(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.categoryDaoList = new ArrayList<CategoryDao>();
    }

    public void addCategory(CategoryDao category) {
        category.setUserDao(this);
        categoryDaoList.add(category);
    }
    public void removeCategory(CategoryDao category) {
        categoryDaoList.remove(category);
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public List<CategoryDao> getCategoryDaoList() {
        return categoryDaoList;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCategoryDaoList(List<CategoryDao> categoryDaoList) {
        this.categoryDaoList = categoryDaoList;
    }
}
