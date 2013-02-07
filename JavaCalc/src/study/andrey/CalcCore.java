package study.andrey;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  ласс, имитирующий работу виндового калькул€тора
 * 
 * @author Andrey
 * 
 */
public class CalcCore {

	private static final String REGEX_FOR_ROUNDING_RESULT = "[.]?(0{4,}[0-9]{1,}|0+\\Z)";
	private double operand, result, registr;
	private String operator, memory = "";
	private boolean equalButtonPressed, operandEntered;
	private StringBuffer display = new StringBuffer(64);
	public static final String[] DIGIT = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "." };
	//  оманды дл€ калькул€тора накод€тс€ в перечислении Commands
	
	private CalcDisplay calcDisplay;
	
	CalcCore() {
		clear();
	}
	
	CalcCore(String initialValue){
		clear();
		setNewValue(initialValue);
	}

	private void setNewValue(String newValue) {
		try {
			double newOp = Double.parseDouble(newValue);
			setOperand(newOp);
			setDisplay(newOp);
			operandEntered = true;
		} catch (Exception ignore) {}
	}

	private void clear() {
		operand = 0;
		result = 0;
		registr = 0;
		operator = "0";
		equalButtonPressed = true;
		display.setLength(0);
		operandEntered = false;
	}

	public void pressKey(String key) {
		for (String str : DIGIT) {
			if (str.equalsIgnoreCase(key)) {
				if (operandEntered) {
					display.setLength(0);
				}
				display.append(key);
				operandEntered = false;
				updateDisplay();
				return;
			}
		}
		for (Commands operation: Commands.values()) {
			if (operation.getKeyShortcut().equals(key)) {
				setOperation(operation);
				return;
			}			
		}
	}

	private void setOperand(double newOp) {
		if (equalButtonPressed) {
			operator = "0";
			operand = newOp;
		} else {
			operand = result;
		}
		result = newOp;
		registr = newOp;
		operandEntered = false;
	}


	public void setOperation(Commands op) {
		switch (op) {
		case Backspace:
			backspace();
			break;
		case Clear:
			clear();
			break;
		case ToMemory:
			storeToMemory();
			break;
		case FromMemory:
			restoreFromMemory();
			break;
		case ChangeSign:
			changeSign();
			break;
		case Round:
			roundResult();
			break;
		case ToBinary:
			toBin();
			return;
		default:
			if (display.length()==0) {
				return;
			}
			if (!operandEntered) {
				setOperand(Double.parseDouble(display.toString()));
			}
			if (op == Commands.Equal) {
				calc();
				equalButtonPressed = true;
			} else {
				if (!operandEntered) {
					calc();
				}
				operator = op.getKeyShortcut();
				equalButtonPressed = false;
			}
		
		}
		updateDisplay();
	}

	private void toBin() {
		if (display.length()>0) {
			calcDisplay.setText(getDisplay(), 2);
		}
	}

	private void storeToMemory() {
		memory = display.toString();
	}
	
	private void restoreFromMemory() {
		setNewValue(memory);
	}
	
	public String getMemory() {
		return memory;
	}
		
	
	private void changeSign() {
		if (display.length()>0) {
			result = -result;
			setDisplay(result);
			operandEntered = true;
		}
	}

	private void backspace() {
		if (!operandEntered && display.length()>0) {
			display.deleteCharAt(display.length()-1);
		}
	}
	
	public void setCalcDisplay(CalcDisplay cd) {
		calcDisplay = cd;
	}
	
	private void roundResult() {
		try {
			int selStart = calcDisplay.getSelectionStart();
			int selEnd   = calcDisplay.getSelectionEnd();
			int pointPos = getDisplay().indexOf(".");
			if (selEnd > selStart && pointPos>=0 && selEnd > pointPos) {
				int afterPointPos = selEnd - pointPos - 1;
				double numOnDisplay = Double.parseDouble(display.toString());
				long r1 = (long) numOnDisplay ;
				double d1 = numOnDisplay - r1;
				double pow = Math.pow(10, afterPointPos);
				numOnDisplay = r1+Math.round(d1*pow)/pow;
				result = numOnDisplay;
				setDisplay(numOnDisplay);
				operandEntered = true;
			}
		} catch (Exception ignore) {}
	}

	private void calc() {
		switch (operator) {
		case "+":
			result = equalButtonPressed ? result + registr : operand + result;
			break;
		case "-":
			result = equalButtonPressed ? result - registr : operand - result;
			break;
		case "*":
			result = equalButtonPressed ? result * registr : operand * result;
			break;
		case "/":
			result = equalButtonPressed ? result / registr : operand / result;
			break;
		default:
			break;
		}
		setDisplay(result);
		operandEntered = true;
	}

	private void setDisplay(Double in) {
		String ret = in.toString();
		Pattern pat = Pattern.compile(REGEX_FOR_ROUNDING_RESULT);
		Matcher mat = pat.matcher(ret);
		if (mat.find()) {
			ret = mat.replaceFirst("");
		}
		display.setLength(0);
		display.append(ret);
	}
	
	public String getDisplay() {
		return display.toString()+" ";
	}

	private void updateDisplay() {
		calcDisplay.setText(getDisplay());
	}
}
