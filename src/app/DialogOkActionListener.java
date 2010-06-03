package app;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class DialogOkActionListener
		implements ActionListener
{

	private Dialog _dialog;

	public DialogOkActionListener(Dialog dialog) {
		_dialog = dialog;
	}

	public final void actionPerformed(ActionEvent actionEvent) {
		_dialog.setVisible(false);
	}

} //class DialogOkActionListener