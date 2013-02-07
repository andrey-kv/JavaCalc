package study.andrey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

class CalcButton extends JButton 
	implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	CalcCore calculator;
	CalcDisplay display;
	Commands cmd;
	
	CalcButton(CalcCore cr, CalcDisplay cDisplay) {
		super();
		calculator = cr;
		display = cDisplay;
		addActionListener(this);
		addKeyListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doAction(e.getActionCommand());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_ENTER:
			doAction("=");
			break;
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
			doAction("D");
			break;
		case KeyEvent.VK_ESCAPE:
			doAction("C");
			break;
		default:
			doAction(""+e.getKeyChar());
		}
	}
	
	private void doAction(String prm) {
		calculator.pressKey(prm.toUpperCase());
		display.setText(calculator.getDisplay());
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent arg0) {}


}