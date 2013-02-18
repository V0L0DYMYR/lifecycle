package org.lifecycle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static <T> Set<T> returnNotNull(Set<T> set) {
        return set == null? Collections.<T>emptySet():set;
    }

    public static <T> Set<T> initializeIfNull(Set<T> set) {
        return set == null ? new HashSet<T>(): set;
    }
}
