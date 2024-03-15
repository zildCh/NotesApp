package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "category")
public class Category extends AbstractEntity{

    @Column(name = "category")
    private String category;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<UserCategoryLink> userCategoryLinkList;

    public Category() {
    }

    public Category(int id, String category) {
        super(id);
        this.category = category;
        this.userCategoryLinkList = new ArrayList<UserCategoryLink>();
    }

    public Category(String category) {
        this.category = category;
        this.userCategoryLinkList = new ArrayList<UserCategoryLink>();
    }

    public void addLink(UserCategoryLink link) {
        link.setCategory(this);
        userCategoryLinkList.add(link);
    }
    public void removeLink(UserCategoryLink link) {
        userCategoryLinkList.remove(link);
    }


    public String getCategory() {
        return category;
    }

    public List<UserCategoryLink> getUserCategoryLinkList() {
        return userCategoryLinkList;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUserCategoryLinkList(List<UserCategoryLink> userCategoryLinkList) {
        this.userCategoryLinkList = userCategoryLinkList;
    }

    @Override
    public String toString() {
        return "CategoryDao{" +
                "id=" + getId() +
                ", category='" + category + '\'' +
                '}';
    }
}