package am.aca.generactive.util.namegenerator;

import java.util.function.Function;

public class Casing {
	public static Casing UPPERCASE = new Casing(String::toUpperCase);
	public static Casing CAPITALIZE = new Casing(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
	public static Casing LOWERCASE = new Casing(String::toLowerCase);

	private final Function<String, String> casingFunction;

	private Casing(Function<String, String> casingFunction) {
		this.casingFunction = casingFunction;
	}

	public String apply(String value) {
		return casingFunction.apply(value);
	}
}