package app;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;

public final class appletviewer
		implements ComponentListener
{

	static boolean debug = false;

	static Frame window;
	private static Panel _panel;
	private static Applet _appletLoader;
	static boolean inWindows;
	private static float var_1f70;
	private static float var_1f78 = 0.0F;
	public static int var_1fa0;
	public static int var_1fa8;
	public static boolean var_1fb0;

	private static File _configFile;
	private static String _configUrl;
	static Hashtable<String, String> configOur = new Hashtable<String, String>();
	static Hashtable<String, String> configInner = new Hashtable<String, String>();

	public final void componentMoved(ComponentEvent paramComponentEvent) {
	}

	public final void componentResized(ComponentEvent paramComponentEvent) {
		resize();
	}

	private static final void loadConfigValues() {
		// reset configuration values
		LanguageStrings.Load();
		configInner.clear();
		configOur.clear();

		BufferedReader localBufferedReader = null;
		try {
			localBufferedReader = getConfigReader();
			String configLine = null;
			while ((configLine = localBufferedReader.readLine()) != null) {
				configLine = configLine.trim();

				if (configLine.startsWith("//") || configLine.startsWith("#")) {
					// ignore line comments
					continue;
				} else if (configLine.startsWith("msg=")) {
					// language string
					configLine = configLine.substring(4);

					int k = configLine.indexOf('=');
					if (k != -1) {
						String name = configLine.substring(0, k).trim().toLowerCase();
						String value = configLine.substring(k + 1).trim();
						LanguageStrings.Set(name, value);
						if (debug) {
							System.out.println("Message - name=" + name + " text=" + value);
						}
					}
				} else if (configLine.startsWith("param=")) {
					// config parameter
					configLine = configLine.substring(6);
					int k = configLine.indexOf('=');
					if (k != -1) {
						String name = configLine.substring(0, k).trim().toLowerCase();
						String value = configLine.substring(k + 1).trim();
						configInner.put(name, value);
						if (debug) {
							System.out.println("Innerconfig - variable=" + name + " value=" + value);
						}
					}
				} else {
					// other config variables
					int k = configLine.indexOf('=');
					if (k == -1) {
						// ignore invalid lines
						continue;
					}

					String name = configLine.substring(0, k).trim().toLowerCase();
					String value = configLine.substring(k + 1).trim();
					configOur.put(name, value);
					if (debug) {
						System.out.println("Ourconfig - variable=" + name + " value=" + value);
					}
				}
			}
		} catch (IOException ex) {
			if (debug) {
				ex.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_load_config"));
		} catch (Exception ex) {
			if (debug) {
				ex.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_decode_config"));
		} finally {
			if (localBufferedReader != null) {
				try {
					localBufferedReader.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	public final void componentHidden(ComponentEvent paramComponentEvent) {
	}

	private static final BufferedReader getConfigReader() throws IOException {
		if (_configUrl != null) {
			return new BufferedReader(new InputStreamReader(new URL(_configUrl).openStream()));
		}
		return new BufferedReader(new FileReader(_configFile));
	}

	public final void componentShown(ComponentEvent paramComponentEvent) {
	}

	public static final void Load(String resourcesName) {
		debug = Boolean.getBoolean("com.jagex.debug");
		if (debug) {
			System.setErr(DialogDebug.GetInstance("Jagex host console"));
			System.setOut(DialogDebug.GetInstance("Jagex host console"));
			System.out.println("release #7");
			System.out.println("java.version = " + System.getProperty("java.version"));
			System.out.println("os.name = " + System.getProperty("os.name"));
			System.out.println("os.arch = " + System.getProperty("os.arch"));
		}

		Preferences.Load();
		LanguageStrings.Load();

		window = new Frame();

		// load window icon
		File resourcesPath = new File(new File(System.getProperty("user.dir")).getParentFile(), resourcesName);
		File iconPath = new File(resourcesPath, "jagexappletviewer.png");
		if (debug) {
			System.out.println("Trying to load icon file: " + iconPath.getAbsolutePath());
		}
		if (iconPath.exists()) {
			Image icon = Toolkit.getDefaultToolkit().getImage(iconPath.getAbsolutePath());
			if (icon != null) {
				window.setIconImage(icon);
			}
		}

		// load 'loading' window
		LoaderBox.Create();

		// load config file
		LoaderBox.SetProgressText(LanguageStrings.Get("loading_config"));

		String configUrl = System.getProperty("com.jagex.config");
		String configFile = System.getProperty("com.jagex.configfile");

		if (configUrl == null) {
			if (configFile == null) {
				DialogFactory.ShowError(LanguageStrings.Get("err_missing_config"));
			}
			_configFile = new File(resourcesPath, configFile);
			if (debug) {
				System.out.println("Config File is " + _configFile.getAbsolutePath());
			}
		} else {
			_configUrl = configUrl;
			if (debug) {
				System.out.println("Config URL is " + _configUrl);
			}
		}

		loadConfigValues();

		String newVersion = configOur.get("viewerversion");
		if (newVersion != null) {
			try {
				if (Integer.parseInt(newVersion) > 100) {
					DialogFactory.ShowOk(LanguageStrings.Get("new_version"));
				}
			} catch (NumberFormatException ex) {
			}
		}

		String codeBase = configOur.get("codebase");

		String osName = System.getProperty("os.name").toLowerCase();
		inWindows = osName.startsWith("win");

		String homePath = null;
		try {
			homePath = System.getProperty("user.home");
			if ((homePath != null) && !homePath.endsWith("/")) {
				homePath += "/";
			}
		} catch (Exception ex) {
		}
		if (homePath == null) {
			homePath = "~/";
		}

		// load rs client
		LoaderBox.SetProgressText(LanguageStrings.Get("loading_app"));
		if (inWindows) {
			//Class_e.sub_ae5();
		}

		try {
			byte[] loaderBinary = downloadBinary(configOur.get("loader_jar"), codeBase);
			ClassLoaderZipFile loaderPackage = new ClassLoaderZipFile(loaderBinary);
			_appletLoader = (Applet)loaderPackage.loadClass("loader").newInstance();
			if (debug) {
				System.out.println("loader_jar : " + loaderBinary.length);
			}
		} catch (Exception localException3) {
			if (debug) {
				localException3.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_target_applet"));
		}

		// hide our loading dialog
		LoaderBox.Hide();

		window.setTitle(configOur.get("title") + " - hacked by _aLfa_ (c) 2010");

		int windowPreferredWidth = Integer.parseInt(configOur.get("window_preferredwidth"));
		int windowPreferredHeight = Integer.parseInt(configOur.get("window_preferredheight"));

		Insets windowInsets = window.getInsets();
		window.setSize(windowPreferredWidth + windowInsets.left + windowInsets.right, windowPreferredHeight + windowInsets.top + windowInsets.bottom);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		_panel = new Panel();
		_panel.setBackground(Color.black);
		_panel.setLayout(null);

		_panel.add(_appletLoader);
		window.add(_panel);
		window.doLayout();
		resize();

		window.addWindowListener(MainWindowAdapter.GetInstance());
		_panel.addComponentListener(new appletviewer());
		_appletLoader.setStub(new Class_g());
		_appletLoader.init();
		_appletLoader.start();
	}

	static final void Terminate() {
		System.exit(0);
	}

	private static final void resize() {
		int appletMinWidth = Integer.parseInt(configOur.get("applet_minwidth"));
		int appletMinHeight = Integer.parseInt(configOur.get("applet_minheight"));
		int appletMaxWidth = Integer.parseInt(configOur.get("applet_maxwidth"));
		int appletMaxHeight = Integer.parseInt(configOur.get("applet_maxheight"));

		Dimension panelSize = _panel.getSize();
		Insets panelInsets = _panel.getInsets();

		int availableWidth = panelSize.width - panelInsets.right - panelInsets.left;
		int availableHeight = panelSize.height - panelInsets.bottom - panelInsets.top;

		int appletWidth = availableWidth;
		if (appletWidth < appletMinWidth) {
			appletWidth = appletMinWidth;
		}
		if (appletWidth > appletMaxWidth) {
			appletWidth = appletMaxWidth;
		}

		int appletHeight = availableHeight;
		if (appletHeight < appletMinHeight) {
			appletHeight = appletMinHeight;
		}
		if (appletHeight > appletMaxHeight) {
			appletHeight = appletMaxHeight;
		}

		int panelMinWidth = availableWidth;
		if (panelMinWidth < appletMinWidth) {
			panelMinWidth = appletMinWidth;
		}

		int panelMinHeight = availableHeight;
		if (panelMinHeight < appletMinHeight) {
			panelMinHeight = appletMinHeight;
		}

		_panel.setSize(panelMinWidth, panelMinHeight);
		_appletLoader.setBounds(0, 0, appletWidth, appletHeight);
	}

	private static final byte[] downloadBinary(String fileName, String baseUrl) {
		byte[] buffer = new byte[300000];
		int bufferLength = 0;

		InputStream reader = null;
		try {
			reader = new URL(baseUrl + fileName).openStream();
			int bytesRead;
			while (
				(buffer.length > bufferLength) &&
				((bytesRead = reader.read(buffer, bufferLength, buffer.length - bufferLength)) > 0)
			) {
				var_1f78 += bytesRead;
				bufferLength += bytesRead;
				LoaderBox.SetProgressPercent((int)(var_1f78 / var_1f70 * 100.0F));
			}
		} catch (Exception ex) {
			if (debug) {
				ex.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_downloading") + ": " + fileName);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
				}
			}
		}

		byte[] binary = new byte[bufferLength];
		System.arraycopy(buffer, 0, binary, 0, bufferLength);
		return binary;
	}

	static {
		var_1f70 = 58988.0F;
		_configFile = null;
		_configUrl = null;
	}

} //class appletviewer