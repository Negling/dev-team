package ua.devteam.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class AbstractBuilder<T> {
    private @Getter(AccessLevel.PROTECTED) final T construction;

    public T build(){
        return construction;
    }

    protected abstract AbstractBuilder<T> perform(Operation operation);
}
