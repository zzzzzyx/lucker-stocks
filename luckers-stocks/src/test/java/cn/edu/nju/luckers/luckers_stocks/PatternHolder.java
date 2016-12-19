package cn.edu.nju.luckers.luckers_stocks;

import java.util.regex.Pattern;

public class PatternHolder {


	public static final Pattern stockCodePattern = Pattern.compile("[a-zA-Z]{2}[0-9]{6}");
	public static final Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
	public static final Pattern digitPattern = Pattern.compile("[0-9]+");
	public static final Pattern decimalPattern = Pattern.compile("[0-9]+\\.[0-9]+");
}
