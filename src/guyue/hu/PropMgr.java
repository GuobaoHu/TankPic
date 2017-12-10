package guyue.hu;

import java.io.IOException;
import java.util.Properties;

public class PropMgr {
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(PropMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//µ¥ÀýÄ£Ê½
	private PropMgr() {
	}
	
	public static String getProp(String key) {
		return prop.getProperty(key);
	}
}
