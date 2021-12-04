package adventofcode2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * My Junk Drawer.
 */
public class Common {

	public static String[] inputAsArrayFor(String dayName) {
		try (var resourceAsStream = Common.class.getResourceAsStream("/" + dayName + ".dat")) {
			return new String(resourceAsStream.readAllBytes()).split("\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> inputAsListFor(String dayName) {
		return Arrays.asList(inputAsArrayFor(dayName));
	}

}
