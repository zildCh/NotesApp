package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "category")
public class CategoryDao extends AbstractEntity{

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) //
    @JoinColumn(name = "id_user")
    private UserDao user;
    @Column(name = "category")
    private String category;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<NoteDao> noteDaoList;

    public CategoryDao() {
    }

    public CategoryDao(int id, UserDao user, String category) {
        super(id);
        this.user = user;
        this.category = category;
        this.noteDaoList = new ArrayList<NoteDao>();
    }

    public void addNote(NoteDao note) {
        note.setCategory(this);
        noteDaoList.add(note);
    }
    public void removeCategory(NoteDao note) {
        noteDaoList.remove(note);
    }

    public UserDao getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    public List<NoteDao> getNoteDaoList() {
        return noteDaoList;
    }

    public void setUserDao(UserDao user) {
        this.user = user;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNoteDaoList(List<NoteDao> noteDaoList) {
        this.noteDaoList = noteDaoList;
    }

    @Override
    public String toString() {
        return "CategoryDao{" +
                "id=" + getId() +
                ", category='" + category + '\'' +
                '}';
    }
}