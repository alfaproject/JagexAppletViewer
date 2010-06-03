package app;

import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.OutputStream;
import java.io.PrintStream;

final class Class_q extends OutputStream
  implements WindowListener
{
  private PrintStream var_507;
  private static Class_q var_50f;
  private StringBuffer var_517 = new StringBuffer(1024);
  private Frame var_51f;
  private String var_527;
  private TextArea var_52f;
  private boolean var_537 = false;

  public final void windowDeactivated(WindowEvent paramWindowEvent)
  {
  }

  public final void windowOpened(WindowEvent paramWindowEvent)
  {
  }

  public final void windowIconified(WindowEvent paramWindowEvent)
  {
  }

  public final void windowDeiconified(WindowEvent paramWindowEvent)
  {
  }

  static final PrintStream sub_5f3(String paramString, boolean paramBoolean)
  {
    if (paramBoolean != true) {
      return (PrintStream)null;
    }
    if (var_50f == null)
    {
      var_50f = new Class_q(paramString);
    }

    return var_50f.var_507;
  }
  public final void windowClosing(WindowEvent paramWindowEvent) {
    this.var_51f.setVisible(false);
    this.var_537 = false;
  }
  public final void windowClosed(WindowEvent paramWindowEvent) {
  }

  public final void windowActivated(WindowEvent paramWindowEvent) {
  }

	public final void write(int paramInt) {
		synchronized (this) {
			if ((paramInt ^ 0xFFFFFFFF) != -11) {
				this.var_517.append(String.valueOf((char)paramInt));
			}
			if (this.var_537 != true) {
				this.var_51f = new Frame();
				this.var_51f.add(this.var_52f, "Center");
				this.var_51f.setVisible(true);
				this.var_51f.setTitle(this.var_527);
				this.var_51f.setLocation(320, 240);
				this.var_51f.setSize(720, 260);
				this.var_51f.addWindowListener(this);
				this.var_537 = true;
			}

			this.var_517.append("\n");
			this.var_52f.append(this.var_517.toString());

			this.var_517 = new StringBuffer(1024);
		}
	}

  private Class_q(String paramString)
  {
    this.var_527 = paramString;
    this.var_52f = new TextArea();
    this.var_52f.setEditable(false);
    this.var_507 = new PrintStream(this, true);
  }
}

/* Location:           C:\Windows\.jagex_cache_32\jagexlauncher\bin\jagexappletviewer\
 * Qualified Name:     app.Class_q
 * JD-Core Version:    0.5.4
 */