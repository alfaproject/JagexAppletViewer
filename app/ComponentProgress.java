package app;

import java.awt.*;

class ComponentProgress
	extends Component
{
	private static final long serialVersionUID = 637854253935208914L;
	private static final Color _colour = new Color(140, 11, 1);

	private int _value = 0;
	private int _maxValue = 100;
	private String _text = "Loading...";

	public ComponentProgress()
	{
		this.setFont(new Font("Helvetica", Font.BOLD, 13));
	}

	public void setValue(int value)
	{
		_value = (value > _maxValue ? _maxValue : value);
		this.repaint();
	}

	public int getValue()
	{
		return _value;
	}

	public void setValue(int value, int maxValue)
	{
		_maxValue = maxValue;
		setValue(value);
	}

	public void setText(String text)
	{
		_text = text;
		this.repaint();
	}

	@Override
	public final void paint(Graphics g)
	{
		if (g == null) {
			repaint();
		} else {
			try {
				// get percentage
				int percent = (int) ((double) _value / (double) _maxValue * 100D);

				// get size
				Dimension size = getSize();

				// fill background
				g.setColor(Color.black);
				g.fillRect(0, 0, size.width, size.height);

				// fill bar
				g.setColor(_colour);
				g.drawRect(size.width / 2 - 152, size.height / 2 - 18, 303, 33);
				if (_value > 0) {
					g.fillRect(size.width / 2 - 150, size.height / 2 - 16, percent * 303 / 100 - 3, 30);
				}

				// draw text
				if (_text != null) {
					String text = _text + " - " + percent + "%";
					g.setColor(Color.white);
					g.drawString(text, (size.width - getFontMetrics(getFont()).stringWidth(text)) / 2, size.height / 2 + 4);
				}
			} catch (Exception ex) {
                if (appletviewer.debug) {
                    ex.printStackTrace();
                }
			}
		}
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_o JD-Core Version: 0.5.4
 */