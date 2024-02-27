package org.mycorp.models_buisnes;

public class NoteDto {
    private int id;
    private CategoryDto categoryDto;
    private String note;
    private String date;
    private String header;

    public NoteDto() {}

    public NoteDto(int id, CategoryDto categoryDto, String note, String date, String header) {
        this.id = id;
        this.categoryDto = categoryDto;
        this.note = note;
        this.date = date;
        this.header = header;
    }

    public int getId() {
        return id;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
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
}
