package com.shinhan.solsolhigh.common.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtils {
    private ListUtils(){};
    public static <T, U> List<U> applyFunctionToElements(List<T> list, Function<T, U> func) {
        return list.stream().map(func).collect(Collectors.toList());
    }
    public static <T> List<T> removeIf(List<T> list, Predicate<T> condition) {
        return list.stream().filter(condition.negate()).collect(Collectors.toList()); // 새로운 리스트로 수집
    }
}