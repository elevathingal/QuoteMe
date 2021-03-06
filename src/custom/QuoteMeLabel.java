package custom;

import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;

import library.FontLibrary;
import resources.Constants;

public class QuoteMeLabel extends JLabel {

	private static final long serialVersionUID = -4580175227994792364L;

	public QuoteMeLabel() {
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));
	}
	
	public QuoteMeLabel(int size) {
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));
	}

	public QuoteMeLabel(String text) {
		super(text);
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));

	}
	
	public QuoteMeLabel(String text, int size, boolean setSize) {
		super(text);
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));
	}

	public QuoteMeLabel(Icon image) {
		super(image);
	}

	public QuoteMeLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));
	}

	public QuoteMeLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));
	}

	public QuoteMeLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, 18));
	}

	public void setFontSize(int size) {
		setFont(FontLibrary.getFont(Constants.fontString, Font.PLAIN, size));

	}
}
