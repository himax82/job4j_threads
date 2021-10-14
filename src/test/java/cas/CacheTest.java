package cas;

import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void putBase() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("User1");
        Base base2 = new Base(2, 1);
        base2.setName("User2");
        cache.add(base1);
        cache.add(base2);
        assertThat(cache.getMemory().get(2).getName(), is("User2"));
    }

    @Test
    public void deleteBase() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("User1");
        Base base2 = new Base(2, 1);
        base1.setName("User2");
        cache.add(base1);
        cache.add(base2);
        cache.delete(base1);
        assertThat(cache.getMemory().size(), is(1));
    }

    @Test
    public void updateTrue() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("User1");
        Base base2 = new Base(2, 2);
        base1.setName("User2");
        cache.add(base1);
        cache.add(base2);
        Base base3 = new Base(2, 2);
        base3.setName("User3");
        cache.update(base3);
        assertThat(cache.getMemory().get(2).getName(), is("User3"));
    }

    @Test(expected = OptimisticException.class)
    public void updateException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("User1");
        Base base2 = new Base(2, 2);
        base1.setName("User2");
        cache.add(base1);
        cache.add(base2);
        Base base3 = new Base(2, 3);
        base3.setName("User3");
        cache.update(base3);
    }

}