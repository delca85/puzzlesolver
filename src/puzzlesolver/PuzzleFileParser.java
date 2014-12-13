package puzzlesolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class PuzzleFileParser {
	
	private static Charset charset = StandardCharsets.UTF_8;
	
	static List<String[]> parseFile(Path filePath) throws IOException  {
		BufferedReader reader = Files.newBufferedReader(filePath,  charset);
		List<String[]> l = new LinkedList<String[]>();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.equals("")) {
					// An empty line can be safely ignored.
					String[] tokens = line.split("\t");
					if (tokens.length != 6) {
						throw new MalformedFileException();
					}
					l.add(tokens);
				}
			}
		} catch (MalformedFileException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		return l;
	}
}
