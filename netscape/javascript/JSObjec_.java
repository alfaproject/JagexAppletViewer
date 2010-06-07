package netscape.javascript;

import app.Preferences;
import app.appletviewer;
import java.applet.Applet;

public class JSObjec_
{
	public Object call(String methodName, Object[] args)
	{
		if (appletviewer.Debug) {
			System.out.println("JSObject.call(methodName = " + methodName + ", args.length = " + args.length + ");");
		}

		if (methodName.equals("zap")) {
			Preferences.set("Member", "yes");
			Preferences.save();
			appletviewer.removeAdvert();
		} else if (methodName.equals("unzap")) {
			Preferences.set("Member", "no");
			Preferences.save();
			appletviewer.reAddAdvert();
		}

		return null;
	}

	public Object eval(String s)
	{
		if (appletviewer.Debug) {
			System.out.println("JSObject.eval(s = " + s + ");");
		}

		System.out.println(s);
		return null;
	}

	public static JSObjec_ getWindow(Applet paramApplet)
	{
		return new JSObjec_();
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * netscape.javascript.JSObjec_ JD-Core Version: 0.5.4
 */