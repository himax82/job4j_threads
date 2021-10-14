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
    private final Map<Integer, Integer> users = new HashMap<>();

    public synchronized boolean add(User user) {
       users.put(user.getId(), user.getAmount());
       return true;
    }

    public synchronized boolean update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user.getAmount());
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user.getAmount());
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int balance = users.get(fromId) - amount;
        if (users.containsKey(fromId) && users.containsKey(toId)) {
            if (balance > 0) {
                users.put(fromId, balance);
                users.put(toId, users.get(toId) + amount);
                return true;
            }
        }
        return false;
    }

}
