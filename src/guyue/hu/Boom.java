package guyue.hu;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Boom {
	private int x, y;
	private TankClient tc;
	private boolean live = true;
//	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = new Image[11];
	
	private int step = 0;
	
	public Boom(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	//ͨ��ImageIO��Image��ֵ����������״α�ը������ͼƬ�����,��ImageIO��ֱ�ӽ�Ӳ���ϵ�ͼƬ����һ���Զ����ڴ���
	//д�ھ�̬�����У���Ϊ��һ���Ը���̬imgs���鸳ֵ�����ٶ�Ӳ�̵Ĵ���������Ӳ�̵�ʹ������
	static {
		try {
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = ImageIO.read(Boom.class.getClassLoader().getResource("images/" + i + ".gif"));
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//������ը����
	public void draw(Graphics g) {
		if(!live) {
			tc.getBooms().remove(this);
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step ++;
		if(step == imgs.length) {
			live = false;
			step = 0;
		}
	}
	
}
