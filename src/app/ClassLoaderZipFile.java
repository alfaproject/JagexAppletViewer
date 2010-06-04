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

	ClassLoaderZipFile(byte[] zippedBinary) throws IOException {
		super(ClassLoaderZipFile.class.getClassLoader());

		_zippedFile = new ZippedFile(zippedBinary);

		CodeSource codeSource = new CodeSource(null, (Certificate[])null);
		Permissions permissions = new Permissions();
		permissions.add(new AllPermission());
		_protectionDomain = new ProtectionDomain(codeSource, permissions);
	}

	@Override protected final synchronized Class loadClass(String name, boolean resolve)
		throws ClassNotFoundException
	{
		// did we already load this class?
		Class cl = _classes.get(name);
		if (cl != null) {
			return cl;
		}

		// check if it is a system class
		try {
			return findSystemClass(name);
		} catch (Exception ex) {
			// ignore these
		}

		// all else failed, let's try to load it from zip file
		try {
			// load class data from zipped binary and save in byte array
			byte[] classData = _zippedFile.Extract(name + ".class");

			// convert byte array to class
			cl = defineClass(name, classData, 0, classData.length, _protectionDomain);

			// resolve the class definition if appropriate
			if (resolve) {
				resolveClass(cl);
			}

			// cache this class
			_classes.put(name, cl);

			// return class just created
			return cl;
		} catch (Exception ex) {
		}

		return null;
	}

} //class ClassLoaderZipFile