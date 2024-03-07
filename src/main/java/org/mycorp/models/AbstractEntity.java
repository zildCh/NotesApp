package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    protected AbstractEntity(int id){
        this.id=id;
    }

    protected AbstractEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
