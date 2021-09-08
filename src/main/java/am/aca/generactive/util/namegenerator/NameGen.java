package am.aca.generactive.util.namegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class NameGen {
    public static final String DEFAULT_SEPARATOR = "_";
    public static final Casing DEFAULT_CASING = Casing.LOWERCASE;

    /**
     * List of {@link Supplier<String>}
     * Each execution of a supplier returns a random value from the given list.
     * So any time {@code supplier.get()} gets invoked new random value should be returned.
     */
    private static final Supplier<String> ADJECTIVES = () -> randomFromList(Arrays.asList(Adjectives.LIST));
    private static final Supplier<String> NOUNS = () -> randomFromList(Arrays.asList(Nouns.LIST));

    /**
     * List of {@link Supplier<String>} to be applied at the end.
     */
    private final List<Supplier<String>> parts = new ArrayList<>();
    private String separator = DEFAULT_SEPARATOR;
    private Casing casing = DEFAULT_CASING;

    private static final Random RND = new Random();

    public static NameGen start() {
        return new NameGen();
    }

    private NameGen() {

    }

    /**
     * Sets a separator between the values.
     */
    public NameGen withSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * Sets the {@link Casing} for the values.
     */
    public NameGen withCasing(Casing casing) {
        this.casing = casing;
        return this;
    }

    public NameGen value(String s) {
        // Define new Supplier inline, which implementation just returns(applies) the given String
        parts.add(() -> s);
        return this;
    }

    public NameGen randomValue(List<String> s) {
        // Define new Supplier inline,
        // which implementation returns(applies) randomly selected value from the List
        parts.add(() -> randomFromList(s));
        return this;
    }

    public NameGen space(String s) {
        // Define new Supplier inline, which implementation just returns(applies) space
        parts.add(() -> " ");
        return this;
    }

    public NameGen noun() {
        // Add predefined Supplier
        parts.add(NOUNS);
        return this;
    }

    public NameGen adjective() {
        // Add predefined Supplier
        parts.add(ADJECTIVES);
        return this;
    }

    public String get() {
        return parts.stream()
                .map(Supplier::get) // execute get for each Supplier (method reference)
                .map(casing::apply) // execute apply of Function (method reference)
                .collect(Collectors.joining(separator));
    }

    private static String randomFromList(List<String> values) {
        return values.get(RND.nextInt(values.size()));
    }
}
