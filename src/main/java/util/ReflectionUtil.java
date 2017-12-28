package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/*反射类*/
public class ReflectionUtil {
    /*反射类的泛型参数*/
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenriceType(final Class clazz, final int index) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType ptype = (ParameterizedType)type;
        Type[] params = ptype.getActualTypeArguments();
        if (index >=params.length || index < 0) {
            return null;
        }
        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class<T>) params[index];
    }
}
