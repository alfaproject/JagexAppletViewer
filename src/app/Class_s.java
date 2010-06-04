package app;

import java.io.IOException;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Hashtable;

final class Class_s extends ClassLoader
{
  private Hashtable var_558 = new Hashtable();
  private ProtectionDomain var_560;
  private ZippedFile var_568;

  protected final synchronized Class loadClass(String paramString, boolean paramBoolean)
    throws ClassNotFoundException
  {
    Class localClass = (Class)this.var_558.get(paramString);
    if (null != localClass)
    {
      return localClass;
    }

    byte[] arrayOfByte = this.var_568.Extract(paramString + ".class");
    if (arrayOfByte != null)
    {
      localClass = defineClass(paramString, arrayOfByte, 0, arrayOfByte.length, this.var_560);
      if (paramBoolean) {
        resolveClass(localClass);
      }
      this.var_558.put(paramString, localClass);
      return localClass;
    }

    return super.findSystemClass(paramString);
  }
  Class_s(byte[] paramArrayOfByte) throws IOException {
    this.var_568 = new ZippedFile(paramArrayOfByte);

    CodeSource localCodeSource = new CodeSource(null, (Certificate[])null);
    Permissions localPermissions = new Permissions();
    localPermissions.add(new AllPermission());
    this.var_560 = new ProtectionDomain(localCodeSource, localPermissions);
  }
}

/* Location:           C:\Windows\.jagex_cache_32\jagexlauncher\bin\jagexappletviewer\
 * Qualified Name:     app.Class_s
 * JD-Core Version:    0.5.4
 */