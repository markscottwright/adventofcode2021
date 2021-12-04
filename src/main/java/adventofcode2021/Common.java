package adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * My Junk Drawer.
 */
public class Common {

	public static String[] inputLinesFor(String dayName) {
		var lines = new ArrayList<>();
		try (var resourceAsStream = Common.class.getResourceAsStream("/" + dayName + ".dat")) {
			var reader = new BufferedReader(new InputStreamReader(resourceAsStream));

			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isBlank())
					lines.add(line.strip());
			}
			return lines.toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static List<String> listOfInputLinesFor(String dayName) {
		return Arrays.asList(inputLinesFor(dayName));
	}

}
