package io.github.lgatodu47.speculative.util;

import java.util.Arrays;

public interface MixinAccessibleObject {
    String[] getAccessedMethodsNames();

    default boolean hasAccessedMethod(String methodName) {
        return Arrays.asList(getAccessedMethodsNames()).contains(methodName);
    }
}
