package app;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;

final class DialogLanguage {

	static Dialog dialog;
	private static ActionListener _actionListener = new DialogLanguageActionListener();
	static int choiceIndex;
	static Choice choice;

	static final int GetChoiceIndex() {
		dialog.setLocationRelativeTo(appletviewer.MainFrame);
		dialog.setVisible(true);
		return choiceIndex;
	}

	static final void Create() {
		// languages choice
		choice = new Choice();
		for (int i = 0; i < appletviewer.languageNames.length; i++) {
			String languageName = appletviewer.languageNames[i];
			choice.add(languageName);
		}

		// ok button
		Button buttonOk = new Button(LanguageStrings.Get("ok"));
		buttonOk.setActionCommand("ok");
		buttonOk.addActionListener(_actionListener);

		// cancel button
		Button buttonCancel = new Button(LanguageStrings.Get("cancel"));
		buttonCancel.setActionCommand("cancel");
		buttonCancel.addActionListener(_actionListener);

		// buttons panel
		Panel panelButtons = new Panel();
		panelButtons.setLayout(new GridLayout(1, 2));
		panelButtons.add(buttonOk);
		panelButtons.add(buttonCancel);

		// main panel
		Panel mainPanel = new Panel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(choice, "Center");
		mainPanel.add(panelButtons, "South");

		// dialog
		dialog = new Dialog(appletviewer.MainFrame, LanguageStrings.Get("language"), true);
		dialog.setSize(200, 150);
		dialog.add(mainPanel);
	}

} //class DialogLanguage