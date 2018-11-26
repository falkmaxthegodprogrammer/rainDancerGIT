 package com.cmbstnengine.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.audio.AudioPlayer;
import com.cmbstnengine.game.entity.entityobjects.ObjectType.ObjType;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.entity.entityobjects.TileObjectManager;
import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.events.EventDispatcher;
import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.events.types.KeyPressedEvent;
import com.cmbstnengine.game.events.types.KeyReleasedEvent;
import com.cmbstnengine.game.events.types.MouseMovedEvent;
import com.cmbstnengine.game.events.types.MousePressedEvent;
import com.cmbstnengine.game.events.types.MouseReleasedEvent;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.inventory.Inventory;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.stats.StatSheet;
import com.cmbstnengine.game.ui.UI;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.TileCoordinate;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class Player extends Mob implements EventListener {

	private int castSpeed = Fireball.FIRE_RATE;
	
	public float centerX = GamePanel.width / 2;
	public float centerY = GamePanel.height / 2;
	private float cameraX = pos.x - centerX;
	private float cameraY = pos.x - centerY;
	private float speedModifier;
	
	public static Rectangle playerBounds = new Rectangle(GamePanel.screenWidth, GamePanel.screenHeight);
	public static Rectangle playerBoundsRight = new Rectangle(GamePanel.screenWidth, GamePanel.screenHeight);
	
	private HashMap<String, AudioClip> sfx;
	
	private static TileObject lastTileObject = null;
	private static TileObject lastPickedUpObject = null;
	public static NPC lastInteractableNPC = null;
	public boolean interactingWithNPC = false;
	
	private TileObject lastHightlightedTileObject = null;
	private Inventory inventory = new Inventory();

	private boolean looting;
	
	private StatSheet stats;
	
	public Player(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);

		this.size = size;
		stats = new StatSheet();
		speedModifier = 1;
		acc = 1;
		deacc = 1;
		maxSpeed = 6f;		
		bounds.setWidth(super.size / 3);
		bounds.setHeight(super.size / 8);
		bounds.setXOffset(super.size / 3 - (super.size >> 6));
		bounds.setYOffset(super.size - super.size / 10);
		noClip = false;
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT),
				10);
		this.health_points = 100;
		
		hitBox = new AABB(origin, this.size, this.size);
		hitBox.setWidth(super.size / 2);
        hitBox.setXOffset(this.size);
		hitBox.setHeight(super.size + 6);
		hitBox.setYOffset(this.size);
		
		this.max_mana = 100;
		this.mana_points = max_mana;
		
		sfx = new HashMap<String, AudioClip>();
		sfx.put("footstep_dirt_00", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_00.wav"));
		sfx.put("footstep_dirt_01", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_01.wav"));
		sfx.put("footstep_dirt_02", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_02.wav"));
		sfx.put("footstep_dirt_03", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_03.wav"));
		sfx.put("footstep_dirt_04", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_04.wav"));
		sfx.put("footstep_dirt_05", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_05.wav"));
		
		sfx.put("spell_00", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Spell_00.wav"));
		sfx.put("spell_01", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Spell_01.wav"));
		sfx.put("spell_02", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Spell_02.wav"));
		sfx.put("spell_03", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Spell_03.wav"));
		sfx.put("spell_04", new AudioClip("res/SFX/Fantasy Sound Library/Wav/Spell_04.wav"));
		
		sfx.put("magic_00", new AudioClip("res/SFX/RPG Sound Pack/battle/magic1.wav"));
		sfx.put("magic_01", new AudioClip("res/SFX/RPG Sound Pack/battle/spell.wav"));
		
		sfx.put("swing0", new AudioClip("res/SFX/RPG Sound Pack/battle/swing.wav"));
		sfx.put("swing2", new AudioClip("res/SFX/RPG Sound Pack/battle/swing2.wav"));
		sfx.put("swing3", new AudioClip("res/SFX/RPG Sound Pack/battle/swing3.wav"));
		
	}

	private void move() {		
	//	if(!up && !down && !left && !right) return;
		if(pos.x < 0) pos.x = 0;
		if(pos.y < 0) pos.y = 0;
		
		if(up) {
			if(getCurrentTile().y == 0) {
				if(pos.y == 0) {
					dy = 0;
					return;
				}
			}
			dy -= acc;
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		} else {
			if(dy < 0) {
				dy += deacc;
				if(dy > 0) {
					dy = 0;
				}
			}
		}
		if(down) {
			dy += acc;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		} else {
			if(dy > 0) {
				dy -= deacc;
				if(dy < 0) {
					dy = 0;
				}
			}
		}
		if(left) {
			if(getCurrentTile().x == 0) {
				if(pos.x == 0) {
					dx = 0;
					return;
				}
			}
			dx -= acc;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else {
			if(dx < 0) {
				dx += deacc;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		if(right) {
			dx += acc;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if(dx > 0) {
				dx -= deacc;
				if(dx < 0) {
					dx = 0;
				}
			}
		}
	}
	
	private void resetPosition() {
		System.out.println("Resetting Player... ");
		pos.x = GamePanel.width / 2 - 32;
		PlayState.map.x = 0;
		pos.y = GamePanel.height /2 - 32;
		PlayState.map.y = 0;
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
	}
	
	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));	
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMoved((MouseMovedEvent) e));	
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPressed((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyReleased((KeyReleasedEvent) e));
	}
	
	public boolean isCameraOnMapEdgeX() {
		return PlayState.map.x == 0;
	}
	
	public boolean isCameraOnMapEdgeY() {
		return PlayState.map.y == 0;
	}
	
	public Vector2i getCurrentTile() {
		return TileCoordinate.getCurrentTile(pos);
	}
	
	public void update() {
		float camerax = 0;
		float cameray = 0;
		
		if(isShooting) {
			determineShootingDirection();
		}
		
		if(!interactingWithNPC) {
			super.update();
		}
		
		if(!fallen) {
			move();
			if(!tc.collisionTile(dx, 0) && !interactingWithNPC  ) {		
				
					if(pos.x < GamePanel.width / 2 - size / 2 ) {
						camerax = 0;	
					} else {
						camerax = dx;
					}		
					
				PlayState.map.x += camerax;
				pos.x += dx;		
			}
			
			if(!tc.collisionTile(0, dy) && !interactingWithNPC) {			
					if(pos.y < GamePanel.height / 2 - size / 2 ) {
						cameray = 0;	
					} else {
						cameray = dy;
					}			
					
				PlayState.map.y += cameray;
				pos.y += dy;		
			}
	
							
		} else {
			if(ani.hasPlayedOnce()) {
				resetPosition();
				dx = 0;
				dy = 0;
				fallen = false;
			}
		}		

		if(health_points <= 0) health_points = 100;
		if(health_points > max_hp) health_points = max_hp;
			
		playerBounds.x = (int) pos.x - (GamePanel.width / 2) - 64;
		playerBounds.y = (int) pos.y - (GamePanel.height / 2) - 64;
		playerBoundsRight.x = (int) pos.x + (GamePanel.width / 2) + 64;
		playerBoundsRight.y = (int) pos.y + (GamePanel.height / 2) + 64;
		
		if(isCameraOnMapEdgeY()) {
			playerBoundsRight.y = (int) pos.y + (GamePanel.height / 2) + 512;
		}
		
		if(isCameraOnMapEdgeX()) {
			playerBoundsRight.x = (int) pos.y + (GamePanel.height / 2) + 512;
		}
		
		
	}

	@Override
	public void render(Graphics2D g) {		
		g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
		if(shield) {
			if(shield && shootingUp || shootingDown || shootingLeft || shootingRight) {
				g.setColor(Color.lightGray);
				g.drawOval((int) pos.getWorldVar().x - 2, (int) pos.getWorldVar().y - 4, 96, 96);
			} else if (shield){
				g.setColor(Color.BLUE);
				g.drawOval((int) pos.getWorldVar().x - 2, (int) pos.getWorldVar().y + 4, 96, 96);
			}
		}
	/*	drawHealthBar(g);
		if(health_points < max_hp) {
			drawMissingHealthBar(g);
		}
		*/
	}

	public void shoot(double dir) {
		int xModifier = 0;
		int yModifier = 0;
		
		if(shootingUp) {
			xModifier = 24;
		} else if(shootingDown) {
			xModifier = -20;
		} else if(shootingLeft) {
			yModifier = -16;
		} else if(shootingRight) {
			xModifier = 6;
			yModifier = 16;
		} else {
			xModifier = 0;
			yModifier = 0;
		}				
		Arrow a = new Arrow(Arrow.ARROW_SPRITE, new Vector2f(pos.x + size / 2 + xModifier, pos.y + size / 2 + yModifier), 48, dir, this);
		PlayState.add(a);
		AudioPlayer.playSound(sfx.get("swing0"), 0.51);
	}

	public void updateShooting(float dy, float dx) {
		if(!isShooting) return;
		double dir = Math.atan2(dy, dx);
		shoot(dir);
		castSpeed = Arrow.FIRE_RATE;	
	}

	public void input(MouseHandler mouse, KeyHandler key) {		
				
		if(castSpeed > 0) { 
			castSpeed--;
		}
		
		float dx = MouseHandler.getX() - GamePanel.width / 2;
		float dy = MouseHandler.getY() - GamePanel.height / 2;
	
		if(castSpeed <= 0 && isShooting) {
			updateShooting(dy, dx);
		} 		
	}
	
	public void determineShootingDirection() {
		if(isShooting && MouseHandler.mouseY < GamePanel.height / 2 - GamePanel.height / 4) {
			shootingLeft = false;
			shootingRight = false;
			shootingDown = false;
			shootingUp = true;
		} else if(isShooting && MouseHandler.mouseY > GamePanel.height / 2 + GamePanel.height / 4) {
			shootingRight = false;
			shootingLeft = false;
			shootingUp = false;
			shootingDown = true;
		} else if(isShooting && MouseHandler.mouseX < GamePanel.width / 2 - 48 && MouseHandler.mouseY < GamePanel.height + GamePanel.height / 4) {
			shootingUp = false;
			shootingRight = false;
			shootingDown = false;
			shootingLeft = true;
		} else if(isShooting && MouseHandler.mouseX > GamePanel.width / 2 + 48 && MouseHandler.mouseY > GamePanel.height / 3) {
			shootingLeft = false;
			shootingUp = false;
			shootingDown = false;
			shootingRight = true;
		}
	}
	
	public static boolean isWithinBounds(int x, int y, int rx, int ry) {
		if(playerBounds.x > x) return false;
		if(playerBounds.y > y) return false;
		if(playerBoundsRight.x < rx) return false;
		if(playerBoundsRight.y < ry) return false;
		return true;
	}

	public float getCameraX() {
		return cameraX;
	}

	public void setCameraX(float cameraX) {
		this.cameraX = cameraX;
	}

	public float getCameraY() {
		return cameraY;
	}

	public void setCameraY(float cameraY) {
		this.cameraY = cameraY;
	}
	
	public Vector2f getPos() {
		return pos;
	}
	
	public void setWorldPos(int x, int y) {
		pos.x = GamePanel.width / 2 - 32;
		PlayState.map.x = x;

		pos.y = GamePanel.height /2 - 32;
		PlayState.map.y = y;
	}

	public String getHP() {
		return String.valueOf(health_points);
	}

	public AABB getHitBox() {
		return hitBox;
	}
	
	public float calcRedHpBarPercentage() {
		float percentage = (float) health_points / max_hp;
		if(percentage < 0.1) {
			percentage = 0;
		}
		return percentage;
	}
	
	public void drawHealthBar(Graphics2D g) {		
		g.setColor(Color.GREEN);
		g.fillRect((int) (pos.getWorldVar().x + size / 4), (int) (pos.getWorldVar().y + size), size / 2, 4);	
	}
	
	public void drawMissingHealthBar(Graphics2D g) {	
		float originalSize = size / 2;
		float missingHpBarWidth = originalSize - originalSize * calcRedHpBarPercentage();
		g.setColor(Color.RED);		
		g.fillRect((int) ((pos.getWorldVar().x + size / 4) + (originalSize - missingHpBarWidth + 1)), (int) (pos.getWorldVar().y + size),(int) missingHpBarWidth + 1, 4);
	}
	
	public void getHit(Enemy e, Projectile p) {
		int damage = p.getDamage();
		if(!shield) {
			health_points -= damage;
		}
	}

	public static void nearTileObject(TileObject tileEntity) {
		lastTileObject = tileEntity;
	}
	
	public boolean withinRangeOf(TileObject tileEntity) {
		if(tileEntity != null) {
			return tileEntity.isInsideSense(getBounds());
		} return false;
	}
	
	public boolean withinRangeOf(NPC npc) {
		if(npc != null) {
			return npc.isInsideSense(getBounds());
		} return false;
	}
		
	public void pickUpObject(TileObject t) {
		inventory.add(t.toString());
		if(t.getGid() == 781) health_points += 25;
	}

	public static void lastPickedUpTileObject(TileObject tileObject) {
		lastPickedUpObject = tileObject;		
	//	pickUpObject(tileObject);
	}
	
	public void interactWithNearestEntity() {
		if(!withinRangeOf(lastTileObject) && lastInteractableNPC != null && withinRangeOf(lastInteractableNPC)) {
			interactingWithNPC = true;
			lastInteractableNPC.interact(this);
		} else if(!withinRangeOf(lastInteractableNPC) && lastTileObject != null && withinRangeOf(lastTileObject)) {
			interactingWithNPC = false;
			lastTileObject.interact(this);
		}
	}
		
	public boolean onMouseMoved(MouseMovedEvent e) {
		if(!isShooting) {
		float x = (float) (e.getX()  + PlayState.map.x);
		float y = (float) (e.getY() + PlayState.map.y);
		
	//	System.out.println(x);
	//	System.out.println(y);
		
		if(lastHightlightedTileObject != null && !lastHightlightedTileObject.containsPoint(x, y)) {
			lastHightlightedTileObject.highlighted = false;
		}
			
		TileObject tileObject = TileObjectManager.getClosestObjectInList(new Vector2f(x, y), 32);
		lastHightlightedTileObject = tileObject;
		
		if(tileObject == null) return true;
		
			if(tileObject != null) {
				if(tileObject.containsPoint(x, y) && tileObject.objectType == ObjType.CLICKABLE) {
					tileObject.highlighted = true;
					return true;
				}
			}	
			
			if(tileObject != null && !tileObject.containsPoint(x,  y)) {
				tileObject.highlighted = false;
			}
		}
			
		return false;
	}
	
	public int inventorySize() {
		return inventory.getTileobjects().size() + inventory.getItems().size();
	}

	
	public boolean onMousePressed(MousePressedEvent e) {		
		if(!isShooting) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				float x = (float) ((e.getX() + PlayState.map.x));
				float y = (float) ((e.getY() + PlayState.map.y));
				TileObject tileObject = TileObjectManager.getClosestObjectInList(new Vector2f(x, y), 32);
				if(tileObject != null) {
					if(tileObject.containsPoint(x, y) && tileObject.objectType == ObjType.CLICKABLE) {
						if(UI.mineCraftButtons[UI.getCurrentEquippedBtnSlot()] != null) {
								if(UI.mineCraftButtons[UI.getCurrentEquippedBtnSlot()].addItemToSlot(tileObject)) {
									tileObject.remove();
									TileObjectManager.removeObject(tileObject);
									inventory.add(tileObject);
									return true;	
								} else {
									return false;
								}
							}
						}
					}
				}
			}
		
		
		if(UI.mineCraftButtons[UI.getCurrentEquippedBtnSlot()] != null) {
			TileObject t  = (TileObject)UI.mineCraftButtons[UI.getCurrentEquippedBtnSlot()].getItem();
			if(t != null && e.getButton() == MouseEvent.BUTTON1) {
				t.setPos(new Vector2f(e.getX() + PlayState.map.x - t.getBounds().getWidth() / 2, e.getY() + PlayState.map.y - t.getBounds().getHeight() / 2));
					TileObjectManager.addObject(t);
					UI.mineCraftButtons[UI.getCurrentEquippedBtnSlot()].removeTopItem();
					t.unRemove();
					PlayState.add(t);
					t.setRemoveFromInventory(true);
			
					t.highlighted = false;
					UI.mineCraftButtons[UI.getCurrentEquippedBtnSlot()].setItem(null);
					inventory.removeItems();

				
				return true;
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			isShooting = true;
				if(isShooting) {
					determineShootingDirection();
					return true;
				}
			}
		return false;
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isShooting = false;
			return true;
		}
		return false;
	}
	
	public boolean onKeyPressed(KeyPressedEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_L) {
			PlayState.spawnEnemies();
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			
			if(interactingWithNPC)  {
				interactingWithNPC = false; 
				UI.hideDialog();
				return true;
			}
			
			for(NPC npc : PlayState.npcs) {
				if(withinRangeOf(npc) && !interactingWithNPC) {
					npc.interact(this);
					interactingWithNPC = true;
					return true;
				} 
			}
			
			TileObject tileObject = TileObjectManager.getClosestObjectInList(pos, this);

			if(tileObject != null) {	//if(withinRangeOf(tileObject))  {
				tileObject.interact(this);
				return true;
			}
		
		if(e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_ALT_GRAPH) {
			looting = true;
		}
			
			
			
				//}
				
				
			/*
			 * 
			if(interactingWithNPC) {
				interactingWithNPC = false;
				UI.hideDialog();
				UI.showDialogFrame = false;
				return true;
			} else {
				interactWithNearestEntity();
				return true;
			}
		} */
			
		}
		return false;

	}

	public boolean onKeyReleased(KeyReleasedEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
			return true;
		}
		
	
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GamePanel.getGsm().addAndPop(2);
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_ALT_GRAPH) {
			looting = false;
		}
		
		return false;
	}

	public void heal(int i) {
		if(health_points + i > max_hp) {
			health_points = max_hp;
		} else {
			health_points += i;
		}
	}
	
	public String[] getPlayerInfo() {
		float posx = pos.x;
		float posy = pos.y;
		float worldX = pos.getWorldVar().x;
		float worldY = pos.getWorldVar().y;
		Vector2i tileX = TileCoordinate.getCurrentTile(pos);
		return new String[]{"XPos: " + posx, "YPos: " + pos.y, "WorldVarX: " + worldX, "WorldVarY: " + worldY, "TileX " + tileX.x, "TileY " + tileX.y};		
	}
	
	
}
