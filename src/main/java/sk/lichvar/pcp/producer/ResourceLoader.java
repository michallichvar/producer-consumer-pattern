package sk.lichvar.pcp.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for loading and processing resources.
 */
public class ResourceLoader {

	/**
	 * Retrieves a list of lines from a resource located in the classpath.
	 *
	 * This method loads the specified resource as an InputStream, reads it line-by-line,
	 * and returns the lines as a List of Strings. If the resource is not found or cannot
	 * be loaded, a RuntimeException is thrown.
	 *
	 * @param resourceName the name of the resource to load (e.g., "/input.cmd")
	 * @return a list of strings representing the lines in the resource
	 */
	public static List<String> getLinesFromResource(String resourceName) {
		try (InputStream inputStream = ResourceLoader.class.getResourceAsStream(resourceName)) {
			if (inputStream == null) {
				throw new RuntimeException("Resource not found: " + resourceName);
			}

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				return reader.lines().collect(Collectors.toList());
			} catch (IOException e) {
				throw new RuntimeException("Error reading resource: " + resourceName, e);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error loading resource: " + resourceName, e);
		}
	}

}