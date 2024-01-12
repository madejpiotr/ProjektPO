import java.util.ArrayList;

public interface CRUD<T>{
    public boolean Create(T item);
    public ArrayList<T> Read();
    public void Update(T item);
    public void Delete(T item);
}
