package engine.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import engine.util.string.StringTools;

public class Palette {

	private static final String PALETTE_PATHNAME = "res/palette.dat";

	private static Map<String, Palette> s_Palettes = new HashMap<>();

	public static void loadPalettes() {

		try {
			Scanner scanner = new Scanner(new File(PALETTE_PATHNAME));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				while (line.equals(""))
					line = scanner.nextLine();
				Palette current = new Palette(line);
				line = scanner.nextLine();
				while (scanner.hasNextLine() && !line.equals("END")) {
					String[] split = line.split(":");
					current.addColor(split[0], Color.parse(split[1]));
					line = scanner.nextLine();
				}
			}
			scanner.close();
		} catch (Exception e) {

		}

	}

	public static Palette get(String name) {
		return s_Palettes.get(name);
	}

	public static Set<String> names() {
		return s_Palettes.keySet();
	}

	private String m_Name;
	private Map<String, Color> m_Colors;

	public Palette(String name) {
		s_Palettes.put(name, this);
		m_Name = name;
		m_Colors = new TreeMap<>();
	}

	public Map<String, Color> colors() {
		return m_Colors;
	}

	public Color color(String key) {
		return m_Colors.get(key);
	}

	public void addColor(String name, Color color) {
		m_Colors.put(name, color);
	}

	public String name() {
		return m_Name;
	}

	public String string(int indentAmt) {
		return StringTools.buildString(StringTools.indent(indentAmt), name(), " {",
				StringTools.indentl(indentAmt + 1), "colors: {\n",
				StringTools.buildString(indentAmt + 2, m_Colors),
				StringTools.indentl(indentAmt + 1), "}", StringTools.indentl(indentAmt), "}");
	}

	@Override
	public String toString() {
		return string(0);
	}

}
