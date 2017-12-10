package guyue.hu;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author hgb22613
 *
 */
public class Boom {
	private int x, y;
	private TankClient tc;
	private boolean live = true;
//	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = new Image[11];
	
	private int step = 0;
	
	/**
	 * @param x,x坐标
	 * @param y，y坐标
	 * @param tc，持有TankClient的引用
	 */
	public Boom(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	//通过ImageIO对Image赋值，不会产生首次爆炸画不出图片的情况,即ImageIO会直接将硬盘上的图片数据一次性读到内存中
	//写在静态语句块中，是为了一次性给静态imgs数组赋值，减少读硬盘的次数，增加硬盘的使用寿命
	static {
		try {
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = ImageIO.read(Boom.class.getClassLoader().getResource("images/" + i + ".gif"));
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//画出爆炸过程
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
