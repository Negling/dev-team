package ua.devteam.dao;

public interface Identified<T> {

    long create(T entity);

    T getById(Long id);
}
