package syn;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();

    public synchronized boolean add(User user) {
       return users.add(user);
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        for (User u : users) {
            if (u.getId() == user.getId()) {
                u.setAmount(user.getAmount());
                result = true;
            }
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    public synchronized User findFromId(int id) {
        User user = null;
        for (User r : users) {
            if (r.getId() == id) {
                user = r;
            }
        }
        return user;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = findFromId(fromId);
        User to = findFromId(toId);
        if (from != null && to != null) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
        } else {
            return false;
        }
        return update(from) && update(to);
    }

}
