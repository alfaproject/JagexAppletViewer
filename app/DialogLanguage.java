package app;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

class DialogLanguage
	extends Dialog
{

	private Map<Integer, String> _languages;
	private Integer _savedLangId;

	private Choice _choiceLanguage;

	public DialogLanguage(Window owner, Map<Integer, String> languages)
	{
		super(owner, Language.getText("language"), Dialog.DEFAULT_MODALITY_TYPE);
		_languages = languages;
		
		_choiceLanguage = new Choice();
		for (String languageName : _languages.values()) {
			_choiceLanguage.add(languageName);
		}

		// get current language and select it
		try {
			_savedLangId = Integer.valueOf(Preferences.get("Language"));
		} catch (NumberFormatException ex) {
			_savedLangId = 0;
		}
		_choiceLanguage.select(_languages.get(_savedLangId));

		// ok button
		Button buttonOk = new Button(Language.getText("ok"));
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveLanguage();
				close();
			}
		});

		// cancel button
		Button buttonCancel = new Button(Language.getText("cancel"));
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		// buttons panel
		Panel panelButtons = new Panel();
		panelButtons.setLayout(new GridLayout(1, 2));
		panelButtons.add(buttonOk);
		panelButtons.add(buttonCancel);

		// dialog
		addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				close();
			}
		});

		add(_choiceLanguage, BorderLayout.CENTER);
		add(panelButtons, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(owner);
		setVisible(true);
	}

	private void close()
	{
		setVisible(false);
		dispose();
	}

	private void saveLanguage()
	{
		Integer selectedLangId = 0;
		for (Integer langId : _languages.keySet()) {
			if (_languages.get(langId).equals(_choiceLanguage.getSelectedItem())) {
				selectedLangId = langId;
				break;
			}
		}

		// save the new language id and warn about restarting the application
		if (!selectedLangId.equals(_savedLangId)) {
			Preferences.set("Language", selectedLangId.toString());
			Preferences.save();
			DialogMessage.showMessage(this, Language.getText("changes_on_restart"));
		}
	}
}