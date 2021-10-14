package cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            if (memory.get(model.getId()).getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            int ver = model.getVersion();
            v = new Base(model.getId(), ++ver);
            v.setName(model.getName());
           return v;
       }) != null;
    }
    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
