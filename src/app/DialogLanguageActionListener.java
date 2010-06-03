package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class DialogLanguageActionListener
		implements ActionListener
{

	public final void actionPerformed(ActionEvent paramActionEvent) {
		if (!paramActionEvent.getActionCommand().equals("ok")) {
			DialogLanguage.choiceIndex = -1;
			DialogLanguage.dialog.setVisible(false);
		}
		DialogLanguage.choiceIndex = DialogLanguage.choice.getSelectedIndex();
		DialogLanguage.dialog.setVisible(false);
  }
}