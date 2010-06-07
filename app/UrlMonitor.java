package app;

final class UrlMonitor
		implements Runnable
{
	private static final UrlMonitor _instance = new UrlMonitor();
	private String _target = null;
	private String _url = null;

	@Override
	public final void run()
	{
		while (true) {
			String url, target;
			synchronized (this) {
				while (true) {
					if (_url != null) {
						url = _url;
						target = _target;
						_url = null;
						_target = null;
						break;
					}
					try {
						super.wait();
					} catch (InterruptedException ex) {
					}
				}
			}
			try {
				if (target != null && target.equals("_top") && (url.endsWith("MAGICQUIT") || url.indexOf("/quit.ws") != -1 || (url.indexOf(".ws") == -1 && url.endsWith("/")))) {
					// exit application
					appletviewer.terminate();
				}
				if (appletviewer.InWindows) {
					if (!(url.startsWith("http://") || url.startsWith("https://"))) {
						throw new Exception();
					}

					String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?&=,.%+-_#:/*";
					for (int i = 0; i < url.length(); i++) {
						if (allowedChars.indexOf(url.charAt(i)) == -1) {
							throw new Exception();
						}
					}

					Runtime.getRuntime().exec("cmd /c start \"j\" \"" + url + "\"");
				} else {
					throw new Exception("Not windows");
				}
			} catch (Exception ex) {
				if (appletviewer.Debug) {
					ex.printStackTrace();
				}
				try {
					new UrlViewer(url);
				} catch (Exception ex2) {
					if (appletviewer.Debug) {
						ex2.printStackTrace();
					}
				}
			}
		}
	}

	public static void start()
	{
		Thread thread = new Thread(_instance);
		thread.setPriority(10);
		thread.setDaemon(true);
		thread.start();
	}

	public static void showUrl(String url, String target)
	{
		synchronized (_instance) {
			_instance._url = url;
			_instance._target = target;
			_instance.notify();
		}
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_i JD-Core Version: 0.5.4
 */