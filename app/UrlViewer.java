package app;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;

final class UrlViewer
		extends WindowAdapter
		implements ActionListener
{
	private Dialog _dialog;

	UrlViewer(String url)
	{
		TextArea textAreaUrl = new TextArea();
		textAreaUrl.setText(Language.getText("copy_paste_url") + ":\n" + url);
		textAreaUrl.setEditable(false);

		Button buttonOk = new Button(Language.getText("ok"));
		buttonOk.addActionListener(this);

		Panel panelButtons = new Panel();
		panelButtons.setLayout(new FlowLayout(1));
		panelButtons.add(buttonOk);

		_dialog = new Dialog(appletviewer.Window, Language.getText("information"), true);
		_dialog.add(textAreaUrl);
		_dialog.add(panelButtons, "South");
		_dialog.addWindowListener(this);
		_dialog.setResizable(false);
		_dialog.setSize(400, 150);
		_dialog.setLocationRelativeTo(appletviewer.Window);
		_dialog.setVisible(true);
	}

	@Override
	public final void actionPerformed(ActionEvent e)
	{
		_dialog.dispose();
	}

	@Override
	public final void windowClosing(WindowEvent e)
	{
		_dialog.dispose();
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
				new UrlViewer(url);
			}
		}
	}
}