package app;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import nativeadvert.browsercontrol;

public class appletviewer
		implements ComponentListener, ActionListener
{
	public static boolean Debug = false;

	public static boolean InWindows;
	public static boolean In64Bits;

	private static File _configFile;
	private static String _configUrl;
	public static Hashtable<String, String> ConfigClient = new Hashtable<String, String>();
	public static Hashtable<String, String> ConfigApplet = new Hashtable<String, String>();

	public static Frame Window;
	private static Applet _gameApplet;

	private static Panel _panel;
	private static Component _footerPanel;
	private static MenuBar _menuBar;
	private static ScrollPane _scrollPane;
	private static Canvas _browserCanvas;
	private static float var_1f78 = 0.0F;

	public static HashMap<Integer, String> languages = new HashMap<Integer, String>();

	public final void componentMoved(ComponentEvent paramComponentEvent)
	{
	}

	public final void componentResized(ComponentEvent paramComponentEvent)
	{
		resize();
	}

	private void loadConfiguration()
	{
		languages.clear();
		ConfigApplet.clear();
		ConfigClient.clear();

		BufferedReader reader = null;
		try {
			reader = getConfigReader();
			String configLine;
			while ((configLine = reader.readLine()) != null) {
				configLine = configLine.trim();
				
				if (configLine.startsWith("//") || configLine.startsWith("#")) {
					// ignore commented lines
				} else if (configLine.startsWith("msg=")) {
					// language text strings
					configLine = configLine.substring(4);
					int k = configLine.indexOf('=');
					if (k != -1) {
						String alias = configLine.substring(0, k).trim().toLowerCase();
						String text = configLine.substring(k + 1).trim();
						if (alias.startsWith("lang")) {
							try {
								languages.put(Integer.valueOf(alias.substring(4)), text);
							} catch (NumberFormatException ex) {
								// invalid language id
							}
						}
						Language.setText(alias, text);
						if (Debug) {
							System.out.println("Message: alias = " + alias + ", text = " + text);
						}
					}
				} else if (configLine.startsWith("param=")) {
					// game applet configuration
					configLine = configLine.substring(6);
					int k = configLine.indexOf('=');
					if (k != -1) {
						String variable = configLine.substring(0, k).trim().toLowerCase();
						String value = configLine.substring(k + 1).trim();
						ConfigApplet.put(variable, value);
						if (Debug) {
							System.out.println("Applet config: variable = " + variable + ", value = " + value);
						}
					}
				} else {
					// client configuration
					int k = configLine.indexOf('=');
					if (k != -1) {
						String variable = configLine.substring(0, k).trim().toLowerCase();
						String value = configLine.substring(k + 1).trim();
						ConfigClient.put(variable, value);
						if (Debug) {
							System.out.println("Client config: variable = " + variable + ", value = " + value);
						}
					}
				}
			}
		} catch (IOException ex) {
			if (Debug) {
				ex.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_load_config"));
		} catch (Exception ex) {
			if (Debug) {
				ex.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_decode_config"));
		} finally {
			try {
				reader.close();
			} catch (Exception ex) {
			}
		}

		// create the language dialog if we have languages
		if (!languages.isEmpty()) {
			// build menu
			_menuBar = new MenuBar();
			Menu menuOptions = new Menu(Language.getText("options"));
			MenuItem menuLanguage = new MenuItem(Language.getText("language") + "...");
			menuLanguage.addActionListener(this);
			menuOptions.add(menuLanguage);
			_menuBar.add(menuOptions);
			Window.setMenuBar(_menuBar);
		}
	}

	public final void componentHidden(ComponentEvent paramComponentEvent)
	{
	}

	private static BufferedReader getConfigReader()
			throws IOException
	{
		if (_configUrl == null) {
			return new BufferedReader(new FileReader(_configFile));
		}
		return new BufferedReader(new InputStreamReader(new URL(_configUrl).openStream()));
	}

	public final void componentShown(ComponentEvent paramComponentEvent)
	{
	}

	private static File getPath(String fileName, String subDir, int mode, String workingPath)
	{
		String[] cachePaths1 = {
				"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", workingPath, "/tmp/", "" };
		String[] cachePaths2 = {
				".jagex_cache_" + mode, ".file_store_" + mode };

		for (String cachePath1 : cachePaths1) {
			for (String cachePath2 : cachePaths2) {
				for (int i = 0; i < 2; i++) {
					switch (i) {
						case 0:
							if (cachePath1.length() == 0 || new File(cachePath1).exists()) {
								new File(cachePath1 + cachePath2 + (subDir == null ? "" : "/" + subDir)).mkdirs();
							}
							break;
						case 1:
							String filePath = cachePath1 + cachePath2 + "/" + (subDir == null ? "" : subDir + "/") + fileName;
							File file = new File(filePath);

							// try to read and write this file
							RandomAccessFile testFile = null;
							try {
								testFile = new RandomAccessFile(file, "rw");
								int l = testFile.read();
								testFile.seek(0L);
								testFile.write(l);
								testFile.seek(0L);
								testFile.close();
								return file;
							} catch (IOException ioEx) {
								if (Debug) {
									System.out.println("Unable to open/write: " + filePath);
								}
								try {
									testFile.close();
								} catch (Exception ex) {
								}
							}
							break;
					}
				}
			}
		}

		if (Debug) {
			throw new RuntimeException("Fatal - could not find ANY location for file: " + fileName);
		}
		throw new RuntimeException();
	}

	public void load(String gameName, String configUrl, String configFile, boolean debug)
	{
		// detect windows os
		String osName = System.getProperty("os.name").toLowerCase();
		InWindows = osName.startsWith("win");

		// detect 64 bit architecture
		String osArch = System.getProperty("os.arch").toLowerCase();
		In64Bits = (osArch.startsWith("amd64") || osArch.startsWith("x86_64"));

		// set debug status
		Debug = debug;
		if (Debug) {
			System.setErr(DialogDebug.getPrintStream("Jagex host console"));
			System.setOut(DialogDebug.getPrintStream("Jagex host console"));

			System.out.println("release #7");
			System.out.println("java.version = " + System.getProperty("java.version"));
			System.out.println("os.name = " + osName);
			System.out.println("os.arch = " + osArch);
			System.out.println("========================================");
		}

		// load saved preferences
		Preferences.load();

		// load or detect locale
		int languageId = 0;
		String savedLangId = Preferences.get("Language");
		if (savedLangId != null) {
			languageId = Integer.parseInt(savedLangId);
		} else {
			Locale locale = Locale.getDefault();
			String localeLanguage = locale.getISO3Language();
			String localeCountry = locale.getISO3Country();
			if (Debug) {
				System.out.println("Current locale language: " + localeLanguage);
				System.out.println("Current locale country: " + localeLanguage);
			}

			if (localeLanguage != null) {
				if (localeLanguage.equals("ger") || localeLanguage.equals("deu") || localeLanguage.equals("nds") || localeLanguage.equals("gmh") || localeLanguage.equals("goh") || localeLanguage.equals("gem") || localeLanguage.equals("gsw")) {
					languageId = 1;
				} else if (localeLanguage.equals("fre") || localeLanguage.equals("fra") || localeLanguage.equals("cpf") || localeLanguage.equals("frm") || localeLanguage.equals("fro")) {
					languageId = 2;
				} else if (localeLanguage.equals("por") || localeLanguage.equals("cpp")) {
					languageId = 3;
				}
			} else if (localeCountry != null) {
				if (localeCountry.equals("deu") || localeCountry.equals("ddr")) {
					languageId = 1;
				} else if (localeCountry.equals("fra") || localeCountry.equals("fxx")) {
					languageId = 2;
				} else if (localeCountry.equals("prt") || localeCountry.equals("bra")) {
					languageId = 3;
				}
			}
			Preferences.set("Language", Integer.toString(languageId));
			Preferences.save();
		}
		Language.load(languageId);
		if (Debug) {
			System.out.println("Language ID loaded: " + languageId);
			System.out.println("========================================");
		}

		// load main window
		Window = new Frame();

		// detect game and current working path
		String currentPath = System.getProperty("user.home");
		if (currentPath == null) {
			currentPath = "~/";
		} else if (!currentPath.endsWith("/")) {
			currentPath += "/";
		}
		File gamePath = new File(new File(currentPath).getParentFile(), gameName);

		// load window icon
		File iconFile = new File(gamePath, "jagexappletviewer.png");
		if (Debug) {
			System.out.println("Trying to load icon file: " + iconFile.getAbsolutePath());
			System.out.println("========================================");
		}
		if (iconFile.exists()) {
			Image icon = Toolkit.getDefaultToolkit().getImage(iconFile.getAbsolutePath());
			if (icon != null) {
				Window.setIconImage(icon);
			}
		}

		// open loading dialog
		ProgressComponent.create();

		// load configuration
		ProgressComponent.setText(Language.getText("loading_config"));
		if (configUrl == null) {
			if (configFile == null) {
				DialogMessage.showError(Window, Language.getText("err_missing_config"));
			} else {
				_configFile = new File(gamePath, configFile);
				if (Debug) {
					System.out.println("Config file: " + _configFile.getAbsolutePath());
				}
			}
		} else {
			if (configUrl != null) {
				_configUrl = formatWithConfig(configUrl);
				if (Debug) {
					System.out.println("Config URL: " + _configUrl);
				}

				// get config url domain
				String domain = _configUrl.toLowerCase();
				if (domain.startsWith("http://")) {
					domain = domain.substring(7);
				} else if (domain.startsWith("https://")) {
					domain = domain.substring(8);
				}

				// trim port
				int colonPos = domain.indexOf(":");
				if (colonPos != -1) {
					domain = domain.substring(0, colonPos);
				}

				// trim extra path
				int slashPos = domain.indexOf("/");
				if (slashPos != -1) {
					domain = domain.substring(0, slashPos);
				}

				if (Debug) {
					System.out.println("Config URL domain: " + domain);
				}
				if (!(domain.endsWith(".runescape.com") || domain.endsWith(".funorb.com"))) {
					DialogMessage.showError(Window, Language.getText("err_invalid_config"));
				}
			}
		}
		loadConfiguration();
		if (Debug) {
			System.out.println("========================================");
		}

		// check for newer version
		String confVersion = ConfigClient.get("viewerversion");
		if (confVersion != null) {
			try {
				int version = Integer.parseInt(confVersion);
				if (version > 100) {
					DialogMessage.showMessage(Window, Language.getText("new_version"));
				}
			} catch (NumberFormatException localNumberFormatException) {
			}
		}

		// get some config variables
		int mode = Integer.parseInt(ConfigApplet.get("modewhat")) + 32;
		String cacheSubdir = ConfigClient.get("cachesubdir");
		String codeBase = ConfigClient.get("codebase");

		// download browsercontrol
		ProgressComponent.setText(Language.getText("loading_app_resources"));
		File browserControlPath = null;
		try {
			if (InWindows) {
				byte[] browserControlJar, browserControlDll;
				if (In64Bits) {
					browserControlJar = downloadFile(ConfigClient.get("browsercontrol_win_amd64_jar"), codeBase);
					browserControlDll = new JavaArchive(browserControlJar).ExtractAndValidate("browsercontrol64.dll");
					if (browserControlDll == null) {
						DialogMessage.showError(Window, Language.getText("err_verify_bc64"));
					}

					browserControlPath = getPath("browsercontrol64.dll", cacheSubdir, mode, currentPath);
					saveFile(browserControlDll, browserControlPath);
				} else {
					browserControlJar = downloadFile(ConfigClient.get("browsercontrol_win_x86_jar"), codeBase);
					browserControlDll = new JavaArchive(browserControlJar).ExtractAndValidate("browsercontrol.dll");
					if (browserControlDll == null) {
						DialogMessage.showError(Window, Language.getText("err_verify_bc"));
					}

					browserControlPath = getPath("browsercontrol.dll", cacheSubdir, mode, currentPath);
					saveFile(browserControlDll, browserControlPath);
				}
				if (Debug) {
					System.out.println("Browser control jar size: " + browserControlJar.length + " bytes");
					System.out.println("Browser control dll size: " + browserControlDll.length + " bytes");
					System.out.println("========================================");
				}
			}
		} catch (Exception ex) {
			if (Debug) {
				ex.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_load_bc"));
		}

		// set up our own class loader, so we can trap
		// 'netscape.javascript.jsobject'
		if (InWindows) {
			MasterClassLoader.init();
		}

		// download game loader
		ProgressComponent.setText(Language.getText("loading_app"));

		try {
			byte[] loaderJar = downloadFile(ConfigClient.get("loader_jar"), codeBase);
			JarClassLoader jcl = new JarClassLoader(loaderJar);
			_gameApplet = (Applet) jcl.loadClass("loader").newInstance();
			if (Debug) {
				System.out.println("Loader jar size: " + loaderJar.length + " bytes");
				System.out.println("========================================");
			}
		} catch (Exception ex) {
			if (Debug) {
				ex.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_target_applet"));
		}

		// hide loading dialog
		ProgressComponent.hideDialog();

		// set up client settings
		Window.setTitle(ConfigClient.get("title") + " - hacked by _aLfa_ (c) 2010");

		int advertHeight = (InWindows ? Integer.parseInt(ConfigClient.get("advert_height")) : 0);
		int preferredWidth = Integer.parseInt(ConfigClient.get("window_preferredwidth"));
		int preferredHeight = Integer.parseInt(ConfigClient.get("window_preferredheight"));
		int footerPadding = 40;

		Insets insets = Window.getInsets();
		Window.setSize(preferredWidth + insets.left + insets.right, preferredHeight + advertHeight + footerPadding + insets.top + insets.bottom);
		Window.setLocationRelativeTo(null);
		Window.setVisible(true);
		_scrollPane = new ScrollPane();
		Window.add(_scrollPane);
		_panel = new Panel();
		_panel.setBackground(Color.red);
		_panel.setLayout(null);
		_scrollPane.add(_panel);

		// display the browser canvas if the user isn't a member
		String member = Preferences.get("Member");
		if (InWindows && (member == null || !member.equals("yes"))) {
			_browserCanvas = new Canvas();
			_panel.add(_browserCanvas);
		}

		_panel.add(_gameApplet);
		_footerPanel = new CopyrightBar(Language.getText("tandc"));
		_panel.add(_footerPanel);
		Window.doLayout();
		resize();
		_scrollPane.doLayout();

		// display the browser control with advertising
		if (InWindows && _browserCanvas != null) {
			// wait until the browser canvas is visible
			while (!(_browserCanvas.isDisplayable() && _browserCanvas.isShowing())) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException ex) {
				}
			}

			// create the browser control in the browser canvas
			try {
				System.load(browserControlPath.getPath());
				browsercontrol.create(_browserCanvas, ConfigClient.get("adverturl"));
				browsercontrol.resize(_browserCanvas.getSize().width, _browserCanvas.getSize().height);
			} catch (Throwable ex) {
				if (Debug) {
					ex.printStackTrace();
				}
				DialogMessage.showError(Window, Language.getText("err_create_advertising"));
			}
		}

		Window.addWindowListener(TerminateEventHandler.getInstance());
		_scrollPane.addComponentListener(new appletviewer());
		_gameApplet.setStub(new GameAppletStub());
		_gameApplet.init();
		_gameApplet.start();
	}

	public static void removeAdvert()
	{
		if (_browserCanvas == null) {
			return;
		}
		if (browsercontrol.iscreated()) {
			browsercontrol.destroy();
		}
		_panel.remove(_browserCanvas);
		_browserCanvas = null;
		resize();
	}

	private static String formatWithConfig(String format)
	{
		String text = format;
		do {
			int holderStart = text.indexOf("$(");
			if (holderStart == -1) {
				break;
			}
			int holderMiddle = text.indexOf(":", holderStart);
			if (holderMiddle == -1) {
				break;
			}
			int holderEnd = text.indexOf(")", holderMiddle);
			if (holderEnd == -1) {
				break;
			}

			String prefName = text.substring(holderStart + 2, holderMiddle);
			String prefDefault = text.substring(holderMiddle + 1, holderEnd);
			String prefValue = Preferences.get(prefName);
			if (prefValue == null) {
				prefValue = prefDefault;
			}

			if (holderEnd + 1 == text.length()) {
				text = text.substring(0, holderStart) + prefValue;
				break;
			} else {
				text = text.substring(0, holderStart) + prefValue + text.substring(holderEnd + 1);
			}
		} while (true);

		return text;
	}

	public static void reAddAdvert()
	{
		if (!InWindows || _browserCanvas != null) {
			return;
		}

		_browserCanvas = new Canvas();
		_panel.add(_browserCanvas);
		resize();

		// wait until the browser canvas is visible
		while (!(_browserCanvas.isDisplayable() && _browserCanvas.isShowing())) {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException ex) {
			}
		}

		// create the browser control in the browser canvas
		try {
			browsercontrol.create(_browserCanvas, ConfigClient.get("adverturl"));
			browsercontrol.resize(_browserCanvas.getSize().width, _browserCanvas.getSize().height);
		} catch (Throwable ex) {
			if (Debug) {
				ex.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_create_advertising"));
		}
	}

	public static void terminate()
	{
		if (browsercontrol.iscreated()) {
			browsercontrol.destroy();
		}
		System.exit(0);
	}

	private static void saveFile(byte[] fileData, File filePath)
	{
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(filePath);
			writer.write(fileData, 0, fileData.length);
		} catch (Exception ex) {
			if (Debug) {
				ex.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_save_file"));
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception ex) {
					// ignore
				}
			}
		}
	}

	private static void resize()
	{
		int advertHeight = (_browserCanvas == null ? 0 : Integer.parseInt(ConfigClient.get("advert_height")));
		int footerHeight = 40;

		int appletMinWidth = Integer.parseInt(ConfigClient.get("applet_minwidth"));
		int appletMinHeight = Integer.parseInt(ConfigClient.get("applet_minheight"));
		int appletMaxWidth = Integer.parseInt(ConfigClient.get("applet_maxwidth"));
		int appletMaxHeight = Integer.parseInt(ConfigClient.get("applet_maxheight"));

		Dimension paneSize = _scrollPane.getSize();
		Insets paneInsets = _scrollPane.getInsets();

		int availableWidth = paneSize.width - paneInsets.left - paneInsets.right;
		int availableHeight = paneSize.height - paneInsets.top + -paneInsets.bottom;

		int appletWidth = availableWidth;
		if (appletWidth < appletMinWidth) {
			appletWidth = appletMinWidth;
		} else if (appletWidth > appletMaxWidth) {
			appletWidth = appletMaxWidth;
		}

		int appletHeight = availableHeight - advertHeight - footerHeight;
		if (appletHeight < appletMinHeight) {
			appletHeight = appletMinHeight;
		} else if (appletHeight > appletMaxHeight) {
			appletHeight = appletMaxHeight;
		}

		int panelWidth = availableWidth;
		if (panelWidth < appletMinWidth) {
			panelWidth = appletMinWidth;
		}

		int panelHeight = availableHeight;
		if (panelHeight < footerHeight + appletMinHeight + advertHeight) {
			panelHeight = footerHeight + appletMinHeight + advertHeight;
		}

		_panel.setSize(panelWidth, panelHeight);
		if (_browserCanvas != null) {
			_browserCanvas.setBounds((panelWidth - appletWidth) / 2, 0, appletWidth, advertHeight);
			if (browsercontrol.iscreated()) {
				browsercontrol.resize(appletWidth, appletHeight);
			}
		}
		_gameApplet.setBounds((panelWidth - appletWidth) / 2, advertHeight, appletWidth, appletHeight);
		_footerPanel.setBounds((panelWidth - appletWidth) / 2, advertHeight + appletHeight, appletWidth, footerHeight);
	}

	private static byte[] downloadFile(String fileName, String baseUrl)
	{
		byte[] buffer = new byte[300000];
		int bufferOffset = 0;

		try {
			InputStream reader = new URL(baseUrl + fileName).openStream();
			int bytesRead;
			while (bufferOffset < buffer.length && (bytesRead = reader.read(buffer, bufferOffset, buffer.length - bufferOffset)) != -1) {
				bufferOffset += bytesRead;
				var_1f78 += bytesRead;
				ProgressComponent.setPercent((int) (var_1f78 / 60000.0F * 100.0F));
			}

			reader.close();
		} catch (Exception localException) {
			if (Debug) {
				localException.printStackTrace();
			}
			DialogMessage.showError(Window, Language.getText("err_downloading") + ": " + fileName);
		}

		byte[] fileData = new byte[bufferOffset];
		System.arraycopy(buffer, 0, fileData, 0, bufferOffset);
		return fileData;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// currently in use by the language menu
		new DialogLanguage(appletviewer.Window, appletviewer.languages);
	}

	public static void showUrl(String url, String target)
	{
		// quit url
		if (target != null && target.equals("_top") && (url.endsWith("MAGICQUIT") || url.indexOf("/quit.ws") != -1 || (url.indexOf(".ws") == -1 && url.endsWith("/")))) {
			// exit application
			appletviewer.terminate();
		} else if (url.startsWith("http://") || url.startsWith("https://")) {
			// we only open http or https urls
			try {
				// open url in user's default browser
				Desktop.getDesktop().browse(new URI(url));
			} catch (URISyntaxException ex) {
				// ignore invalid url
				if (appletviewer.Debug) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				// default browser could not be opened for some reason
				if (appletviewer.Debug) {
					ex.printStackTrace();
				}

				// show a window for user to copy/paste the url
				new DialogUrl(url);
			}
		}
	}
}