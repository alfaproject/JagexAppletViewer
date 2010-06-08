package app;

import java.awt.*;
import java.awt.event.*;

final class DialogUrl
		extends Dialog
		implements WindowListener, ActionListener
{

	DialogUrl(String url)
	{
		super(appletviewer.frame, Language.getText("information"), true);

		String message = Language.getText("copy_paste_url") + ":\n" + url;
		TextArea textAreaUrl = new TextArea(message, 2, 50, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		textAreaUrl.setEditable(false);

		Button buttonOk = new Button(Language.getText("ok"));
		buttonOk.addActionListener(this);

		Panel panelButtons = new Panel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelButtons.add(buttonOk);

		this.add(textAreaUrl);
		this.add(panelButtons, BorderLayout.SOUTH);
		this.addWindowListener(this);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(appletviewer.frame);
		this.setVisible(true);
	}

	@Override
	public final void actionPerformed(ActionEvent e)
	{
		dispose();
	}

	@Override
	public final void windowClosing(WindowEvent e)
	{
		dispose();
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
}