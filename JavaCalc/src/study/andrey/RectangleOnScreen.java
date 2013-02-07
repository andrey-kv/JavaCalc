package study.andrey;

import java.awt.Rectangle;
import java.awt.Toolkit;

public class RectangleOnScreen extends Rectangle {

	private static final long serialVersionUID = 1L;

	public void fit() {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		if (!screen.contains(this)) {
			if (width > screen.width) {
				width = screen.width-50;
			}
			if (height > screen.height) {
				height = screen.height-100;
			}
			if (x+width > screen.x+screen.width) {
				x = screen.x+screen.width - width - 50;
			}
			if (y+height > screen.y+screen.height) {
				y = screen.y+screen.height - height - 50;
			}
			if (x < screen.x) {
				x = screen.x + 50;
			}
			if (y < screen.y) {
				y = screen.y + 50;
			}
		}
	}
}
