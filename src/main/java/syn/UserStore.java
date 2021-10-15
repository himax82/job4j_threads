package syn;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
       return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        if (users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int balance = users.get(fromId).getAmount() - amount;
        if (users.containsKey(fromId) && users.containsKey(toId)) {
            if (balance > 0) {
                users.replace(fromId, new User(fromId, balance));
                users.replace(toId, new User(toId, users.get(toId).getAmount() + amount));
                return true;
            }
        }
        return false;
    }

}
