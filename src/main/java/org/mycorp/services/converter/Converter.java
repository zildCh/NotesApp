package org.mycorp.services.converter;

public abstract class Converter<C,T> {
    public abstract C convertToDto(T daoObj);
    public abstract T convertToDao(C dtoObj);
    public abstract T updateDao(T daoObj, C dtoObj);
}
