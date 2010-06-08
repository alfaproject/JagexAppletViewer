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
import java.util.Map;
import java.util.Iterator;

final class GameAppletStub
		implements AppletStub, AppletContext
{
	private Map<String, String> _configClient;
	private Map<String, String> _configApplet;

	public GameAppletStub(Map<String, String> configClient, Map<String, String> configApplet) {
		_configClient = configClient;
		_configApplet = configApplet;
	}

	@Override
	public final void showDocument(URL url)
	{
		if (appletviewer.debug) {
			System.out.println("GameAppletStub.showDocument(url = " + url + ")");
		}
		appletviewer.getInstance().showUrl(url.toString(), null);
	}

	@Override
	public final void showDocument(URL url, String target)
	{
		if (appletviewer.debug) {
			System.out.println("GameAppletStub.showDocument(url = " + url + ", target = " + target + ")");
		}
		appletviewer.getInstance().showUrl(url.toString(), target);
	}

	@Override
	public final String getParameter(String name)
	{
		String value = _configApplet.get(name);
		if (appletviewer.debug) {
			System.out.println("GameAppletStub.getParameter(name = " + name + "):\tresult = " + value);
		}
		return value;
	}

	@Override
	public final URL getCodeBase()
	{
		try {
			return new URL(_configClient.get("codebase"));
		} catch (MalformedURLException ex) {
			throw new InvalidParameterException();
		}
	}

	@Override
	public final URL getDocumentBase()
	{
		try {
			return new URL(_configClient.get("codebase"));
		} catch (MalformedURLException ex) {
			throw new InvalidParameterException();
		}
	}

	@Override
	public final AppletContext getAppletContext()
	{
		return this;
	}

	@Override
	public final Applet getApplet(String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final void showStatus(String status)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getStream(String key)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final Enumeration<Applet> getApplets()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final AudioClip getAudioClip(URL url)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final void setStream(String key, InputStream stream)
			throws IOException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final Image getImage(URL url)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final void appletResize(int width, int height)
	{
	}

	@Override
	public final boolean isActive()
	{
		return true;
	}

	@Override
	public final Iterator<String> getStreamKeys()
	{
		throw new UnsupportedOperationException();
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_g JD-Core Version: 0.5.4
 */