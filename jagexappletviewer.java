import app.appletviewer;

public class jagexappletviewer
{
	public static void main(String[] args)
	{
		appletviewer gameViewer = new appletviewer();
		if (args.length < 1) {
			gameViewer.load("runescape", "http://www.runescape.com/k=3/l=$(Language:0)/jav_config.ws", null, true);
		} else {
			String appName = args[0];
			String configUrl = System.getProperty("com.jagex.config");
			String configFile = System.getProperty("com.jagex.configfile");
			boolean debug = Boolean.getBoolean("com.jagex.debug");
			gameViewer.load(appName, configUrl, configFile, debug);
		}
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * jagexappletviewer JD-Core Version: 0.5.4
 */