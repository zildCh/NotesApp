package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user_category_link", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "category_id" })
})
public class UserCategoryLink extends AbstractEntity{
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Note> noteList;

    public UserCategoryLink() {
    }

    public UserCategoryLink(int id, User user, Category category) {
        super(id);
        this.user = user;
        this.category = category;
        this.noteList = new ArrayList<Note>();
    }

    public UserCategoryLink(User user, Category category) {
        this.user = user;
        this.category = category;
        this.noteList = new ArrayList<Note>();
    }

    public void addNote(Note note) {
        note.setLink(this);
        noteList.add(note);
    }
    public void removeNote(Note note) {
        noteList.remove(note);
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
