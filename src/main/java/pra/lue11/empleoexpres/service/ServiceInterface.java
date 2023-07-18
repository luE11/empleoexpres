package pra.lue11.empleoexpres.service;

import java.util.List;

/**
 * @author luE11 on 18/07/23
 */
public interface ServiceInterface <I, T> {

    public T insert(T entity);
    public T findById(I id);
    public List<T> getAll();
    public T update(I id, T entity);
    public boolean delete(I id);

}
