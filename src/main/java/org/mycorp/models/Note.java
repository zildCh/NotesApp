package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table (name = "notes")
public class Note extends AbstractEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_link")
    private UserCategoryLink link;
    @Column(name = "note")
    private String note;
    @Column(name = "date")
    private String date;
    @Column(name = "header")
    private String header;
    public Note(){}
    public Note(int id, UserCategoryLink link, String note, String date, String header) {
        super(id);
        this.link = link;
        this.note = note;
        this.date = date;
        this.header = header;
    }

    public UserCategoryLink getLink() {
        return link;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }

    public void setLink(UserCategoryLink link) {
        this.link = link;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
