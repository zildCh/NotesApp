package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table (name = "notes")
public class NoteDao extends AbstractEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) //
    @JoinColumn(name = "id_category")
    private CategoryDao category;
    @Column(name = "note")
    private String note;
    @Column(name = "date")
    private String date;
    @Column(name = "header")
    private String header;
    public NoteDao(){}
    public NoteDao(int id, CategoryDao category, String note, String date, String header) {
        super(id);
        this.category = category;
        this.note = note;
        this.date = date;
        this.header = header;
    }

    public CategoryDao getCategory() {
        return category;
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

    public void setCategory(CategoryDao category) {
        this.category = category;
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
