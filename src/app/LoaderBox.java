package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

final class LoaderBox
		extends Component
{

	private static LoaderBox _progressBar;
	private Color _progressColour = new Color(140, 11, 1);
	private String _progressText = "Loading...";
	private int _progressPercent = 0;

	private static Dialog _dialog;
	private FontMetrics _fontMetrics;
	private Font _font;

	private LoaderBox(String progressText) {
		_progressText = progressText;
	}

	static final void SetProgressPercent(int progressPercent) {
		if (_progressBar == null) {
			return;
		}
		_progressBar._progressPercent = (progressPercent > 100 ? 100 : progressPercent);
		_progressBar.repaint();
	}

	static final void SetProgressText(String progressText) {
		if (_progressBar == null) {
			return;
		}
		_progressBar._progressText = progressText;
		_progressBar.repaint();
	}

	static final void Create() {
		_progressBar = new LoaderBox(LanguageStrings.Get("loaderbox_initial"));
		_progressBar._font = new Font("Helvetica", 1, 13);
		_progressBar._fontMetrics = _progressBar.getFontMetrics(_progressBar._font);

		_dialog = new Dialog(appletviewer.window, "Jagex Ltd.", false);
		_dialog.add(_progressBar);
		_dialog.addWindowListener(MainWindowAdapter.GetInstance());
		_dialog.setResizable(false);
		_dialog.setSize(320, 100);
		_dialog.setLocationRelativeTo(appletviewer.window);
		_dialog.setVisible(true);
	}

	static final void Hide() {
		if (_dialog != null) {
			_dialog.setVisible(false);
		}
	}

	@Override public final void paint(Graphics graphics) {
		try {
			if (graphics == null) {
				repaint();
			}

			// fill background
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, getSize().width, getSize().height);

			// fill progress bar
			graphics.setColor(_progressColour);
			graphics.drawRect(-152 + getSize().width / 2, -18 + getSize().height / 2, 303, 33);
			graphics.fillRect(-152 + (getSize().width / 2 - -2), 2 + (-18 + getSize().height / 2), -3 + _progressPercent * 303 / 100, 30);

			// draw progress bar text
			String str = _progressText + " - " + _progressPercent + "%";
			graphics.setFont(_font);
			graphics.setColor(Color.white);
			graphics.drawString(str, (getSize().width + -_fontMetrics.stringWidth(str)) / 2, getSize().height / 2 - -4);
		}
		catch (Exception ex) {
		}
	}

} //class LoaderBox