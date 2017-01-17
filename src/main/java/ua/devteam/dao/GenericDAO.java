package ua.devteam.dao;

/**
 * Generic interface with 2 of CRUD operations - update and delete.
 *
 * @param <T>
 */
public interface GenericDAO<T> {

    /**
     * Updates old entity params by new ones.
     *
     * @param oldEntity to be updated
     * @param newEntity that updates
     */
    void update(T oldEntity, T newEntity);

    /**
     * Deletes specified entity from repository.
     *
     * @param entity to be deleted
     */
    void delete(T entity);

}
