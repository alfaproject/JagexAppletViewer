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

final class RunescapeAppletStub
		implements AppletStub, AppletContext
{

	public final AppletContext getAppletContext() {
		return this;
	}

	public final void showDocument(URL url) {
		if (appletviewer.debug) {
			System.out.println("RunescapeAppletStub.showDocument(url = " + url + ")");
		}
	}

	public final Applet getApplet(String name) {
		throw new UnsupportedOperationException();
	}

	public final String getParameter(String name) {
		String value;
		if (name.equalsIgnoreCase("sitesettings_member")) {
			value = "1"; // tell runescape client that we are a member
		} else {
			value = appletviewer.configInner.get(name);
		}

		if (appletviewer.debug) {
			System.out.println("RunescapeAppletStub.getParameter(name = " + name + ")\tresult = " + value);
		}
		return value;
	}

	public final URL getCodeBase() {
		try {
			return new URL(appletviewer.configOur.get("codebase"));
		} catch (MalformedURLException ex) {
			throw new InvalidParameterException();
		}
	}

	public final void showStatus(String status) {
		throw new UnsupportedOperationException();
	}

	public final InputStream getStream(String key) {
		throw new UnsupportedOperationException();
	}

	public final Enumeration getApplets() {
		throw new UnsupportedOperationException();
	}

	public final AudioClip getAudioClip(URL url) {
		throw new UnsupportedOperationException();
	}

	public final void setStream(String key, InputStream stream) throws IOException {
		throw new UnsupportedOperationException();
	}

	public final Image getImage(URL url) {
		throw new UnsupportedOperationException();
	}

	public final void appletResize(int width, int weight) {
	}

	public final URL getDocumentBase() {
		try {
			return new URL(appletviewer.configOur.get("codebase"));
		} catch (MalformedURLException ex) {
			throw new InvalidParameterException();
		}
	}

	public final void showDocument(URL url, String target) {
		if (appletviewer.debug) {
			System.out.println("RunescapeAppletStub.showDocument(url = " + url + ", target = " + target + ")");
		}
	}

	public final boolean isActive() {
		return true;
	}

	public final Iterator getStreamKeys() {
		throw new UnsupportedOperationException();
	}

} //class RunescapeAppletStub