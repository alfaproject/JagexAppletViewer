package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.StringTokenizer;

final class CopyrightBar
		extends Component
		implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 3368879434278379489L;

	private CopyrightText[][] _lines;
	private CopyrightLink[] _links;

	private static Color backColour = new Color(0x00307A);
	private static Color textColour = new Color(0xFFFFFF);
	private static Color linkColour = new Color(0xFFD200);

	@Override
	public final void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public final void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		for (CopyrightLink link : _links) {
			if (link.hotspot.contains(point)) {
				appletviewer.showUrl(link.href, null);
				return;
			}
		}
	}

	@Override
	public final void paint(Graphics g)
	{
		if (_lines == null) {
			return;
		}

		int width = getWidth();

		FontMetrics fontMetrics = g.getFontMetrics();
		int textHeight = fontMetrics.getHeight();

		int k = textHeight;
		for (CopyrightText[] tokens : _lines) {
			int textWidth = 0;
			for (CopyrightText token : tokens) {
				textWidth += fontMetrics.stringWidth(token.text);
			}

			int left = (width - textWidth) / 2;

			for (CopyrightText token : tokens) {
				int tokenWidth = fontMetrics.stringWidth(token.text);
				CopyrightLink tokenLink = token.link;
				if (tokenLink != null) {
					g.setColor(linkColour);
					Rectangle tokenHotspot = tokenLink.hotspot;
					tokenHotspot.x = left;
					tokenHotspot.y = k - textHeight;
					tokenHotspot.height = textHeight;
					tokenHotspot.width = tokenWidth;
				} else {
					g.setColor(textColour);
				}

				g.drawString(token.text, left, k);
				left += tokenWidth;
			}

			k += textHeight;
		}
	}

	@Override
	public final void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public final void mouseReleased(MouseEvent e)
	{
	}

	private void parseMessage(String message)
	{
		if (message == null) {
			return;
		}

		StringTokenizer messageTokenizer = new StringTokenizer(message, "\\");
		_lines = new CopyrightText[messageTokenizer.countTokens()][];

		CopyrightText[] tokens = new CopyrightText[100];

		for (int i = 0; messageTokenizer.hasMoreTokens(); i++) {
			String messageLine = messageTokenizer.nextToken();
			int j = 0, urlStart;
			do {
				urlStart = messageLine.indexOf('[');
				if (urlStart == -1) {
					// no link
					tokens[j++] = new CopyrightText(messageLine);
				} else {
					// there is a link
					tokens[j++] = new CopyrightText(messageLine.substring(0, urlStart));

					// get href
					messageLine = messageLine.substring(urlStart);

					int hrefStart = messageLine.indexOf('"');
					if (hrefStart == -1) {
						break;
					}
					int hrefEnd = messageLine.indexOf('"', hrefStart + 1);
					if (hrefEnd == -1) {
						break;
					}

					// get text
					int urlEnd = messageLine.indexOf(']');
					if (urlEnd == -1) {
						break;
					}

					String href = messageLine.substring(hrefStart + 1, hrefEnd);
					String text = messageLine.substring(hrefEnd + 1, urlEnd).trim();
					tokens[j++] = new CopyrightText(text, href);

					if (urlEnd + 1 < messageLine.length()) {
						messageLine = messageLine.substring(urlEnd + 1);
					}
				}
			} while (urlStart != -1);

			_lines[i] = new CopyrightText[j];
			System.arraycopy(tokens, 0, _lines[i], 0, j);
		}
	}

	@Override
	public final void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public final void mouseExited(MouseEvent e)
	{
	}

	@Override
	public final void mouseMoved(MouseEvent e)
	{
		Point point = e.getPoint();
		for (CopyrightLink link : _links) {
			if (link.hotspot.contains(point)) {
				setCursor(Cursor.getPredefinedCursor(12));
				return;
			}
		}
		setCursor(Cursor.getPredefinedCursor(0));
	}

	CopyrightBar(String message)
	{
		setBackground(backColour);
		addMouseListener(this);
		addMouseMotionListener(this);

		parseMessage(message);
		if (_lines == null) {
			return;
		}

		// get all links in the message
		int links = 0;
		for (CopyrightText[] tokens : _lines) {
			for (CopyrightText token : tokens) {
				if (token.link != null) {
					links++;
				}
			}
		}
		_links = new CopyrightLink[links];
		int l = 0;
		for (CopyrightText[] tokens : _lines) {
			for (CopyrightText token : tokens) {
				if (token.link != null) {
					_links[l++] = token.link;
				}
			}
		}
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_a JD-Core Version: 0.5.4
 */