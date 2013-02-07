package study.andrey;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CalcDisplay extends JTextField {

	private static final long serialVersionUID = 1L;

	public CalcDisplay() {
		super();
		setFont(new Font("Verdana", Font.BOLD, 11));
		setHorizontalAlignment(JTextField.RIGHT);
		setPreferredSize(new Dimension(0, 30));
		setEditable(false);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}
	
	public void setText(String strg, int numSystem) {
		switch (numSystem) {
		case 2:
			long num = (long) Double.parseDouble(strg);
			strg = Long.toBinaryString(num) + " ";
		default:
		}
		setText(strg);
	}
}
