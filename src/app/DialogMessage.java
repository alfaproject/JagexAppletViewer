package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class DialogMessage
	extends Dialog
	implements ActionListener
{

	public DialogMessage(Window owner, String title, String message, String buttonText, String buttonAction)
	{
		super(owner, title);
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		Label label = new Label(message, 1);

		Button button = new Button(buttonText);
		button.setActionCommand(buttonAction);
		button.addActionListener(this);
		
		Panel buttonsPanel = new Panel();
		buttonsPanel.setLayout(new FlowLayout(1));
		buttonsPanel.add(button);

		this.add(label);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.setSize(500, 100);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);
	}

	public static void showMessage(Window owner, String message)
	{
	    new DialogMessage(owner, Language.getText("message"), message, Language.getText("ok"), "ok");
	}

	public static void showError(Window owner, String message)
	{
		appletviewer.progressDialog.dispose();
		new DialogMessage(owner, Language.getText("error"), message, Language.getText("quit"), "quit");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("ok")) {
			this.dispose();
		} else if (e.getActionCommand().equals("quit")) {
			appletviewer.terminate();
		}
	}

}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_d JD-Core Version: 0.5.4
 */