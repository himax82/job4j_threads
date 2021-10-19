package cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public Map<Integer, Base> getMemory() {
        return memory;
    }

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Версия заменяемой модели и замещающей не совпадают!!!");
            }
            v = new Base(model.getId(), model.getVersion() + 1);
            v.setName(model.getName());
           return v;
       }) != null;
    }
    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
