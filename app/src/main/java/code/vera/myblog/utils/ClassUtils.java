package code.vera.myblog.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by vera on 2017/1/4 0004.
 */

public class ClassUtils {
    private ClassUtils() {
        throw new RuntimeException();
    }

    /**
     * 获取class类泛型参数的class 类型
     *
     * @param clz    要获取的 class 类
     * @param _index 参数的下标
     * @return
     */
    public static final Class getParameterizedClass(Class<?> clz, int _index) {
        Type superClass = clz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) superClass;
            Type[] types = type.getActualTypeArguments();
            return (Class) types[_index];
        }

        throw new IllegalStateException("Unable to detect the actual type of SlimTable.");
    }
}
