package ua.devteam.dao;

/**
 * Implemented by DAO objects which works with entities that have unique ID identifier.
 *
 * @param <T> entity with ID
 */
public interface Identified<T> {

    /**
     * Records entity to repository and returns unique generated entity ID.
     *
     * @param entity to be recorded
     * @return long as generated ID value
     */
    long create(T entity);

    /**
     * Returns entity, queried by it's own unique ID.
     *
     * @param id query param
     * @return entity
     * @throws org.springframework.dao.EmptyResultDataAccessException - if there no entity with such ID
     */
    T getById(Long id);
}
