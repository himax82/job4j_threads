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
        AtomicInteger ver = new AtomicInteger(model.getVersion());
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != ver.get()) {
                throw new OptimisticException("Versions are not equal");
            }
            v = new Base(model.getId(), ver.getAndIncrement());
            v.setName(model.getName());
           return v;
       }) != null;
    }
    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
