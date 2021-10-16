package syn;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
       return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User f = users.get(fromId);
        User t = users.get(toId);
        if (f != null && t != null && f.getAmount() >= 0) {
                f.setAmount(f.getAmount() - amount);
                t.setAmount(t.getAmount() + amount);
                users.replace(fromId, f);
                users.replace(toId, t);
                return true;
        }
        return false;
    }

}
