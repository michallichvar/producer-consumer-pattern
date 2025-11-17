package sk.lichvar.pcp.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceLoader {

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