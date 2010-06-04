package app;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.Iterator;

final class Class_g
		implements AppletStub, AppletContext
{

	public final AppletContext getAppletContext() {
		return this;
	}

	public final void showDocument(URL paramURL) {
		if (appletviewer.debug) {
			System.out.println("showdocument url:" + paramURL);
		}
	}

	public final Applet getApplet(String paramString) {
		throw new UnsupportedOperationException();
	}

	public final String getParameter(String paramString) {
		String str = appletviewer.configInner.get(paramString);
		if (appletviewer.debug && (str == null)) {
			System.out.println("Unavailable param:" + paramString);
		}
		return str;
	}

	public final URL getCodeBase() {
		try {
			return new URL(appletviewer.configOur.get("codebase"));
		} catch (MalformedURLException ex) {
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
			return new URL(appletviewer.configOur.get("codebase"));
		} catch (MalformedURLException localMalformedURLException) {
			throw new InvalidParameterException();
		}
	}

	public final void showDocument(URL paramURL, String paramString) {
		if (appletviewer.debug) {
			System.out.println("showdocument url:" + paramURL + " target:" + paramString);
		}
	}

	public final boolean isActive() {
		return true;
	}

	public final Iterator getStreamKeys() {
		throw new UnsupportedOperationException();
	}

} //class Class_g