package study.andrey;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JFrame;

public class MyPrefs extends Properties
{

	private static final long serialVersionUID = 1L;
	final String settingsFilename;

	public MyPrefs(String fileName) {
		super();
		String homeDir = System.getProperty("user.home");
		settingsFilename = homeDir + File.separator + fileName;
		
		try {
			FileInputStream input = new FileInputStream(settingsFilename);
			this.load(input);
			input.close();
		} catch(Exception ignore) {
		}
	}
	
	public void savePrefs() {
		try {
			FileOutputStream output = new FileOutputStream(settingsFilename);
            this.store(output, "Saved settings");
            output.close();
        } catch(Exception ignore) {
		}
	}
	
	public Integer getInt(String key, int defValue) {
		int ret;
		try {
			ret = Integer.parseInt(this.getProperty(key, ""+defValue));
		} catch (NumberFormatException e) {
			ret = defValue;
		}
		return ret;
	}
	
	public String getString(String key, String defValue) {
		String ret;
		try {
			ret = this.getProperty(key, defValue);
		} catch (Exception e) {
			ret = defValue;
		}
		return ret;
	}
	
	public void restoreWindowState(JFrame frame, Rectangle def) {
		RectangleOnScreen bounds = new RectangleOnScreen();
		bounds.x = getInt("posX", def.x);
		bounds.y = getInt("posY", def.y);
		bounds.height = getInt("height", def.height);
		bounds.width = getInt("width", def.width);
		bounds.fit();
		frame.setBounds(bounds);
		if (getString("Max","").equals("true")) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
	
	public void saveWindowState(JFrame frame) {
    	if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
    		setProperty("Max", "true");
    	} else {
	    	Rectangle bounds = frame.getBounds();
	    	setProperty("posX", ""+bounds.x);
	    	setProperty("posY", ""+bounds.y);
	    	setProperty("width", ""+bounds.width);
	    	setProperty("height", ""+bounds.height);
	    	setProperty("Max", "false");
    	}
		
	}
}
