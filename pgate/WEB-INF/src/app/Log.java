package app;
/*
 * @author Dipak.Mali
 */

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

	public static Logger logger;

	static {
		try {
			boolean append = true;

			String fileName = Config.get("LOG_PATH") + Log.getFileName();

			FileHandler fh = new FileHandler(fileName, append);
			// sfh.setFormatter(new XMLFormatter());
			fh.setFormatter(new SimpleFormatter() {
				@Override
				public String format(LogRecord rec) {
					StringBuffer buf = new StringBuffer(1000);
					buf.append(new java.util.Date());
					buf.append(' ');
					buf.append(rec.getLevel());
					buf.append(' ');
					buf.append(this.formatMessage(rec));
					String newLine = String.format("%n");
					buf.append("\n" + newLine);
					
					return buf.toString();
				}
			});
			logger = Logger.getLogger("TestLog");
			logger.addHandler(fh);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static String getFileName() {

		String arrMonth[] = new String[12];
		arrMonth[0] = "01";
		arrMonth[1] = "02";
		arrMonth[2] = "03";
		arrMonth[3] = "04";
		arrMonth[4] = "05";
		arrMonth[5] = "06";
		arrMonth[6] = "07";
		arrMonth[7] = "08";
		arrMonth[8] = "09";
		arrMonth[9] = "10";
		arrMonth[10] = "11";
		arrMonth[11] = "12";

		Date dateObj = new Date();
		String fileName = "Log" + (dateObj.getYear() + 1900) + "-"
				+ arrMonth[dateObj.getMonth()] + "-" + dateObj.getDate()
				+ ".log";

		return fileName;
	}
}