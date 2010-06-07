package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class TerminateEventHandler
		extends WindowAdapter
		implements ActionListener
{
	private static final TerminateEventHandler INSTANCE = new TerminateEventHandler();

	private TerminateEventHandler() {
		// prevent direct instantiation
	}

	public static TerminateEventHandler getInstance()
	{
		return INSTANCE;
	}

	@Override
	public final void actionPerformed(ActionEvent e)
	{
		appletviewer.terminate();
	}

	@Override
	public final void windowClosing(WindowEvent e)
	{
		appletviewer.terminate();
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_b JD-Core Version: 0.5.4
 */