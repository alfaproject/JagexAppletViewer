package netscape.javascript;

import app.appletviewer;
import app.Preferences;
import java.applet.Applet;

public class JSObjec_ {

	public Object call(String paramString, Object[] paramArrayOfObject) {
		System.out.println("Received command: " + paramString);

		if (paramString.equals("zap")) {
			Preferences.Set("Member", "yes");
			Preferences.Save();
			appletviewer.removeadvert();
		} else if (paramString.equals("unzap")) {
			Preferences.Set("Member", "no");
			Preferences.Save();
			appletviewer.readdadvert();
		}

		return null;
	}

	public Object eval(String paramString) {
		System.out.println(paramString);
		return null;
	}

	public static JSObjec_ getWindow(Applet paramApplet) {
		return new JSObjec_();
	}

} //class JSObjec_