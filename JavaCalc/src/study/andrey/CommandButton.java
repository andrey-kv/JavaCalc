package study.andrey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class CommandButton extends JButton 
	implements ActionListener {

	private static final long serialVersionUID = 1L;
	CalcCore calculator;
	Commands cmd;
	static final Color COLOR_NORMAL = new Color(0,128,0);
	static final Color COLOR_CLEAR  = new Color(128,0,0);
	
	CommandButton(CalcCore cr) {
		super();
		calculator = cr;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		calculator.setOperation(cmd);
	}
	
	public void setOperation(Commands operation) {
		cmd = operation;
		setText(operation.getKeyCaption());
		setActionCommand(operation.getKeyShortcut());
		if (operation == Commands.Clear) {
			setForeground(COLOR_CLEAR);
		} else {
			setForeground(COLOR_NORMAL);
		}
	}
}