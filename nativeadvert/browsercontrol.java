package nativeadvert;

import java.awt.Canvas;

public class browsercontrol
{
	private static boolean error = false;
	private static boolean iscreated = false;

	private static native void resize0(int paramInt1, int paramInt2);

	private static native void destroy0();

	private static native void navigate0(String paramString);

	private static native boolean browsercontrol0(Canvas paramCanvas, String paramString);

	public static void resize(int paramInt1, int paramInt2)
	{
		if (!iscreated) {
			throw new IllegalStateException();
		}
		resize0(paramInt1, paramInt2);
	}

	public static boolean iscreated()
	{
		return iscreated;
	}

	public static void navigate(String paramString)
	{
		if (!iscreated) {
			throw new IllegalStateException();
		}
		navigate0(paramString);
	}

	public static boolean create(Canvas paramCanvas, String paramString)
	{
		if (iscreated) {
			throw new IllegalStateException();
		}
		if (error) {
			return false;
		}

		boolean bool = browsercontrol0(paramCanvas, paramString);
		if (!bool) {
			error = true;
		} else {
			iscreated = true;
		}
		return bool;
	}

	public static void destroy()
	{
		if (!iscreated) {
			throw new IllegalStateException();
		}
		destroy0();
		iscreated = false;
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * nativeadvert.browsercontrol JD-Core Version: 0.5.4
 */