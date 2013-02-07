package study.andrey;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class MainActivity extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String VERSION = "2.0";
	private static final Rectangle DEFAULT_FRAME = new Rectangle(100, 100, 360, 210);
	private static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
	private static final boolean USE_GRID_LAYOUT = false;
	CalcDisplay calcDisplay;
	CalcCore cr;
	MyPrefs prefs;
	JFrame frame;
	private boolean saveResult = false;
	private JCheckBoxMenuItem saveRes;

	MainActivity() {
		frame = new JFrame("Калькулятор");
		frame.setMinimumSize(DEFAULT_FRAME.getSize());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Чтение сохраненных состояний и настроек
		prefs = new MyPrefs("calc.ini");
		prefs.restoreWindowState(frame, DEFAULT_FRAME);
		saveResult = Boolean.parseBoolean(prefs.getString("SaveRes","false"));
		String initVal = saveResult ? prefs.getString("display", ""): "";
		
		KeyListener keyPress = new ButtonKeyListener();

		cr = new CalcCore(initVal);
		buildMenu();
		
		BorderLayout lt = new BorderLayout();
		lt.setHgap(5);
		lt.setVgap(3);
		frame.setLayout(lt);
		
		calcDisplay = new CalcDisplay();
		frame.add(calcDisplay, BorderLayout.NORTH);

		cr.setCalcDisplay(calcDisplay);
		calcDisplay.setText(cr.getDisplay());
		calcDisplay.addKeyListener(keyPress);
		
		if (USE_GRID_LAYOUT) {
			buildGridLayout(keyPress);
		} else {
			buildGridBagLayout(keyPress);
		}
		
		frame.setVisible(true);
	    frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	closeApplication();
            }
	    });
	}

	// Отрисовка всех кнопочек с помощью менеджера компоновки GridBagLayout
	private void buildGridBagLayout(KeyListener keyPress) {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		JPanel pan1 = new JPanel(gbl);
		frame.add(pan1, BorderLayout.CENTER);
		
		int gridx=0;
		int gridy=0;
		gbc.insets = new Insets(1, 2, 1, 0);
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// Добавление кнопочек с циферками
		for (String digit: CalcCore.DIGIT) {
			DigitalButton btn = new DigitalButton(cr, digit);
			btn.setFont(FONT);
			btn.addKeyListener(keyPress);
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			gbc.gridwidth = (digit.equals("0")) ? 2:1; // Кнопку 0 делаем в 2 раза шире
			pan1.add(btn, gbc);
			gridx+=gbc.gridwidth;
			if (gridx>2) {
				gridy++;
				gridx=0;
			}
		}
		
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		JPanel pan2 = new JPanel(gbl);
		frame.add(pan2, BorderLayout.EAST);
		
		gridx=0;
		gridy=0;
		gbc.insets = new Insets(1, 0, 1, 2);
		gbc.weightx = 1;
		gbc.weighty = 1;

		// Добавление кнопочек с действиями
		for (Commands operation: Commands.values()) {
			CommandButton btnAc = new CommandButton(cr);
			btnAc.setFont(FONT);
			btnAc.setOperation(operation);
			btnAc.addKeyListener(keyPress);
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			pan2.add(btnAc, gbc);
			gridx++;
			if (gridx>2) {
				gridy++;
				gridx=0;
			}
		}
	}

	// Отрисовка всех кнопочек с помощью менеджера компоновки GridLayout
	private void buildGridLayout(KeyListener keyPress) {
		JPanel pan1 = new JPanel(new GridLayout(4, 3, 1, 1));
		frame.add(pan1, BorderLayout.CENTER);
		
		// Добавление кнопочек с циферками
		for (String digit: CalcCore.DIGIT) {
			DigitalButton btn = new DigitalButton(cr, digit);
			btn.setFont(FONT);
			btn.addKeyListener(keyPress);
			pan1.add(btn);
		}

		JPanel pan2 = new JPanel(new GridLayout(4, 3, 1, 1));
		frame.add(pan2, BorderLayout.EAST);
		
		// Добавление кнопочек с действиями
		for (Commands operation: Commands.values()) {
			CommandButton btnAc = new CommandButton(cr);
			btnAc.setFont(FONT);
			btnAc.setOperation(operation);
			btnAc.addKeyListener(keyPress);
			pan2.add(btnAc);
		}
	}

	private void buildMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Меню");
		menu.setFont(FONT);
		
		saveRes = new JCheckBoxMenuItem("Сохранять результат");
		saveRes.setFont(FONT);
		saveRes.setState(saveResult);
		saveRes.setMnemonic('С');
		menu.add(saveRes);
		
		menu.addSeparator();
		
		JMenuItem about = new JMenuItem("О программе...");
		about.setFont(FONT);
		// about.setIcon(new ImageIcon("img_info.png"));
		about.setMnemonic('О');
		about.setAccelerator(KeyStroke.getKeyStroke('I',KeyEvent.CTRL_MASK));
		menu.add(about);
		
		menubar.add(menu);
		frame.setJMenuBar(menubar);
		
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String aboutText = "Java Калькулятор\nВерсия: "+VERSION+"\n"+
						"Автор: Коваленко А.Б.\n\n"+
						"JRE: "+System.getProperty("java.version");
				JOptionPane.showMessageDialog(frame, aboutText, "О программе", JOptionPane.NO_OPTION);
			}
			
		});
		
		JMenuItem exit = new JMenuItem("Выход");
		exit.setFont(FONT);
		exit.setMnemonic('В');
		exit.setAccelerator(KeyStroke.getKeyStroke('Q',KeyEvent.CTRL_MASK));
		menu.add(exit);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeApplication();
			}
		});
	}

	private void closeApplication() {
		prefs.saveWindowState(frame);
		saveResult = saveRes.getState();
		if (saveResult) {
			prefs.setProperty("display", cr.getDisplay());
		}
		prefs.setProperty("SaveRes", new Boolean(saveResult).toString());
    	prefs.savePrefs();
		System.exit(0);
	}

	private final class ButtonKeyListener implements KeyListener {
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			System.out.println(e);
			switch (key) {
			case KeyEvent.VK_ENTER:
				cr.setOperation(Commands.Equal);
				break;
			case KeyEvent.VK_BACK_SPACE:
			case KeyEvent.VK_DELETE:
				cr.setOperation(Commands.Backspace);
				break;
			case KeyEvent.VK_ESCAPE:
				cr.setOperation(Commands.Clear);
				break;
			default:
				cr.pressKey(""+e.getKeyChar());
			}
		}

		@Override
		public void keyPressed(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
	}

	public static void main(String[] args) {
		new MainActivity();
	}
}
