package guyue.hu;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.*;

public class Bullet {
	public static final int X_STEP = 10;
	public static final int Y_STEP = 10;
	private int locationX, locationY;
	private Direction direction;
	private TankClient tc;
	private boolean good = true;
	private static int kill = 0;
	private static Image[] bulletImgs;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	static {
		try {
			bulletImgs = new Image[] {
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileU.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileD.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileL.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileR.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileLU.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileRU.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileLD.gif")),
					ImageIO.read(Bullet.class.getClassLoader().getResource("images/missileRD.gif"))
			};
			imgs.put("U", bulletImgs[0]);
			imgs.put("D", bulletImgs[1]);
			imgs.put("L", bulletImgs[2]);
			imgs.put("R", bulletImgs[3]);
			imgs.put("LU", bulletImgs[4]);
			imgs.put("RU", bulletImgs[5]);
			imgs.put("LD", bulletImgs[6]);
			imgs.put("RD", bulletImgs[7]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static final int SIZE = bulletImgs[0].getWidth(null);
	
	public static int getKill() {
		return kill;
	}

	public Bullet(int locationX, int locationY, Direction direction) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.direction = direction;
	}
	
	public Bullet(int locationX, int locationY, Direction direction, TankClient tc, boolean good) {
		this(locationX, locationY, direction);
		this.good = good;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		
		this.move(g);
	}
	
	public void move(Graphics g) {
		switch(direction) {
		case U :
			g.drawImage(imgs.get("U"), locationX, locationY, null);
			locationY -= Y_STEP;
			break;
		case D :
			g.drawImage(imgs.get("D"), locationX, locationY, null);
			locationY += Y_STEP;
			break;
		case L :
			g.drawImage(imgs.get("L"), locationX, locationY, null);
			locationX -= X_STEP;
			break;
		case R :
			g.drawImage(imgs.get("R"), locationX, locationY, null);
			locationX += X_STEP;
			break;
		case LU :
			g.drawImage(imgs.get("LU"), locationX, locationY, null);
			locationX -= X_STEP;
			locationY -= Y_STEP;
			break;
		case RU :
			g.drawImage(imgs.get("RU"), locationX, locationY, null);
			locationX += X_STEP;
			locationY -= Y_STEP;
			break;
		case LD :
			g.drawImage(imgs.get("LD"), locationX, locationY, null);
			locationX -= X_STEP;
			locationY += Y_STEP;
			break;
		case RD :
			g.drawImage(imgs.get("RD"), locationX, locationY, null);
			locationX += X_STEP;
			locationY += Y_STEP;
			break;
		}
		this.outRange();
	}
	
	//判断是否出界并作出处理
	public void outRange() {
		if(locationX < 0 || locationY < 0 || locationX > TankClient.GAME_WIDTH || locationY > TankClient.GAME_HEIGHT) {
			this.tc.getBullets().remove(this);
		}
	}
	
	//获取子弹的外包裹矩形
	public Rectangle getRect() {
		return new Rectangle(locationX, locationY, SIZE, SIZE);
	}
	
	//检查子弹撞墙的问题
	public boolean hitWall(Wall w) {
		if(this.getRect().intersects(w.getRect())) {
			tc.getBullets().remove(this);
			return true;
		}
		return false;
	}
	
	//检查子弹是否击中Tank
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive() && (this.good != t.isGood())) {
			if(t.isGood()) {
				t.setLife(t.getLife() - 10);
				if(t.getLife() <= 0) {
					t.setLive(false);
				}
			} else {
				t.setLive(false);
			}
			tc.getBooms().add(new Boom(locationX, locationY, tc));
			tc.getBullets().remove(this);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if(this.hitTank(tank)) {
				kill ++;
				tanks.remove(tank);
				return true;
			}
		}
		if(tanks.size() < 6) {
			tc.addEnemy();
		}
		return false;
	}
}
