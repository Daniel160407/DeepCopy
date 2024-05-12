import org.example.CopyUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class CopyUtilsTest {

    @Test
    public void testCopyNull() throws InstantiationException, IllegalAccessException {
        Object obj = null;
        Object copy = CopyUtils.deepCopy(obj);

        assertEquals(obj, copy);
    }

    @Test
    public void testCopyString() throws InstantiationException, IllegalAccessException {
        String str = "Hello";
        String copy = CopyUtils.deepCopy(str);

        assertNotSame(str, copy);
        assertEquals(str, copy);
    }

    @Test
    public void testCopyPrimitiveArray() throws InstantiationException, IllegalAccessException {
        int[] array = {1, 2, 3};
        int[] copy = CopyUtils.deepCopy(array);

        assertNotSame(array, copy);
        assertEquals(Arrays.toString(array), Arrays.toString(copy));
    }

    @Test
    public void testCopyObjectArray() throws InstantiationException, IllegalAccessException {
        Integer[] array = {1, 2, 3};
        Integer[] copy = CopyUtils.deepCopy(array);

        assertNotSame(array, copy);
        assertEquals(Arrays.toString(array), Arrays.toString(copy));
    }

    @Test
    public void testCopyList() throws InstantiationException, IllegalAccessException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        List<Integer> copy = CopyUtils.deepCopy(list);

        assertNotSame(list, copy);
        assertEquals(list, copy);
    }

    @Test
    public void testCopySet() throws InstantiationException, IllegalAccessException {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);

        Set<Integer> copy = CopyUtils.deepCopy(set);

        assertNotSame(set, copy);
        assertEquals(set, copy);
    }

    @Test
    public void testCopyMap() throws InstantiationException, IllegalAccessException {
        Map<String, Integer> map = new HashMap<>();
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);

        Map<String, Integer> copy = CopyUtils.deepCopy(map);

        assertNotSame(map, copy);
        assertEquals(map, copy);
    }

    @Test
    public void testCopyNestedObject() throws InstantiationException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        map.put("list", list);

        Map<String, Object> copy = CopyUtils.deepCopy(map);

        assertNotSame(map, copy);
        assertEquals(map, copy);

        List<Integer> nestedList = (List<Integer>) map.get("list");
        List<Integer> nestedCopy = (List<Integer>) copy.get("list");

        assertNotSame(nestedList, nestedCopy);
        assertEquals(nestedList, nestedCopy);
    }
}
