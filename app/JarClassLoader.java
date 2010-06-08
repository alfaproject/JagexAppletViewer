package app;

import java.io.IOException;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Hashtable;

final class JarClassLoader
		extends ClassLoader
{
	private Hashtable<String, Class<?>> _loadedClasses = new Hashtable<String, Class<?>>();
	private ProtectionDomain _protectionDomain;
	private JavaArchive _jar;

	@Override
	protected final synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException
	{
		// try to load from cache
		Class<?> cl = _loadedClasses.get(name);
		if (cl != null) {
			return cl;
		}

		// try to load from system
		try {
			cl = super.findSystemClass(name);
			return cl;
		} catch (ClassNotFoundException ex) {
		}

		// try to load from jar
		byte[] classData = _jar.Extract(name + ".class");
		if (classData != null) {
			cl = defineClass(name, classData, 0, classData.length, _protectionDomain);
			if (resolve) {
				resolveClass(cl);
			}

			_loadedClasses.put(name, cl);
			return cl;
		}

		throw new ClassNotFoundException();
	}

	JarClassLoader(byte[] jarData)
			throws IOException
	{
		_jar = new JavaArchive(jarData);

		Permissions permissions = new Permissions();
		permissions.add(new AllPermission());
		_protectionDomain = new ProtectionDomain(new CodeSource(null, (Certificate[]) null), permissions);
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_s JD-Core Version: 0.5.4
 */