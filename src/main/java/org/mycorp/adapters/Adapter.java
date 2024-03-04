package org.mycorp.adapters;

import org.mycorp.models.AbstractEntity;
import org.mycorp.models.CategoryDao;

public interface Adapter<T extends AbstractEntity> {

    boolean createEntity(int idParentEntity, T Entity);
    boolean updateEntity(int id, T Entity);
    boolean deleteEntity(int id);

}