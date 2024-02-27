package org.mycorp.models_buisnes;

public class CategoryDto{
    private int id;
    private UserDto userDto;
    private String category;

    public CategoryDto() {}

    public CategoryDto(int id, UserDto userDto, String category) {
        this.id = id;
        this.userDto = userDto;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public String getCategory() {
        return category;
    }


}