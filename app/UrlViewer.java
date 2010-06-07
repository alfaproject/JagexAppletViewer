package app;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class UrlViewer
		extends WindowAdapter
		implements ActionListener
{
	private Dialog _dialog;

	@Override
	public final void actionPerformed(ActionEvent paramActionEvent)
	{
		_dialog.setVisible(false);
	}

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
	public final void windowClosing(WindowEvent e)
	{
		_dialog.setVisible(false);
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_p JD-Core Version: 0.5.4
 */