package app;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import nativeadvert.browsercontrol;

public final class appletviewer
  implements ComponentListener
{
  private static Panel var_1f08;
  private static Component var_1f10;
  static boolean debug = false;
  private static Applet var_1f20;
  static Hashtable var_1f28 = new Hashtable();
  static boolean var_1f30;
  private static MenuBar var_1f38;
  private static boolean var_1f40;
  static Frame MainFrame;
  private static ScrollPane var_1f50;
  private static Canvas var_1f58;
  static Hashtable var_1f60 = new Hashtable();
  private static File var_1f68;
  private static float var_1f70;
  private static float var_1f78 = 0.0F;
  private static String var_1f80;
  private static int[] var_1f88;
  static String[] languageNames;
  public static int var_1fa0;
  public static int var_1fa8;
  public static boolean var_1fb0;

  public final void componentMoved(ComponentEvent paramComponentEvent)
  {
  }

  public final void componentResized(ComponentEvent paramComponentEvent)
  {
    sub_3809(2);
  }

	private static final boolean sub_2026(byte paramByte, int paramInt) { boolean bool = Preferences.dummy; int i = 0;
		var_1f60.clear();
		LanguageStrings.Load();
		var_1f28.clear();
		Object localObject = null;
		int k = 0;
		try {
			String str4;
			BufferedReader localBufferedReader = sub_2562(93);

			do {
				do {
					localObject = localBufferedReader.readLine();
					if (localObject == null) {
						//break label469;
						break;
					}

					localObject = ((String)localObject).trim();
				} while ((((String)localObject).startsWith("//")) || (((String)localObject).startsWith("#")));

				if (!((String)localObject).startsWith("param=")) {
					String str2;
					if (((String)localObject).startsWith("msg=")) {
						localObject = ((String)localObject).substring(4);

						k = ((String)localObject).indexOf('=');
						if (0 != (k ^ 0xFFFFFFFF)) {
							str2 = ((String)localObject).substring(0, k).trim().toLowerCase();
							str4 = ((String)localObject).substring(k + 1).trim();
							if (str2.startsWith("lang")) {
								try {
									Integer.parseInt(str2.substring(4));
									++i;
								} catch (NumberFormatException localNumberFormatException2) {
								}
							}
							LanguageStrings.Set(str2, str4);
							if (debug) {
								System.out.println("Message - name=" + str2 + " text=" + str4);
							}
						}

						if (!bool) {
							continue;
						}
					}
					k = ((String)localObject).indexOf('=');
					if (-1 != k) {
						str2 = ((String)localObject).substring(0, k).trim().toLowerCase();

						str4 = ((String)localObject).substring(k - -1).trim();
						var_1f28.put(str2, str4);
						if (debug) {
							System.out.println("Ourconfig - variable=" + str2 + " value=" + str4);
						}
					}

					if (!bool) {
						continue;
					}
				}
				localObject = ((String)localObject).substring(6);

				k = ((String)localObject).indexOf('=');
				if (k == -1) {
					continue;
				}
				String str2 = ((String)localObject).substring(0, k).trim().toLowerCase();

				str4 = ((String)localObject).substring(k + 1).trim();
				var_1f60.put(str2, str4);
				if (!debug)
					continue;
				System.out.println("Innerconfig - variable=" + str2 + " value=" + str4);
			} while (!bool);

			label469:
			localBufferedReader.close();
		} catch (IOException localIOException) {
			if (debug) {
				localIOException.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_load_config"));
		} catch (Exception localException) {
			if (debug) {
				localException.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_decode_config"));
		}
		if (0 < i) {
			var_1f88 = new int[i];
			languageNames = new String[i];

			do {
				Enumeration localEnumeration = LanguageStrings.GetNames();
				do {
					if (!localEnumeration.hasMoreElements()) {
						//break label729;
						break;
					}
					localObject = (String)localEnumeration.nextElement();
					if (!((String)localObject).startsWith("lang")) {
						//break label724;
						break;
					}
					k = 0;
					try {
						k = Integer.parseInt(((String)localObject).substring(4));
					} catch (NumberFormatException ex) {
					}
				} while (!bool);

				int str1 = 0;
				for (int str3 = 0; str3 < str1; str3++) {
					if (((str3 ^ 0xFFFFFFFF) == (str1 ^ 0xFFFFFFFF)) || (var_1f88[str3] > k)) {
						for (int str4 = str1; str4 > str3; str4--) {
							languageNames[str4] = languageNames[str4 - 1];
							var_1f88[str4] = var_1f88[str4 - 1];
						}

						var_1f88[str3] = k;
						languageNames[str3] = LanguageStrings.Get((String)localObject);
						if (!bool)
							break;
					}
				}

				label724:
				++str1;
			} while (!bool);

			label729:
			DialogLanguage.Create();
			var_1f38 = new MenuBar();

			localObject = new Menu(LanguageStrings.Get("options"));

			MenuItem localMenuItem = new MenuItem(LanguageStrings.Get("language") + "...");
			Class_f localClass_f = new Class_f();
			localMenuItem.addActionListener(localClass_f);
			((Menu)localObject).add(localMenuItem);
			var_1f38.add((Menu)localObject);
			MainFrame.setMenuBar(var_1f38);
			if (Preferences.Get("Language") == null) {
				return -1 < (sub_260c(true) ^ 0xFFFFFFFF);
			}
		}

		return true;
	}

  public final void componentHidden(ComponentEvent paramComponentEvent)
  {
  }

  private static final BufferedReader sub_2562(int paramInt)
    throws IOException
  {
    if (var_1f80 != null) {
      return new BufferedReader(new InputStreamReader(new URL(var_1f80).openStream()));
    }

    int i = -39 % ((paramInt - 29) / 52);
    return new BufferedReader(new FileReader(var_1f68));
  }

  static final int sub_260c(boolean paramBoolean)
  {
    int i = DialogLanguage.GetChoiceIndex();
    if (i < 0)
    {
      return -1;
    }
    Preferences.Set("Language", Integer.toString(var_1f88[i]));
    if (paramBoolean != true) {
      removeadvert();
    }
    Preferences.Save();
    return i;
  }

  public final void componentShown(ComponentEvent paramComponentEvent)
  {
  }

  private static final File sub_26a2(String paramString1, boolean paramBoolean, String paramString2, int paramInt, String paramString3)
  {
    boolean bool = Preferences.dummy; String[] arrayOfString1 = { "c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", paramString3, "/tmp/", "" };
    String[] arrayOfString2 = { ".jagex_cache_" + paramInt, ".file_store_" + paramInt };

    int i = 0;
    if (paramBoolean)
      return (File)null;
    do {
      if (i >= 2) {
        break;
      }

      int j = 0;
      do { if (arrayOfString2.length <= j) {
          break;
        }

        int k = 0;
        do { if ((arrayOfString1.length ^ 0xFFFFFFFF) >= (k ^ 0xFFFFFFFF)) {
            break;
          }

          String str1 = arrayOfString1[k] + arrayOfString2[j] + "/" + ((paramString2 == null) ? "" : new StringBuffer().append(paramString2).append("/").toString()) + paramString1;
          RandomAccessFile localRandomAccessFile = null;
          try
          {
            File localFile = new File(str1);
            if ((-1 != (i ^ 0xFFFFFFFF)) || (localFile.exists()) || 
              (bool))
            {
              String str2 = arrayOfString1[k];
              if ((i != 1) || (0 >= str2.length()) || (new File(str2).exists()) || 
                (bool))
              {
                new File(arrayOfString1[k] + arrayOfString2[j]).mkdir();
                if (null != paramString2)
                {
                  new File(arrayOfString1[k] + arrayOfString2[j] + "/" + paramString2).mkdir();
                }
                localRandomAccessFile = new RandomAccessFile(localFile, "rw");

                int l = localRandomAccessFile.read();
                localRandomAccessFile.seek(0L);
                localRandomAccessFile.write(l);
                localRandomAccessFile.seek(0L);
                localRandomAccessFile.close();
                return localFile;
              }
            }
          } catch (Exception localException1) {
            if (debug)
              System.out.println("Unable to open/write: " + str1);
            try
            {
              if (localRandomAccessFile != null)
              {
                localRandomAccessFile.close();
                localRandomAccessFile = null;
              }
            }
            catch (Exception localException2)
            {
            }
          }
          ++k; } while (!bool);

        ++j; } while (!bool);

      ++i; } while (!bool);

    if (!debug) {
      throw new RuntimeException();
    }
    throw new RuntimeException("Fatal - could not find ANY location for file: " + paramString1);
  }

	public static final void Load(String resourcesName) {
		boolean bool = Preferences.dummy;
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

		MainFrame = new Frame();

		// load window icon
		File resourcesPath = new File(new File(System.getProperty("user.dir")).getParentFile(), resourcesName);
		File iconPath = new File(resourcesPath, "jagexappletviewer.png");
		System.out.println("Trying to load icon file: " + iconPath.getAbsolutePath());
		if (iconPath.exists()) {
			Image icon = Toolkit.getDefaultToolkit().getImage(iconPath.getAbsolutePath());
			if (icon != null) {
				MainFrame.setIconImage(icon);
			}
		}

		LoaderBox.Create();

		LoaderBox.SetProgressText(LanguageStrings.Get("loading_config"));
		Object localObject3 = System.getProperty("com.jagex.config");

		String str2 = System.getProperty("com.jagex.configfile");
		if (null == localObject3) {
			if (str2 == null) {
				DialogFactory.ShowError(LanguageStrings.Get("err_missing_config"));
			}
			var_1f68 = new File(resourcesPath, str2);
		}
		//label670:
		int k;
		int i = 0;
		do {
			String str1 = null;
			do {
				if (localObject3 != null) {
					var_1f80 = sub_34ed(-1, (String)localObject3);
					System.out.println("Config URL is " + var_1f80);

					String str3 = var_1f80.toLowerCase();

					if (!str3.startsWith("http://")) {
						if (!str3.startsWith("https://")) {
							break; //break label670;
						}
						str3 = str3.substring(8);
						if (!bool) {
							break; //break label670;
						}
					}
					str3 = str3.substring(7);

					k = str3.indexOf(":");

					if ((k ^ 0xFFFFFFFF) != 0) {
						str3 = str3.substring(0, k);
					}
					int i1 = str3.indexOf("/");
					if (0 != (i1 ^ 0xFFFFFFFF)) {
						str3 = str3.substring(0, i1);
					}
					if (debug) {
						System.out.println("Domain: " + str3);
					}
					if ((!str3.endsWith(".runescape.com")) && (!str3.endsWith(".funorb.com"))) {
						DialogFactory.ShowError(LanguageStrings.Get("err_invalid_config"));
					}
				}

				if ((sub_2026((byte)69, i)) && (!bool)) {
					break; //break label829;
				}
				str1 = Preferences.Get("Language");
				i = 0;
			} while (str1 == null);
			i = Integer.parseInt(str1);
		} while (!bool);

		//label829:
		String str3 = (String)var_1f28.get("viewerversion");
		if (str3 != null) {
			try {
				k = Integer.parseInt(str3);
				if (-101 > (k ^ 0xFFFFFFFF)) {
					DialogFactory.ShowOk(LanguageStrings.Get("new_version"));
				}
			} catch (NumberFormatException localNumberFormatException) {
			}
		}

		int l = Integer.parseInt((String)var_1f60.get("modewhat")) - -32;

		String str4 = (String)var_1f28.get("cachesubdir");

		String str5 = (String)var_1f28.get("codebase");

		String str6 = System.getProperty("os.name").toLowerCase();
		String str7 = System.getProperty("os.arch").toLowerCase();
		var_1f30 = str6.startsWith("win");

		var_1f40 = ((var_1f30) && (str7.startsWith("amd64"))) || (str7.startsWith("x86_64"));
		String str8 = null;
		try {
			str8 = System.getProperty("user.home");
			if (str8 != null) {
				str8 = str8 + "/";
			}
		} catch (Exception localException1) {
		}
		if (null == str8) {
			str8 = "~/";
		}
		LoaderBox.SetProgressText(LanguageStrings.Get("loading_app_resources"));
		File localFile = null;
		Object localObject4;
		try {
			byte[] arrayOfByte1;
			if (!var_1f40) {
				if (var_1f30) {
					arrayOfByte1 = sub_3a29((String)var_1f28.get("browsercontrol_win_x86_jar"), 23312, str5);

					localFile = sub_26a2("browsercontrol.dll", false, str4, l, str8);
					localObject4 = new Class_u(arrayOfByte1).sub_ca1((byte)54, "browsercontrol.dll");
					if (null == localObject4) {
						localFile = null;
						DialogFactory.ShowError(LanguageStrings.Get("err_verify_bc"));
					}

					sub_3774((byte[])localObject4, false, localFile);
					if (debug) {
						System.out.println("dlldata : " + arrayOfByte1.length);
					}
				}
			} else {
				arrayOfByte1 = sub_3a29((String)var_1f28.get("browsercontrol_win_amd64_jar"), 23312, str5);
				localFile = sub_26a2("browsercontrol64.dll", false, str4, l, str8);

				localObject4 = new Class_u(arrayOfByte1).sub_ca1((byte)54, "browsercontrol64.dll");
				if (null == localObject4) {
					localFile = null;
					DialogFactory.ShowError(LanguageStrings.Get("err_verify_bc64"));
				}

				sub_3774((byte[])localObject4, false, localFile);
			}
		} catch (Exception localException2) {
			if (debug) {
				localException2.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_load_bc"));
		}
		LoaderBox.SetProgressText(LanguageStrings.Get("loading_app"));
		if (var_1f30) {
			Class_e.sub_ae5();
		}

		try {
			byte[] arrayOfByte2 = sub_3a29((String)var_1f28.get("loader_jar"), -1, str5);
			localObject4 = new Class_s(arrayOfByte2);
			var_1f20 = (Applet)((Class_s)localObject4).loadClass("loader").newInstance();
			if (debug) {
				System.out.println("loader_jar : " + arrayOfByte2.length);
			}
		} catch (Exception localException3) {
			if (debug) {
				localException3.printStackTrace();
			}
			DialogFactory.ShowError(LanguageStrings.Get("err_target_applet"));
		}
		LoaderBox.Hide();
		Class_i.sub_7d4(-12660);

		MainFrame.setTitle((String)var_1f28.get("title"));
		int i2 = (var_1f30) ? Integer.parseInt((String)var_1f28.get("advert_height")) : 0;

		int i3 = Integer.parseInt((String)var_1f28.get("window_preferredwidth"));

		int i4 = Integer.parseInt((String)var_1f28.get("window_preferredheight"));
		int i5 = 40;

		Insets localInsets = MainFrame.getInsets();
		MainFrame.setSize(i3 + (localInsets.left - -localInsets.right), i5 + localInsets.top + (i2 + i4) - -localInsets.bottom);
		MainFrame.setLocationRelativeTo(null);
		MainFrame.setVisible(true);
		var_1f50 = new ScrollPane();
		MainFrame.add(var_1f50);
		var_1f08 = new Panel();
		var_1f08.setBackground(Color.black);
		var_1f08.setLayout(null);
		var_1f50.add(var_1f08);

		int i6 = (!"yes".equals(Preferences.Get("Member"))) ? 1 : 0;
		i6 = 1;
		if ((var_1f30) && (i6 != 0)) {
			var_1f58 = new Canvas();
			var_1f08.add(var_1f58);
		}

		var_1f08.add(var_1f20);
		var_1f10 = new Class_a(LanguageStrings.Get("tandc"));
		var_1f08.add(var_1f10);
		MainFrame.doLayout();
		sub_3809(-1);
		var_1f50.doLayout();
		if (var_1f30) if (i6 != 0) {
			do {
				while (true) {
					if ((var_1f58.isDisplayable()) && (var_1f58.isShowing())) {
						break; //break label1817;
					}
					try {
						Thread.sleep(100L);
					} catch (Exception localException4) {
					}
				}
			} while (!bool);
			try {
				label1817:
				System.load(localFile.toString());
				browsercontrol.create(var_1f58, (String)var_1f28.get("adverturl"));
				browsercontrol.resize(var_1f58.getSize().width, var_1f58.getSize().height);
			} catch (Throwable localThrowable) {
				if (debug) {
					localThrowable.printStackTrace();
				}
				DialogFactory.ShowError(LanguageStrings.Get("err_create_advertising"));
				return;
			}
		}

		MainFrame.addWindowListener(MainWindowAdapter.GetInstance());
		var_1f50.addComponentListener(new appletviewer());
		var_1f20.setStub(new Class_g());
		var_1f20.init();
		var_1f20.start();
	}

  public static void removeadvert() {
    if (var_1f58 == null)
      return;
    if (browsercontrol.iscreated()) {
      browsercontrol.destroy();
    }
    var_1f08.remove(var_1f58);
    var_1f58 = null;
    sub_3809(2);
  }

  private static final String sub_34ed(int paramInt, String paramString)
  {
    boolean bool = Preferences.dummy; if (paramInt != -1) {
      return (String)null;
    }

    String str1 = paramString;
    do
    {
      int i = str1.indexOf("$(");
      if ((0 > i) && 
        (!bool)) {
        break;
      }

      int j = str1.indexOf(":", i);
      int k = str1.indexOf(")", j);
      if (((j ^ 0xFFFFFFFF) > -1) || (-1 < (k ^ 0xFFFFFFFF)))
      {
        break;
      }

      String str2 = str1.substring(2 + i, j);

      Object localObject = str1.substring(j + 1, k);

      String str3 = Preferences.Get(str2);
      if (str3 != null)
      {
        localObject = str3;
      }
      if (k < -1 + str1.length()) {
        str1 = str1.substring(0, i) + (String)localObject + str1.substring(k - -1); if (!bool) continue;
      }
      str1 = str1.substring(0, i) + (String)localObject;
    }
    while (!bool);

    return (String)str1;
  }

  public static void readdadvert() {
    if ((!var_1f30) || (var_1f58 != null))
      return;
    var_1f58 = new Canvas();
    var_1f08.add(var_1f58);
    sub_3809(2);

    while ((!var_1f58.isDisplayable()) || (!var_1f58.isShowing()))
    {
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception localException) {
      }
    }
    try {
      browsercontrol.create(var_1f58, (String)var_1f28.get("adverturl"));
      browsercontrol.resize(var_1f58.getSize().width, var_1f58.getSize().height);
    } catch (Throwable localThrowable) {
      if (debug) {
        localThrowable.printStackTrace();
      }
      DialogFactory.ShowError(LanguageStrings.Get("err_create_advertising"));
      return;
    }
  }

	static final void Terminate() {
		if (browsercontrol.iscreated()) {
			browsercontrol.destroy();
		}
		System.exit(0);
	}

  private static final boolean sub_3774(byte[] paramArrayOfByte, boolean paramBoolean, File paramFile) {
    try {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      localFileOutputStream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
      if (paramBoolean) {
        var_1f50 = (ScrollPane)null;
      }
      localFileOutputStream.close();
      return true;
    } catch (IOException localIOException) {
      if (debug)
      {
        localIOException.printStackTrace();
      }
      DialogFactory.ShowError(LanguageStrings.Get("err_save_file"));
    }return false;
  }

  private static final void sub_3809(int paramInt)
  {
    int i = (var_1f58 == null) ? 0 : Integer.parseInt((String)var_1f28.get("advert_height"));

    int j = 40;

    int k = Integer.parseInt((String)var_1f28.get("applet_minwidth"));

    int l = Integer.parseInt((String)var_1f28.get("applet_minheight"));

    int i1 = Integer.parseInt((String)var_1f28.get("applet_maxwidth"));
    int i2 = Integer.parseInt((String)var_1f28.get("applet_maxheight"));

    Dimension localDimension = var_1f50.getSize();

    Insets localInsets = var_1f50.getInsets();

    int i3 = -localInsets.right + (localDimension.width + -localInsets.left);

    int i4 = -localInsets.bottom + -localInsets.top + localDimension.height;

    int i5 = i3;
    if (i5 < k)
    {
      i5 = k;
    }
    int i6 = -i + i4 - j;
    if (i6 < l)
    {
      i6 = l;
    }
    if ((i5 ^ 0xFFFFFFFF) < (i1 ^ 0xFFFFFFFF))
    {
      i5 = i1;
    }
    if (i6 > i2) {
      i6 = i2;
    }

    int i7 = i3;

    int i8 = i4;
    if ((k ^ 0xFFFFFFFF) < (i7 ^ 0xFFFFFFFF)) {
      i7 = k;
    }
    if (i8 < j + l + i)
    {
      i8 = j + (l + i);
    }
    var_1f08.setSize(i7, i8);
    if (var_1f58 != null)
    {
      var_1f58.setBounds((i7 + -i5) / 2, 0, i5, i);
    }
    var_1f20.setBounds((-i5 + i7) / 2, i, i5, i6);
    var_1f10.setBounds((i7 - i5) / paramInt, i + i6, i5, j);
    if ((var_1f58 == null) || (!browsercontrol.iscreated()))
      return;
    browsercontrol.resize(var_1f58.getSize().width, var_1f58.getSize().height);
  }

  private static final byte[] sub_3a29(String paramString1, int paramInt, String paramString2)
  {
    boolean bool = Preferences.dummy; if (paramInt != 23312) {
      var_1f78 = 0.7462825F;
    }

    byte[] arrayOfByte1 = new byte[300000];

    int i = 0;
    try
    {
      InputStream localInputStream = new URL(paramString2 + paramString1).openStream();
		do {
			if (arrayOfByte1.length <= i) {
				break;
			}
			int j = localInputStream.read(arrayOfByte1, i, arrayOfByte1.length + -i);
			if ((-1 < (j ^ 0xFFFFFFFF)) && (!bool))
				break;
			var_1f78 += j;
			i += j;
			LoaderBox.SetProgressPercent((int)(var_1f78 / var_1f70 * 100.0F));
		} while (!bool);

      localInputStream.close();
    } catch (Exception localException) {
      if (debug)
      {
        localException.printStackTrace();
      }
      DialogFactory.ShowError(LanguageStrings.Get("err_downloading") + ": " + paramString1);
    }

    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    return arrayOfByte2;
  }

  static
  {
    var_1f70 = 58988.0F;

    var_1f68 = null;

    var_1f80 = null;
  }
}

/* Location:           C:\Windows\.jagex_cache_32\jagexlauncher\bin\jagexappletviewer\
 * Qualified Name:     app.appletviewer
 * JD-Core Version:    0.5.4
 */