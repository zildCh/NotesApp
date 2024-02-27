package org.mycorp.models_dao;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
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
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "password_hash", columnDefinition = "bytea")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CategoryDao> categoryDaoList;

    public UserDao() {
    }

    public UserDao(int id, String nickname, byte[] password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.categoryDaoList = new ArrayList<CategoryDao>();
    }
    public UserDao(String nickname, byte[] password) {
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

    public byte[] getPassword() {
        return password;
    }

    public List<CategoryDao> getCategoryDaoList() {
        return categoryDaoList;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setCategoryDaoList(List<CategoryDao> categoryDaoList) {
        this.categoryDaoList = categoryDaoList;
    }
}
