package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

final class ProgressComponent
		extends Component
{
	private static final long serialVersionUID = 637854253935208914L;

	private static ProgressComponent _instance;
	private int _progressPercent = 0;
	private Color _progressColour = new Color(140, 11, 1);
	private String _progressText = "Loading...";

	private static Dialog _dialog;
	private FontMetrics _fontMetrics;
	private Font _font;

	public static void setPercent(int percent)
	{
		if (_instance != null) {
			_instance._progressPercent = (percent > 100 ? 100 : percent);
			_instance.repaint();
		}
	}

	public static void setText(String text)
	{
		if (_instance != null) {
			_instance._progressText = text;
			_instance.repaint();
		}
	}

	@Override
	public final void paint(Graphics g)
	{
		if (g == null) {
			repaint();
		} else {
			try {
				// get size
				Dimension size = getSize();

				// fill background
				g.setColor(Color.black);
				g.fillRect(0, 0, size.width, size.height);

				// fill bar
				g.setColor(_progressColour);
				g.drawRect(size.width / 2 - 152, size.height / 2 - 18, 303, 33);
				if (_progressPercent > 0) {
					g.fillRect(size.width / 2 - 150, size.height / 2 - 16, _progressPercent * 303 / 100 - 3, 30);
				}

				// draw text
				String text = _progressText + " - " + _progressPercent + "%";
				g.setFont(this._font);
				g.setColor(Color.white);
				g.drawString(text, (size.width - _fontMetrics.stringWidth(text)) / 2, size.height / 2 + 4);
			} catch (Exception ex) {
                if (appletviewer.Debug) {
                    ex.printStackTrace();
                }
			}
		}
	}

	public static void hideDialog()
	{
		if (_dialog != null)
			_dialog.setVisible(false);
	}

	public static void create()
	{
		_instance = new ProgressComponent(Language.getText("loaderbox_initial"));
		_instance._font = new Font("Helvetica", 1, 13);
		_instance._fontMetrics = _instance.getFontMetrics(_instance._font);

		_dialog = new Dialog(appletviewer.Window, "Jagex Ltd.", false);
		_dialog.add(_instance);
		_dialog.addWindowListener(TerminateEventHandler.getInstance());
		_dialog.setResizable(false);
		_dialog.setSize(320, 100);
		_dialog.setLocationRelativeTo(appletviewer.Window);
		_dialog.setVisible(true);
	}

	private ProgressComponent(String text)
	{
		_progressText = text;
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_o JD-Core Version: 0.5.4
 */