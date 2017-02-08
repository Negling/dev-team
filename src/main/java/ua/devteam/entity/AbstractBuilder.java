package ua.devteam.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base class for inheritance. Target - build and configure entities in 'entity' package.
 *
 * @param <T> entity, that builder is producing.
 */
@AllArgsConstructor
public abstract class AbstractBuilder<T> {
    private @Getter(AccessLevel.PROTECTED) final T construction;

    /**
     * Returns configured entity.
     *
     * @return entity
     */
    public T build() {
        return construction;
    }

    /**
     * Performs operation and returns itself.
     *
     * @param operation - mostly entity setters with lambda conversion
     * @return builder itself, for construction chaining
     */
    protected AbstractBuilder<T> perform(Operation operation) {
        operation.perform();
        return this;
    }
}
