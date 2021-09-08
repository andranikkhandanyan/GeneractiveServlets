package am.aca.generactive.util.idgenerator;

import java.util.HashMap;
import java.util.Map;

public final class IdGenerator {

    private static final Map<Type, Integer> CURRENT = new HashMap<>();

    static {
        for (Type type : Type.values()) {
            CURRENT.put(type, 0);
        }
    }

    public static int getNext(Type type) {
        int rv = CURRENT.get(type);
        CURRENT.put(type, ++rv);

        return rv;
    }

    private IdGenerator() {

    }
}
