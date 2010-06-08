package app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

class JavaArchive
{
	private final HashMap<String, byte[]> _entries = new HashMap<String, byte[]>();

	public byte[] Extract(String fileName)
	{
		return _entries.get(fileName);
	}

	public JavaArchive(byte[] jarData)
		throws IOException
	{
		JarInputStream jarReader = new JarInputStream(new ByteArrayInputStream(jarData), true);
		JarEntry entry;
		while ((entry = jarReader.getNextJarEntry()) != null) {
			// get this file from jar
			ByteArrayOutputStream byteArrayWriter = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = jarReader.read(buffer, 0, 1024)) > 0) {
				byteArrayWriter.write(buffer, 0, bytesRead);
			}

			// put this entry file data in cache
			_entries.put(entry.getName(), byteArrayWriter.toByteArray());
		}
		jarReader.close();
	}
}