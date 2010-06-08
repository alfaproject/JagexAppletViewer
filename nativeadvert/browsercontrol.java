package nativeadvert;

import java.awt.Canvas;

public class browsercontrol
{
	private static boolean error = false;
	private static boolean iscreated = false;

	private static native void resize0(int width, int height);

	private static native void destroy0();

	private static native void navigate0(String url);

	private static native boolean browsercontrol0(Canvas canvas, String url);

	public static void resize(int width, int height)
	{
		if (!iscreated) {
			throw new IllegalStateException();
		}
		resize0(width, height);
	}

	public static boolean iscreated()
	{
		return iscreated;
	}

	public static void navigate(String url)
	{
		if (!iscreated) {
			throw new IllegalStateException();
		}
		navigate0(url);
	}

	public static boolean create(Canvas canvas, String url)
	{
		if (iscreated) {
			throw new IllegalStateException();
		}
		if (error) {
			return false;
		}

		boolean success = browsercontrol0(canvas, url);
		if (!success) {
			error = true;
		} else {
			iscreated = true;
		}
		return success;
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