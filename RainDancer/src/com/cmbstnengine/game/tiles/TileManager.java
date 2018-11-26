package com.cmbstnengine.game.tiles;

import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.entity.entityobjects.TileObjectData;
import com.cmbstnengine.game.entity.entityobjects.TileObjectManager;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.tiles.blocks.Block;
import com.cmbstnengine.game.tiles.blocks.NormBlock;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;



public class TileManager {
		
	public static ArrayList<TileMap> tm;
	public static ArrayList<TileMapObj> tmObjects;
	public static ArrayList<TileObject> entityObjects;
	public static ArrayList<TileObject> tileEntities;
	public static ArrayList<TileMapLights> tmLights;
	public static ArrayList<TileMap> tmUpperLayers;
	
	public static Map<String, Block> worldMap;
	
	public static Sprite spriteSheet;
	
	public TileManager() {
		tm = new ArrayList<TileMap>();
	}
	
	public TileManager(String path, int type) {
		if(type == 0) {
			worldMap = new TreeMap<>();
		}
	}
	
	public TileManager(String path) {
		worldMap = new TreeMap<>();
		tm = new ArrayList<TileMap>();
		tmUpperLayers = new ArrayList<TileMap>();
		tmObjects = new ArrayList<TileMapObj>();
		tileEntities = new ArrayList<TileObject>();
		tmLights = new ArrayList<TileMapLights>();
		addTileMap(path, 64, 64);
	}
	
	public void clearDataStructures() {
	//	for(TileMap t : tm) {
		//	tm.clear();
	//	}
		
		for(TileMapObj to : tmObjects) {
			to.clear();
		}
		
	//	for(TileObject tmo : entityObjects) {
			//tmo.clear();
	//	}
		
	//	for(TileMap tmm : tmUpperLayers) {
		//	tmm.clear();
	//	}
		
		tileEntities.clear();
		tmLights.clear();
		tmUpperLayers.clear();
		tmObjects.clear();
		
	}
	
	public void update(Player player) {

	}
		
	
	public void addTileMap(String path, int blockWidth, int blockHeight) {
		String imagePath;
		
		int width = 0;
		int height = 0;
		int tileWidth;
		int tileHeight;
		int tileCount;
		int tileColumns;
		int layers = 0;
		
		int objectID = 0;
		double x = 0;
		double y = 0;
		int objectWidth = 0;
		int objectHeight = 0;
		int objectGroups = 0;
		int object_count = 0;
		int objectGid = 0;
		
		String objectGroupName;
		String layerName;
		Sprite sprite;
		
		String[] data = new String[10];
		String[] objectData = new String[10];
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
			doc.getDocumentElement().normalize();
			
			NodeList list = doc.getElementsByTagName("tileset");
			Node node = list.item(0);
			Element eElement = (Element) node;
			
			imagePath = eElement.getAttribute("name");
			tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
			tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
			tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
			tileColumns = Integer.parseInt(eElement.getAttribute("columns"));
			spriteSheet = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);
			
			list = doc.getElementsByTagName("layer");
			layers = list.getLength();

			for(int i = 0; i < layers; i++) {
				node = list.item(i);
				eElement = (Element) node;
				layerName = eElement.getAttribute("name");
				if(i <= 0) {
					width = Integer.parseInt(eElement.getAttribute("width"));
					height = Integer.parseInt(eElement.getAttribute("height"));
				}					
				data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();
				Character layerNumber = layerName.charAt(layerName.length() - 1);
				
					if(layerName.equalsIgnoreCase("solid")) {
						tm.add(new TileMapObj(data[i], spriteSheet, width, height, blockWidth, blockHeight, tileColumns));
					} else if(layerName.equalsIgnoreCase("objects")) {
						tmObjects.add(new TileMapObj(data[i], spriteSheet, width, height, blockWidth, blockHeight, tileColumns));
					} else if(layerName.equalsIgnoreCase("lights")) {
						tmLights.add(new TileMapLights(data[i], spriteSheet, width, height, blockWidth, blockHeight, tileColumns));
					} else if(Integer.parseInt(layerNumber.toString()) >= 3) {
						tmUpperLayers.add(new TileMapNorm(data[i], spriteSheet, width, height, blockWidth, blockHeight, tileColumns));
					} 
				 else {
					tm.add(new TileMapNorm(data[i], spriteSheet, width, height, blockWidth, blockHeight, tileColumns));
				}
			}
						
			/* OBJECT PARSING */
			
			list = doc.getElementsByTagName("objectgroup");
			objectGroups = list.getLength();
			
			//System.out.println("Object groups: " + objectGroups);
			objectGroupName = eElement.getAttribute("name");
			//System.out.println("Object group name: " + objectGroupName);
			
			list = doc.getElementsByTagName("object");
			object_count = list.getLength();
			
			for(int i = 0; i < objectGroups; i++) {
				for(int j = 0; j < object_count; j++) {
					node = list.item(j);
					eElement = (Element) node;
					objectID = Integer.parseInt(eElement.getAttribute("id"));
					
					String name = eElement.getAttribute("name");
					String type = eElement.getAttribute("type");

										
					if(!objectGroupName.equalsIgnoreCase("colission")) {
						objectGid = Integer.parseInt(eElement.getAttribute("gid"));
					}
					
					x = Double.parseDouble(eElement.getAttribute("x"));
					y = Double.parseDouble(eElement.getAttribute("y"));
					objectWidth = Integer.parseInt(eElement.getAttribute("width"));
					objectHeight = Integer.parseInt(eElement.getAttribute("height"));

					if(objectGroupName.equalsIgnoreCase("colission")) {
						AABB bbox = new AABB(new Vector2f((int) x, (int) y), objectWidth, objectHeight);
						
					} else {					
						TileObjectData t = new TileObjectData(spriteSheet, new Vector2f((float) x * 2, (float) (y * 2) - 64), (tileWidth * 2), objectGid, tileColumns, tileWidth * 2, tileHeight * 2, objectWidth * 2, objectHeight * 2, name, type);
						TileObjectManager.add(t);
					}
				}
		}
			//tileEntities.add(te);
			//System.out.println(te);

			//tileEntities.add(te);

		} catch(Exception e) {
			System.err.println("ERROR - TileManager! Can not read tilemap");
		};
		
	}
	
    public void render(Graphics2D g) {
    	for(int i = 0; i < tm.size(); i++) {
    		tm.get(i).render(g);
    	}   	
    }
	
    public void renderObjects(Graphics2D g) {
    	for(int i = 0; i < tmObjects.size(); i++) {
    		tmObjects.get(i).render(g);		
    	}
    }
    
    public void renderLights(Graphics2D g) {
    	for(int i = 0; i < tmLights.size(); i++) {
    		tmLights.get(i).render(g);
    	}
    }
    
    public void renderUpperLayers(Graphics2D g) {
    	for(int i = 0; i < tmUpperLayers.size(); i++) {
    		tmUpperLayers.get(i).render(g);
    	}
    }
    
    
 //   public void renderEntityObjects(Graphics2D g) {
 //   	for(int i = 0; i < tileEntities.size(); i++) {
 //   		tileEntities.get(i).render(g);
 //   	}
 //   }
    
    
   	
}
