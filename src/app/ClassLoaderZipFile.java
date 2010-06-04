package app;

import java.io.IOException;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Hashtable;

final class ClassLoaderZipFile
		extends ClassLoader
{
	private Hashtable<String, Class> _classes = new Hashtable<String, Class>();
	private ProtectionDomain _protectionDomain;
	private ZippedFile _zippedFile;

	@Override protected final synchronized Class loadClass(String name, boolean resolve)
		throws ClassNotFoundException
	{
		Class localClass = _classes.get(name);
		if (localClass != null) {
			return localClass;
		}

		byte[] classData = _zippedFile.Extract(name + ".class");
		if (classData != null) {
			localClass = defineClass(name, classData, 0, classData.length, _protectionDomain);
			if (resolve) {
				resolveClass(localClass);
			}
			_classes.put(name, localClass);
			return localClass;
		}

		return super.findSystemClass(name);
	}

	ClassLoaderZipFile(byte[] zippedBinary) throws IOException {
		_zippedFile = new ZippedFile(zippedBinary);

		CodeSource codeSource = new CodeSource(null, (Certificate[])null);
		Permissions permissions = new Permissions();
		permissions.add(new AllPermission());
		_protectionDomain = new ProtectionDomain(codeSource, permissions);
	}

} //class ClassLoaderZipFile