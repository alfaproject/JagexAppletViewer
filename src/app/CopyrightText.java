package app;

final class CopyrightText
{
	public String text;
	public CopyrightLink link;

	CopyrightText(String text)
	{
		this.text = text;
	}

	CopyrightText(String text, String href)
	{
		this.text = text;
		this.link = new CopyrightLink(href);
	}
}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_l JD-Core Version: 0.5.4
 */