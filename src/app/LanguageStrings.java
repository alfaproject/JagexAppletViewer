package app;

import java.util.Enumeration;
import java.util.Hashtable;

final class LanguageStrings {

	private static Hashtable<String, String> _langStrings = new Hashtable<String, String>();

	static final void Load() {
		_langStrings.clear();
		_langStrings.put("err_missing_config", "Missing com.jagex.config setting");
		_langStrings.put("err_invalid_config", "Invalid com.jagex.config setting");
		_langStrings.put("loading_config", "Loading configuration");
		_langStrings.put("err_load_config", "Error loading configuration");
		_langStrings.put("err_decode_config", "Error decoding configuration");
		_langStrings.put("loaderbox_initial", "Loading...");
		_langStrings.put("error", "Error");
		_langStrings.put("quit", "Quit");
	}

	static final String Get(String name) {
		String value = _langStrings.get(name);
		return (value == null ? "" : value);
	}

	static final void Set(String name, String value) {
		_langStrings.put(name, value);
	}

	static final Enumeration GetNames() {
		return _langStrings.keys();
	}

} //class LanguageStrings