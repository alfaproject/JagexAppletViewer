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

final class DialogCopyPasteUrl
		extends WindowAdapter
		implements ActionListener
{

	private Dialog _dialog;

	DialogCopyPasteUrl(String url) {
		// copy following url text area
		TextArea textArea = new TextArea();
		textArea.setText(LanguageStrings.Get("copy_paste_url") + ":\n" + url);
		textArea.setEditable(false);

		// ok button
		Button buttonOk = new Button(LanguageStrings.Get("ok"));
		buttonOk.addActionListener(this);

		// buttons panel
		Panel panelButtons = new Panel();
		panelButtons.setLayout(new FlowLayout(1));
		panelButtons.add(buttonOk);

		// information dialog
		_dialog = new Dialog(appletviewer.MainFrame, LanguageStrings.Get("information"), true);
		_dialog.add(textArea);
		_dialog.add(panelButtons, "South");
		_dialog.addWindowListener(this);
		_dialog.setResizable(false);
		_dialog.setSize(400, 150);
		_dialog.setLocationRelativeTo(appletviewer.MainFrame);
		_dialog.setVisible(true);
	}

	public final void actionPerformed(ActionEvent paramActionEvent) {
		_dialog.setVisible(false);
	}

	@Override public final void windowClosing(WindowEvent paramWindowEvent) {
		_dialog.setVisible(false);
	}

} //class Class_p