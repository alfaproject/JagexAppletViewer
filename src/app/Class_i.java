package app;

final class Class_i
  implements Runnable
{

	private String var_59c = null;
	private static Class_i var_5a4;
	private String var_5ac = null;

	public final void run() {
		while (true) {
			String str1 = null;

			String str2 = null;
			synchronized (this) {
				while (true) {
					if (null != this.var_5ac) {
						str1 = this.var_59c;
						str2 = this.var_5ac;
						this.var_5ac = null;
						this.var_59c = null;
						break;
					}
					try {
						super.wait();
					} catch (InterruptedException localInterruptedException) {
					}
				}
			}
			try {
				if ((str1 != null) && (str1.equals("_top")) && (((str2.endsWith("MAGICQUIT")) || (0 != (str2.indexOf("/quit.ws") ^ 0xFFFFFFFF)) || ((str2.indexOf(".ws") == -1) && (str2.endsWith("/")))))) {
					appletviewer.Terminate();
				}
				if (appletviewer.inWindows) {
					if ((!str2.startsWith("http://")) && (!str2.startsWith("https://"))) {
						throw new Exception();
					}

					String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?&=,.%+-_#:/*";
					for (int i = 0; i < str2.length(); i++) {
						if (allowedChars.indexOf(str2.charAt(i)) == -1) {
							throw new Exception();
						}
					}

					Runtime.getRuntime().exec("cmd /c start \"j\" \"" + str2 + "\"");
				} else {
					throw new Exception("Not windows");
				}
			} catch (Exception localException1) {
				if (appletviewer.debug) {
					localException1.printStackTrace();
				}
				try {
					new DialogCopyPasteUrl(str2);
				} catch (Exception localException2) {
					if (appletviewer.debug) {
						localException2.printStackTrace();
					}
				}
			}
		}
	}

	static final void sub_7d4() {
		if (var_5a4 != null) {
			return;
		}
		var_5a4 = new Class_i();

		Thread localThread = new Thread(var_5a4);
		localThread.setPriority(10);
		localThread.setDaemon(true);
		localThread.start();
	}

	public static final void showurl(String paramString1, String paramString2) {
		synchronized (var_5a4) {
			var_5a4.var_5ac = paramString1;
			var_5a4.var_59c = paramString2;
			var_5a4.notify();
		}
	}

	static {
		var_5a4 = null;
	}

} //class Class_i