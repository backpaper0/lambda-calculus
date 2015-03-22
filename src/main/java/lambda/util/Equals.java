package lambda.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Equals<T> {

    private final Class<T> clazz;

    private final List<Function<T, Object>> getters = new ArrayList<>();

    private Equals(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> Equals<T> of(Class<T> clazz) {
        return new Equals<>(clazz);
    }

    public Equals<T> getter(Function<T, Object> getter) {
        getters.add(getter);
        return this;
    }

    public boolean equals(T self, Object obj) {
        if (self == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (self.getClass() != obj.getClass()) {
            return false;
        }
        T other = clazz.cast(obj);
        return getters.stream().allMatch(f -> Objects.equals(f.apply(self),
                                                             f.apply(other)));
    }
}
