package org.mycorp.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "users")
public class User extends AbstractEntity{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "nickname")
    private String nickname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Convert(converter = ConverterPassword.class)
    @Column(name = "password_hash", columnDefinition = "bytea")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<UserCategoryLink> userCategoryLinkList;

    public User() {
    }

    public User(int id, String nickname, String password) {
        super(id);
        this.nickname = nickname;
        this.password = password;
        this.userCategoryLinkList= new ArrayList<UserCategoryLink>();
    }

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.userCategoryLinkList= new ArrayList<UserCategoryLink>();
    }

    public void addLink(UserCategoryLink link) {
        link.setUser(this);
        userCategoryLinkList.add(link);
    }
    public void removeLink(UserCategoryLink link) {
        userCategoryLinkList.remove(link);
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public List<UserCategoryLink> getUserCategoryLinkList() {
        return userCategoryLinkList;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserCategoryLinkList(List<UserCategoryLink> userCategoryLinkList) {
        this.userCategoryLinkList = userCategoryLinkList;
    }
}
