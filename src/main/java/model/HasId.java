package model;

import java.util.Objects;
import java.util.Optional;

public interface HasId<T> {

    T getId();

    static <C, E extends Enum & HasId<C>> Optional<E> findById(
            final Class<E> enumClass, final C id
    ) {
        if (id != null) {
            final E[] values = enumClass.getEnumConstants();
            for (final E item : values) {
                if (Objects.equals(item.getId(), id)) {
                    return Optional.of(item);
                }
            }
        }
        return Optional.empty();
    }

    static <C, E extends Enum & HasId<C>> E getById(final Class<E> enumClass, final C id) {
        return findById(enumClass, id)
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(id)));
    }

}