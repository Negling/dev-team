package ua.devteam.dao;

public interface GenericDAO<T> {

    void update(T oldEntity, T newEntity);

    void delete(T entity);

}
