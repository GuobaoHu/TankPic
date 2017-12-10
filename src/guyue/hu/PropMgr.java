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
	
	//单例模式
	private PropMgr() {
	}
	
	/**
	 * @param key 配置文件的key
	 * @return 返回配置文件中对应的value值，模式是"key=value",等号左右两边不要有空格
	 * 
	 */
	public static String getProp(String key) {
		return prop.getProperty(key);
	}
}
