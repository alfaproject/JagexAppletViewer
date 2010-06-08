package app;

import java.awt.*;
import java.awt.event.*;

class DialogProgress
	extends Dialog
	implements WindowListener
{

	private ComponentProgress _progress;

	public DialogProgress(Window owner, String title)
	{
		super(owner, title);

		_progress = new ComponentProgress();
		this.add(_progress);

		this.addWindowListener(this);
		this.setResizable(false);
		this.setSize(320, 100);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);
	}

	public void setText(String text)
	{
		_progress.setText(text);
	}

	public int getValue()
	{
		return _progress.getValue();
	}

	public void setValue(int value)
	{
		_progress.setValue(value);
	}

	public void setValue(int value, int maxValue)
	{
		_progress.setValue(value, maxValue);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		appletviewer.terminate();
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