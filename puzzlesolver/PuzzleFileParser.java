package puzzlesolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper class to parse puzzle input files.
 */
public class PuzzleFileParser {

	public static class PieceStruct {
		public String id;
		public char character;
		public String n;
		public String e;
		public String s;
		public String w;
	}

	private static Charset charset = StandardCharsets.UTF_8;

	static List<PieceStruct> parseFile(Path filePath) throws IOException  {
		BufferedReader reader = Files.newBufferedReader(filePath,  charset);
		List<PieceStruct> l = new LinkedList<PieceStruct>();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!line.equals("")) {
					// An empty line - especially at the end - although not really legal can be safely ignored.
					String[] tokens = line.split("\t");
					if (tokens.length != 6) {
						throw new MalformedFileException();
					}
					if (tokens[1].length() > 1) {
						throw new MalformedFileException();
					}
					char c = tokens[1].charAt(0);
					PieceStruct p = new PieceStruct();
					p.id = tokens[0];
					p.character = c;
					p.n = tokens[2];
					p.e = tokens[3];
					p.s = tokens[4];
					p.w = tokens[5];
					l.add(p);
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
