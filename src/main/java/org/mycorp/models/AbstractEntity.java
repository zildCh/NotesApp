package org.mycorp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
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
