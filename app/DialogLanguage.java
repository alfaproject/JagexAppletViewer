package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

final class DialogLanguage
	extends Dialog
	implements ActionListener
{

	private Map<Integer, String> _languages;
	private Integer _savedLangId;

	private Choice _choiceLanguage;

	public DialogLanguage(Frame owner, Map<Integer, String> languages)
	{
		super(owner, Language.getText("language"), true);
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

		Button buttonOk = new Button(Language.getText("ok"));
		buttonOk.setActionCommand("ok");
		buttonOk.addActionListener(this);

		Button buttonCancel = new Button(Language.getText("cancel"));
		buttonCancel.addActionListener(this);

		Panel panelButtons = new Panel();
		panelButtons.setLayout(new GridLayout(1, 2));
		panelButtons.add(buttonOk);
		panelButtons.add(buttonCancel);

		this.add(_choiceLanguage, BorderLayout.CENTER);
		this.add(panelButtons, BorderLayout.SOUTH);
		this.setSize(200, 150);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("ok")) {
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
		this.setVisible(false);
		this.dispose();
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_j JD-Core Version: 0.5.4
 */