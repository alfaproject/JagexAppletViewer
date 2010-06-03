package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class MainWindowAdapter
		extends WindowAdapter
		implements ActionListener
{

	private static MainWindowAdapter _instance;

	static final MainWindowAdapter GetInstance() {
		if (_instance == null) {
			_instance = new MainWindowAdapter();
		}
		return _instance;
	}

	public final void actionPerformed(ActionEvent actionEvent) {
		appletviewer.Terminate();
	}

	public final void windowClosing(WindowEvent windowEvent) {
		appletviewer.Terminate();
	}

} //class MainWindowAdapter