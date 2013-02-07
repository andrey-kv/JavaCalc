package study.andrey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DigitalButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	CalcCore calculator;

	DigitalButton(CalcCore cr, String digit) {
		super();
		calculator = cr;
		setText(digit);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		calculator.pressKey(e.getActionCommand());
	}
}