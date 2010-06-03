package app;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;

final class DialogFactory {
  
	private static Dialog _dialog;
	private static Button _button;

	static final void ShowOk(String text) {
		makeDialog(text, LanguageStrings.Get("ok"), LanguageStrings.Get("message"));
		_button.addActionListener(new DialogOkActionListener(_dialog));
		_dialog.setVisible(true);
	}

	static final void ShowError(String text) {
		LoaderBox.Hide();
		makeDialog(text, LanguageStrings.Get("quit"), LanguageStrings.Get("error"));
		_dialog.addWindowListener(MainWindowAdapter.GetInstance());
		_button.addActionListener(MainWindowAdapter.GetInstance());
		_dialog.setVisible(true);
	}

	private static final void makeDialog(String text, String buttonText, String title) {
		_dialog = new Dialog(appletviewer.MainFrame, title, true);
		_dialog.add(new Label(text, 1));

		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(1));

		_button = new Button(buttonText);
		panel.add(_button);

		_dialog.add(panel, "South");
		_dialog.setResizable(false);
		_dialog.setSize(500, 100);
		_dialog.setLocationRelativeTo(appletviewer.MainFrame);
	}

} //class DialogFactory