package adventofcode2021;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

    public static <T> List<T> parseInputFor(String dayName, Function<String, T> parser) {
        return inputAsListFor(dayName).stream().map(parser).toList();
    }

    public static String inputAsStringFor(String dayName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Common.class.getResourceAsStream("/" + dayName + ".dat").transferTo(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(out.toByteArray());
    }
}
