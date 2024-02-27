package org.mycorp.models_dao;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "category")
public class CategoryDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
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
        this.id = id;
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

    public int getId() {
        return id;
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
                "id=" + id +
                ", user=" + user +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDao)) return false;
        CategoryDao that = (CategoryDao) o;
        return getId() == that.getId() && Objects.equals(getUser(), that.getUser()) && Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getNoteDaoList(), that.getNoteDaoList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getCategory(), getNoteDaoList());
    }
}