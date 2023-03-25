package src;

import java.util.ArrayList;

public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> {

    // when adding element order by Alphabetical
    @Override
    public boolean add(E e) {
        int i = 0;
        while (i < size()) {
            if (get(i).compareTo(e) > 0) {
                break;
            }
            i++;
        }
        add(i, e);
        return true;
    }
}



