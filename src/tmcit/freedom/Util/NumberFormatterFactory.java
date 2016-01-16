package tmcit.freedom.Util;

import java.text.NumberFormat;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class NumberFormatterFactory extends DefaultFormatterFactory {
	private static NumberFormatter numberFormatter = new NumberFormatter();

	static {
		numberFormatter.setValueClass(Integer.class);
		((NumberFormat) numberFormatter.getFormat()).setGroupingUsed(false);
	}

	public NumberFormatterFactory() {
		super(numberFormatter, numberFormatter, numberFormatter);
	}

}

