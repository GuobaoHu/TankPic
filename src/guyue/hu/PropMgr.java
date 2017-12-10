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
	
	//����ģʽ
	private PropMgr() {
	}
	
	/**
	 * @param key �����ļ���key
	 * @return ���������ļ��ж�Ӧ��valueֵ��ģʽ��"key=value",�Ⱥ��������߲�Ҫ�пո�
	 * 
	 */
	public static String getProp(String key) {
		return prop.getProperty(key);
	}
}
