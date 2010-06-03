package app;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

final class Class_g
  implements AppletStub, AppletContext
{
  public final AppletContext getAppletContext()
  {
    return this;
  }
  public final void showDocument(URL paramURL) {
    if (appletviewer.var_1f18)
    {
      System.out.println("showdocument url:" + paramURL);
    }
    Class_i.showurl(paramURL.toString(), null);
  }
  public final Applet getApplet(String paramString) {
    throw new UnsupportedOperationException();
  }

  public final String getParameter(String paramString) {
    String str = (String)appletviewer.var_1f60.get(paramString);
    if ((appletviewer.var_1f18) && (null == str)) {
      System.out.println("Unavailable param:" + paramString);
    }
    return str;
  }
  public final URL getCodeBase() {
    try {
      return new URL((String)appletviewer.var_1f28.get("codebase"));
    } catch (MalformedURLException localMalformedURLException) {
      throw new InvalidParameterException();
    }
  }

  public final void showStatus(String paramString) {
    throw new UnsupportedOperationException();
  }
  public final InputStream getStream(String paramString) {
    throw new UnsupportedOperationException();
  }
  public final Enumeration getApplets() {
    throw new UnsupportedOperationException();
  }
  public final AudioClip getAudioClip(URL paramURL) {
    throw new UnsupportedOperationException();
  }
  public final void setStream(String paramString, InputStream paramInputStream) throws IOException {
    throw new UnsupportedOperationException();
  }
  public final Image getImage(URL paramURL) {
    throw new UnsupportedOperationException();
  }
  public final void appletResize(int paramInt1, int paramInt2) {
  }
  public final URL getDocumentBase() {
    try {
      return new URL((String)appletviewer.var_1f28.get("codebase"));
    } catch (MalformedURLException localMalformedURLException) {
      throw new InvalidParameterException();
    }
  }

  public final void showDocument(URL paramURL, String paramString)
  {
    if (appletviewer.var_1f18)
    {
      System.out.println("showdocument url:" + paramURL + " target:" + paramString);
    }
    Class_i.showurl(paramURL.toString(), paramString);
  }
  public final boolean isActive() {
    return true;
  }
  public final Iterator getStreamKeys() {
    throw new UnsupportedOperationException();
  }
}

/* Location:           C:\Windows\.jagex_cache_32\jagexlauncher\bin\jagexappletviewer\
 * Qualified Name:     app.Class_g
 * JD-Core Version:    0.5.4
 */