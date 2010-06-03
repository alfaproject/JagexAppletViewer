package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;

public final class Preferences {

	private static Hashtable<String, String> _preferences = new Hashtable<String, String>();
	private static File _preferencesFile = new File("jagexappletviewer.preferences");

	public static boolean dummy;

	public static final void Load() {
		_preferences.clear();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(_preferencesFile));
			String prefLine;
			while ((prefLine = reader.readLine()) != null) {
				int i = prefLine.indexOf("=");
				if (i != -1) {
					String prefName = prefLine.substring(0, i);
					String prefValue = prefLine.substring(i + 1);
					_preferences.put(prefName, prefValue);
				}
			}
		} catch (IOException ex) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	public static final void Set(String name, String value) {
		_preferences.put(name, value);
	}

	public static final String Get(String name) {
		if (!_preferences.containsKey(name)) {
			Load();
		}
		return _preferences.get(name);
	}

	public static final void Save() {
		PrintStream writer = null;
		try {
			writer = new PrintStream(new FileOutputStream(_preferencesFile));
			for (String pref : _preferences.keySet()) {
				String prefName = pref;
				String prefValue = _preferences.get(pref);
				writer.println(prefName + "=" + prefValue);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

} // class Preferences