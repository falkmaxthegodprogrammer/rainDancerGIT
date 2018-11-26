package com.cmbstnengine.game.states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.audio.AudioPlayer;
import com.cmbstnengine.game.entity.Enemy;
import com.cmbstnengine.game.entity.Entity;
import com.cmbstnengine.game.entity.NPC;
import com.cmbstnengine.game.entity.Particle;
import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.Projectile;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.graphics.GFont;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.menu.Menu;
import com.cmbstnengine.game.tiles.TileManager;
import com.cmbstnengine.game.ui.UI;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.TileCoordinate;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.world.WorldMap;

public class PlayState extends GameState implements EventListener {

	private GFont font;
	public static Player player;
	private TileManager tm;
	private Menu m;
	public static UI ui;
		
	public static Vector2f map;
	public static Vector2f spawnPoint;
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<TileObject> tileObjects = new ArrayList<>();
	public static ArrayList<NPC> npcs = new ArrayList<>();
	
	public static WorldMap worldMap;
	
	public static boolean loading = false;
	
	AudioClip bg_music_1;
	
	public static int player_size = 96;
	
	public BufferedImage miniMapImage;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
					
		bg_music_1 = new AudioClip("res/music/bgmusic_chill.wav");
		AudioPlayer.playSound(bg_music_1, 0.52);
		
		spawnPoint = new Vector2f(80, 80);
		map = new Vector2f(spawnPoint.x, spawnPoint.y);
		Vector2f.setWorldVar(map.x, map.y);
		worldMap = new WorldMap("maps/arcademap_newtilesheet.xml");
	
		//tm = new TileManager("maps/worldmaptest.xml");
		//tm = new TileManager("maps/boundstest.xml");
		//tm = new TileManager("maps/arcademap.xml");
		//tm = new TileManager("maps/coltestbbox.xml");
		//tm = new TileManager("maps/arcademap.xml");
		//tm = new TileManager("maps/zombieshooter.xml");
		//tm = new TileManager("maps/cthulustemple.xml");
		
		font = new GFont("font/font.png", 10, 10);
		//player = new Player(new Sprite("entity/mageSheet_swimmer.png", 64, 64), new Vector2f(0 + (GamePanel.width / 2) - player_size / 2, 0 + (GamePanel.height / 2) - player_size / 2), player_size);
		player = new Player(new Sprite("entity/lady10bowtest.png", 96, 96), new Vector2f(spawnPoint.x + ((GamePanel.width / 2)) - player_size / 2, spawnPoint.y + ((GamePanel.height / 2) - player_size / 2)), player_size);

		NPC n1 = new NPC(new Sprite("entity/linkformatted.png", 32, 32), TileCoordinate.getTileCoordinate(17, 11), 64, "Link", false);
		NPC n2 = new NPC(new Sprite("entity/mageSheet_BLUE.png", 64, 64), TileCoordinate.getTileCoordinate(15, 11), 64, "Mage:Blue", true);

		npcs.add(n1);
		npcs.add(n2);

		ui = new UI();
	
		players.add(player);

		m = new Menu(1280 - 350, 0, 350);
	}
	
	public static void spawnEnemies() {
		Enemy e1 = new Enemy(new Sprite("entity/mage_GHOST.png", 64, 64), new Vector2f(600, 400), 256);
		Enemy e2 = new Enemy(new Sprite("entity/mage_GHOST.png", 64, 64), new Vector2f(400, 400), 256);
		Enemy e3 = new Enemy(new Sprite("entity/mage_GHOST.png", 64, 64), new Vector2f(350, 400), 128);
		Enemy e4 = new Enemy(new Sprite("entity/mage_GHOST.png", 64, 64), new Vector2f(350, 500), 512);
		enemies.add(e1);
		enemies.add(e2);
		enemies.add(e3);
		enemies.add(e4);
	}
	
	public void update() {
		Vector2f.setWorldVar(map.x, map.y);
		player.update();
		worldMap.update(player);
		updateEntities();
		for(Enemy e : enemies) {
			e.update(player);
		}
		for(NPC npc : npcs) {
			npc.update(player);
		}
		ui.update();					
		if(PlayState.map.x < 0) PlayState.map.x = 0;
		if(PlayState.map.y < 0) PlayState.map.y = 0;
	}

	public static void setWorldPos(int x, int y) {
		PlayState.map.x = x;
		PlayState.map.y = y;
			if(PlayState.map.x < 0) {
				PlayState.map.x = 0;
			}
			if(PlayState.map.y < 0) {
				PlayState.map.y = 0;
			}
		player.getPos().x = x + GamePanel.width / 2 - player_size / 2; 
		player.getPos().y = y + GamePanel.height / 2 - player_size / 2;
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		player.input(mouse, key);
		m.input(key, mouse);
		ui.input(mouse, key);
	}

	public void render(Graphics2D g) {	
		
		worldMap.renderBottomLayers(g);
		for(int i = 0; i < tileObjects.size(); i++) {
			tileObjects.get(i).render(g);
		}
		for(NPC npc : npcs) {
			npc.render(g);
		}	
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(g);
		}

		
		if(!loading) {
			player.render(g);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);;
		}
		renderEntities(g);
		worldMap.renderTopLayers(g);
	
		ui.render(g);
		//Sprite.drawArray(g, font, "HP:" + player.getHP(), new Vector2f(0, 0), 16, 16);
		//Sprite.drawArray(g, font, "MANA:" + player.getMana(), new Vector2f(0, 18), 16, 16);
	}
		
	public static void add(Entity e) {
		if(e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if(e instanceof TileObject) {
			tileObjects.add((TileObject) e);
		} else {
			entities.add(e);
		}
	}
	
	private void remove() {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).isRemoved()) entities.remove(i);
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).isRemoved()) projectiles.remove(i);		
		}
		
		for(int i = 0; i < particles.size(); i++) {
			if(particles.get(i).isRemoved()) particles.remove(i);		
		}	
		
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).isRemoved()) enemies.remove(i);
		}
		
		for(int i = 0; i < tileObjects.size(); i++) {
			if(tileObjects.get(i).isRemoved()) tileObjects.remove(i);
		}	
	}
	
	public void updateEntities() {	
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}

		remove();		
	}
	
	public void renderEntities(Graphics2D g ) {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}	
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void onEvent(Event event) {
		ui.onEvent(event);
		player.onEvent(event);
	}
	
}
