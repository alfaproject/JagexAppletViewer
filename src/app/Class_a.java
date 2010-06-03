package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.StringTokenizer;

final class Class_a extends Component
  implements MouseListener, MouseMotionListener
{
  private static Color var_81d = new Color(12410);
  private Class_l[][] var_825;
  private static Color var_82d = new Color(16777215);
  private Class_c[] var_835;
  private static Color var_83d = new Color(16765440);

  public final void mouseDragged(MouseEvent paramMouseEvent)
  {
  }

  public final void mousePressed(MouseEvent paramMouseEvent)
  {
    boolean bool = Preferences.dummy; Point localPoint = paramMouseEvent.getPoint();

    int i = 0;
    do { if (i >= this.var_835.length)
        return;
      if (this.var_835[i].var_105.contains(localPoint))
      {
        Class_i.showurl(this.var_835[i].var_10d, null);
      }
      ++i; } while (!bool);
  }

  public final void paint(Graphics paramGraphics)
  {
    boolean bool = Preferences.dummy; int i = getWidth();

    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int j = localFontMetrics.getHeight();

    int k = j;
    if (null == this.var_825)
    {
      return;
    }

    int l = 0;
    do { if ((l ^ 0xFFFFFFFF) <= (this.var_825.length ^ 0xFFFFFFFF)) {
        return;
      }
      Class_l[] arrayOfClass_l = this.var_825[l];

      int i1 = 0;

      int i2 = 0;
      do { if (arrayOfClass_l.length <= i2) break;
        i1 += localFontMetrics.stringWidth(arrayOfClass_l[i2].var_122);

        ++i2; } while (!bool);

      i2 = (-i1 + i) / 2;

		for (int i3 = 0; i3 < arrayOfClass_l.length; i3++) {
			Class_l localClass_l = arrayOfClass_l[i3];

			int i4 = localFontMetrics.stringWidth(localClass_l.var_122);
			Class_c localClass_c = localClass_l.var_11a;
			if (localClass_c != null) {
				paramGraphics.setColor(var_83d);
				Rectangle localRectangle = localClass_c.var_105;
				localRectangle.y = (k - j);
				localRectangle.height = j;
				localRectangle.width = i4;
				localRectangle.x = i2;
			} else {
				paramGraphics.setColor(var_82d);
			}

			paramGraphics.drawString(localClass_l.var_122, i2, k);
			i2 += i4;
		}

      k += j;

      ++l; } while (!bool);
  }

  public final void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public final void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  private final void sub_af7(int paramInt, String paramString)
  {
    boolean bool = Preferences.dummy; if (paramString == null)
    {
      return;
    }

    if (paramInt != -1) {
      mouseDragged((MouseEvent)null);
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "\\");
    this.var_825 = new Class_l[localStringTokenizer.countTokens()][];

    Class_l[] arrayOfClass_l = new Class_l[100];

    int i = 0;
    do { if (!localStringTokenizer.hasMoreTokens()) {
        return;
      }

      int j = 0;
      String str1 = localStringTokenizer.nextToken();
      do
      {
        int k = str1.indexOf('[');
        if (k < 0)
        {
          arrayOfClass_l[(j++)] = new Class_l(str1);
          if (!bool)
            break;
        }
        if (-1 > (k ^ 0xFFFFFFFF))
        {
          arrayOfClass_l[(j++)] = new Class_l(str1.substring(0, k));
          str1 = str1.substring(k);
        }

        int l = str1.indexOf('"');
        if (l < 0)
        {
          break;
        }

        int i1 = str1.indexOf('"', 1 + l);
        if (-1 < (i1 ^ 0xFFFFFFFF))
        {
          break;
        }

        int i2 = str1.indexOf(']');
        if (0 > i2)
        {
          break;
        }

        String str2 = str1.substring(1 + l, i1);
        String str3 = str1.substring(1 + i1, i2).trim();
        arrayOfClass_l[(j++)] = new Class_l(str3, str2);
        if (1 + i2 >= str1.length()) {
          break;
        }

        str1 = str1.substring(1 + i2);
      }while (!bool);

      this.var_825[i] = new Class_l[j];
      System.arraycopy(arrayOfClass_l, 0, this.var_825[i], 0, j);

      ++i; } while (!bool);
  }

  public final void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public final void mouseExited(MouseEvent paramMouseEvent)
  {
  }

  public final void mouseMoved(MouseEvent paramMouseEvent)
  {
    boolean bool = Preferences.dummy; Point localPoint = paramMouseEvent.getPoint();

    int i = 0;
    do { if ((this.var_835.length ^ 0xFFFFFFFF) >= (i ^ 0xFFFFFFFF))
        break;
      if (this.var_835[i].var_105.contains(localPoint))
      {
        setCursor(Cursor.getPredefinedCursor(12));
        return;
      }
      ++i; } while (!bool);

    setCursor(Cursor.getPredefinedCursor(0));
  }

  Class_a(String paramString)
  {
    this.var_825 = ((Class_l[][])null);

    setBackground(var_81d);
    addMouseListener(this);
    addMouseMotionListener(this);
    sub_af7(-1, paramString);
    if (this.var_825 == null) {
      return;
    }

		int i = 0;
		for (int j = 0; j < this.var_825.length; j++ ) {
			for (int l = 0; l < this.var_825[j].length; l++) {
				if (this.var_825[j][l].var_11a != null) {
					i++;
				}
			}
		}
		this.var_835 = new Class_c[i];

		int j = 0;
		for (int k = 0; k < this.var_825.length; k++) {
			for (int i1 = 0; i1 < this.var_825[k].length; i1++) {
				if (null != this.var_825[k][i1].var_11a) {
					this.var_835[j++] = this.var_825[k][i1].var_11a;
				}
			}
		}
	}

} //class Class_a