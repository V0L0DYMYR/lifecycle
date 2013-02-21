package org.lifecycle;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static <T> Set<T> returnNotNull(Set<T> set) {
        return set == null? Collections.<T>emptySet():set;
    }

    public static <T> Set<T> initializeIfNull(Set<T> set) {
        return set == null ? new HashSet<T>(): set;
    }

    public static List<Class<?>> asList(Class<?>... classes) {
        return Lists.newArrayList(classes);
    }

    public static <T> Set<T> asSet(T... set) {
        return Sets.newHashSet(set);
    }

    public static boolean isNotEmpty(String s){
        return s != null && s.length() != 0 && s.trim().length() != 0;
    }
}
