package study.andrey;

/**
 * @author Andrey
 *
 */
public enum Commands {
	ChangeSign("+/-","!"), Backspace("Del.","D"), Clear("C"), 
	Addition("+"), Subtraction("-"), ToMemory("M"),
	Multiplication("*"), Division("/"), FromMemory("R"),
	Equal("="), Round("Rnd.","O"), ToBinary("Bin","B") ;
	private String keyCaption;
	private String keyShortcut;
	Commands(String caption) {
		this.keyCaption = caption;
		this.keyShortcut = caption;
	}
	Commands(String caption, String shortcut) {
		this.keyCaption = caption;
		this.keyShortcut = shortcut;
	}
	public String getKeyCaption() {
		return keyCaption;
	}
	public String getKeyShortcut() {
		return keyShortcut;
	}
}
