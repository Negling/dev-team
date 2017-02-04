package ua.devteam.entity;


public abstract class AbstractBuilder<T> {
    protected final T instance;

    protected AbstractBuilder(T instance) {
        this.instance = instance;
    }

    public T build(){
        return  instance;
    }
}
