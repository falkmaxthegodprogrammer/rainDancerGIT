package com.cmbstnengine.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.entity.NPC;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.entity.items.Item;
import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.events.EventDispatcher;
import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.events.types.KeyPressedEvent;
import com.cmbstnengine.game.events.types.KeyReleasedEvent;
import com.cmbstnengine.game.events.types.MouseMovedEvent;
import com.cmbstnengine.game.events.types.MousePressedEvent;
import com.cmbstnengine.game.events.types.MouseReleasedEvent;
import com.cmbstnengine.game.events.types.MouseWheelMovedEvent;
import com.cmbstnengine.game.graphics.GFont;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.ui.components.GActionListener;
import com.cmbstnengine.game.ui.components.GButton;
import com.cmbstnengine.game.ui.components.GLabel;
import com.cmbstnengine.game.ui.components.GPanel;
import com.cmbstnengine.game.ui.components.GProgressBar;
import com.cmbstnengine.game.ui.components.GScrollButton;
import com.cmbstnengine.game.ui.components.GScrollPane;
import com.cmbstnengine.game.ui.components.GUIManager;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class UI implements EventListener {

	private GFont font;
	
	private int skillBarLength;
	private int skillSlotLength;
	private int skillBarHeight;
	private int skillBarPosX;
	private int skillBarPosY;
	
	private int miniMapPosX;
	private int miniMapPosY;
	private int miniMapWidth;
	private int miniMapHeight;
	
	private int dialogBoxWidth;
	private int dialogBoxHeight;
	private int dialogPosX;
	private int dialogPosY;
	
	public static boolean showDialogFrame = false;
	public static String lastMessageDialog;
	
	
	private GUIManager gui;
	private GButton button1;
	private GButton button2;
	private GButton button3;
	private GButton button4;

	private GPanel miniMapPanel;
	private GPanel mainRightPanel;
	private GPanel mineCraftPanel;
	private GPanel statsPanel;
	private static GPanel dialogPanel;
	
	private GPanel inventoryPanel;
	private GPanel inventoryLabelPanel;
	private GButton[] inventoryLabelBtns;
	
	private GPanel characterSheet;
	private GPanel skillSheet;
	private GPanel professionSheet;
	private GPanel portraitContainer;
	
	private Color mainAlphaColor = new Color(0, 0, 0, 230);
	
	public static Vector2f[] uiAreas;
	public static int mainPanelWidth;
	public static int mainPanelHeight;
		
	private GLabel uiLabel;
	private GLabel hpLabel;
	private GLabel manaLabel;
	private GLabel inventoryLabel;
	
	private GProgressBar hpBar;
	private GProgressBar manaBar;
	private GProgressBar staminaBar;

	public static boolean draggingIcon = false;
	public static BufferedImage dragIcon;
	
	public static ArrayList<? extends TileObject> tempObjects = new ArrayList<TileObject>();
	public static ArrayList<? extends Item> tempItems = new ArrayList<>();

	private BufferedImage bgImage1;
	private BufferedImage bgImage2;
	private BufferedImage bgImage3;
	private BufferedImage mineCraftImage;
	private BufferedImage mineCraftBtnHighlighted;
	private BufferedImage button1img;
	private BufferedImage button1highlighted;
	private BufferedImage inventoryBtnImg;
	private BufferedImage inventoryBtnImgHighlighted;
	private BufferedImage portraitContainerImg;
	private final BufferedImage uiOverlay = loadIMG("ui/woodRPG/uioverlay2.png");
	private final BufferedImage ladyPortrait = loadIMG("ui/woodRPG/ladyportrait.png");
	private final BufferedImage statsOverlay = loadIMG("ui/woodRPG/statspaneloverlay.png");
	
	public static GScrollButton[] mineCraftButtons;
	
	private static Font hpFont = new Font("Helvetica", Font.PLAIN, 10);

		
	public UI() {
		mineCraftImage = loadIMG("ui/woodRPG/minecraftButton.png");
		bgImage1 = loadIMG("ui/woodRPG/wood background.png");
		bgImage2 = loadIMG("ui/woodRPG/paper background.png");
		bgImage3 = loadIMG("ui/woodRPG/gray background.png");
		button1img = loadIMG("ui/woodRPG/button1.png");
		button1highlighted = loadIMG("ui/woodRPG/button1highlighted.png");
		mineCraftBtnHighlighted = loadIMG("ui/woodRPG/minecraftBtnHighlighted.png");
		inventoryBtnImg = loadIMG("ui/woodRPG/inventoryBtn1.png");
		inventoryBtnImgHighlighted = loadIMG("ui/woodRPG/inventoryBtn1Highlighted.png");
		portraitContainerImg = loadIMG("ui/woodRPG/portraitContainer.png");
		
		font = new GFont("font/font.png", 10, 10);
		this.skillBarLength = GamePanel.width / 2 + GamePanel.width / 4;	
		this.skillSlotLength = skillBarLength / 16;
		this.skillBarHeight = 45;
		this.skillBarPosX = (GamePanel.width - skillBarLength) / 2;
		this.skillBarPosY = GamePanel.height / 2 + GamePanel.height / 3 + GamePanel.height / 16;
		
		this.miniMapWidth = 120;
		this.miniMapHeight = 140;
		this.miniMapPosX = GamePanel.width - miniMapWidth;
		this.miniMapPosY = GamePanel.height + GamePanel.height / 12;
		
		this.dialogBoxWidth = GamePanel.width / 2 + GamePanel.width / 4;
		this.dialogBoxHeight = GamePanel.height / 4;
		this.dialogPosX = (GamePanel.width - dialogBoxWidth) / 2;
		this.dialogPosY =  GamePanel.height / 2 + GamePanel.height / 8;
		
		miniMapPanel = new GPanel(new Vector2i(GamePanel.width - 150, 2), new Vector2i(150, 140));
		miniMapPanel.setColor(mainAlphaColor);
		mainRightPanel = new GPanel(new Vector2i(GamePanel.width - 130, 155), new Vector2i(130, 330));
		mainRightPanel.setColor(mainAlphaColor);
		
		/*
		 * PORTRAIT-CONTAINER CODE
		 * 
		 */
		
		portraitContainer = new GPanel(new Vector2i(20, 20), new Vector2i(200, 64));
		portraitContainer.showBorder(false);	
		portraitContainer.setBGImage(portraitContainerImg);
		
		hpBar = new GProgressBar(new Vector2i(92, 26), new Vector2i(104, 12));
		hpBar.setProgress(1.0);
		
		staminaBar = new GProgressBar(new Vector2i(92, 46), new Vector2i(104, 12));
		staminaBar.setProgress(1.0);
		staminaBar.setColor(new Color(0x6DAA2C));
		
		manaBar = new GProgressBar(new Vector2i(92, 66), new Vector2i(104, 12));
		manaBar.setProgress(1.0);
		manaBar.setColor(new Color(0x597DCE));
		
		portraitContainer.addComponent(hpBar);
		portraitContainer.addComponent(staminaBar);
		portraitContainer.addComponent(manaBar);
		
		/* 
		 * MINECRAFT-PANEL CODE
		 * 
		 */
		
		mineCraftPanel = new GPanel(new Vector2i(skillBarPosX, skillBarPosY), new Vector2i(skillBarLength, skillBarHeight));
		mineCraftPanel.setRenderMe(false);
		mineCraftPanel.showBorder(false);
		mineCraftButtons = new GScrollButton[16];
		
		for(int i = 0; i < mineCraftButtons.length; i++) {
			int buttonWidth = skillBarLength / 16;
			GScrollButton button = new GScrollButton(new Vector2i(skillBarPosX + buttonWidth * i, skillBarPosY), new Vector2i(buttonWidth, skillBarHeight), button1img);
			mineCraftButtons[i] = button;	
			mineCraftButtons[i].setHighlightedImage(mineCraftBtnHighlighted);	
			mineCraftButtons[i].setActionListenerMinecraft();
		}
			
		mineCraftButtons[0].toggleSelected();
		
		/* 
		 * INVENTORY PANEL CODE
		 * 
		 */
		
		inventoryPanel = new GPanel(new Vector2i(skillBarPosX + skillBarLength / 12, 100), new Vector2i(skillBarLength - skillBarLength / 6, GamePanel.height - 200));
		inventoryPanel.setColor(mainAlphaColor);
		inventoryPanel.showBorder(true);

		statsPanel = new GPanel(new Vector2i(skillBarPosX + skillBarLength / 12, 100), new Vector2i(skillBarLength / 5 - 1, GamePanel.height - 200));
		inventoryPanel.addPanel(statsPanel);
		statsPanel.setColor(mainAlphaColor);
		//inventoryPanel.setBGImage(bgImage2);
		
		inventoryLabelPanel = new GPanel(new Vector2i(skillBarPosX + skillBarLength / 12 + skillBarLength / 5, 100), new Vector2i(skillBarLength - skillBarLength / 6 - skillBarLength / 5, 25));
		inventoryLabelBtns = new GButton[5];
		
		for(int i = 0; i < inventoryLabelBtns.length; i++) {
			inventoryLabelBtns[i] = new GButton(new Vector2i(inventoryLabelPanel.pos.x + (i * inventoryLabelPanel.size.x / 5) + 1, inventoryLabelPanel.pos.y), new Vector2i(inventoryLabelPanel.size.x / 5, 25), inventoryBtnImg);
			inventoryLabelPanel.addComponent(inventoryLabelBtns[i]);	
			inventoryLabelBtns[i].setShowBorder(true);
			inventoryLabelBtns[i].setHighlightedImage(inventoryBtnImgHighlighted);
		}
				
		inventoryLabelBtns[0].setText("ALL ITEMS", Color.white);
		inventoryLabelBtns[1].setText("WEAPONS", Color.white);
		inventoryLabelBtns[2].setText("APPAREL", Color.white);
		inventoryLabelBtns[3].setText("CRAFTING", Color.white);
		inventoryLabelBtns[4].setText("QUEST ITEMS", Color.white);

		inventoryPanel.addComponent(inventoryLabelPanel);
		toggleInventoryPanel();
		
		dialogPanel = new GPanel(new Vector2i(dialogPosX, dialogPosY), new Vector2i(dialogBoxWidth, dialogBoxHeight));
		dialogPanel.setColor(mainAlphaColor);
		dialogPanel.setHidden(true);
		
		mainPanelWidth = (int) mainRightPanel.size.x;
		mainPanelHeight = (int) mainRightPanel.size.y;
		
		GScrollPane inventoryScrollPane = new GScrollPane(new Vector2i(inventoryLabelPanel.pos.x, inventoryLabelPanel.pos.y + 28), new Vector2i(inventoryLabelPanel.size.x, 350));
		GPanel inventoryScrollList = new GPanel(new Vector2i(inventoryScrollPane.pos.x, inventoryScrollPane.pos.y), new Vector2i(inventoryLabelPanel.size.x, 480), bgImage1);
		inventoryScrollPane.add(inventoryScrollList);
		
		button1 = new GButton(new Vector2i(mainRightPanel.pos.x, mainRightPanel.pos.y), new Vector2i(mainRightPanel.size.x / 4, 30), button1img, new GActionListener() {
			public void actionPerformed() {
				toggleInventoryPanel();
			}
		});
		
		button1.setHighlightedImage(button1highlighted);
		
		button2 = new GButton(new Vector2i(mainRightPanel.pos.x + mainRightPanel.size.x / 4, mainRightPanel.pos.y), new Vector2i(mainRightPanel.size.x / 4, 30), button1img, new GActionListener() {
			public void actionPerformed() {
				System.out.println("Action on button2 - open Map");
			}
		});
		
		button2.setHighlightedImage(button1highlighted);
		
		button3 = new GButton(new Vector2i(mainRightPanel.pos.x + (mainRightPanel.size.x / 4 * 2), mainRightPanel.pos.y), new Vector2i(mainRightPanel.size.x / 4, 30), button1img, new GActionListener() {
			public void actionPerformed() {
				System.out.println("Action on button3");
			}
		});
		
		button3.setHighlightedImage(button1highlighted);
	
		button4 = new GButton(new Vector2i(mainRightPanel.pos.x + 2 + mainRightPanel.size.x / 4 * 3 - 1, mainRightPanel.pos.y), new Vector2i(mainRightPanel.size.x / 4 - 1, 30), button1img, new GActionListener() {
			public void actionPerformed() {
				System.out.println("Action on button4");
			}
		});
		
		button4.setHighlightedImage(button1highlighted);
		
		inventoryLabel = new GLabel(new Vector2i(mainRightPanel.pos.x + 64, statsPanel.pos.y + 100), "Inventory");
		
		mainRightPanel.addComponent(button1);
		mainRightPanel.addComponent(button2);
		mainRightPanel.addComponent(button3);
		mainRightPanel.addComponent(button4);
		
		
		for(int i = 0; i < mineCraftButtons.length; i++) {
			mineCraftPanel.addComponent(mineCraftButtons[i]);
		}
		
		mineCraftPanel.setColor(0xFFFFFF);
		
		gui = new GUIManager();
		gui.addPanel(miniMapPanel);
	//  miniMapPanel.addComponent(miniMap);
		
		gui.addPanel(mainRightPanel);	
	
		gui.addPanel(mineCraftPanel);
		gui.addPanel(dialogPanel);
		gui.addPanel(statsPanel);
		gui.addPanel(portraitContainer);

		//gui.addScrollPane(inventoryScrollPane);
	
		portraitContainer.addComponent(hpBar);
		portraitContainer.addComponent(manaBar);
		portraitContainer.addComponent(staminaBar);

		gui.addPanel(inventoryPanel);
		statsPanel.addComponent(inventoryLabel);
		statsPanel.setRenderMe(false);	
	}
	
	public static void setDragItems(BufferedImage icon, ArrayList<TileObject> list) {
		dragIcon = icon;
		tempObjects = list;
	}

	public static void setDragItems2(BufferedImage icon, ArrayList<Item> list) {
		dragIcon = icon;
		tempItems = list;
	}
	
	public static void toggleDragging() {
		if(draggingIcon) {
			draggingIcon = false;
		} else if(!draggingIcon) {
			draggingIcon = true;
		}
	}
	
	public void update() {
		gui.update();
		hpBar.setProgress(PlayState.player.calcRedHpBarPercentage());
	}
	
	public static void toggleDialogFrame() {
		if(showDialogFrame)
			dialogPanel.setHidden(false);
		else
			dialogPanel.setHidden(true);
	}
	
	public GUIManager getGUIManager() {
		return gui;
	}
		
    private BufferedImage loadIMG(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }
	
    public static int getCurrentEquippedBtnSlot() {
    	int index = -1;
    	for(int i = 0; i < mineCraftButtons.length; i++) {
    		if(mineCraftButtons[i].isSelected()) {
    			index = i;
    		}
    	}
    	return index;
    }
    
	public Object getCurrentEquippedObject() {
		for(int i = 0; i < UI.mineCraftButtons.length; i++) {
			if(UI.mineCraftButtons[i].isSelected()) {
				if(UI.mineCraftButtons[i].getItem() != null) {
					return UI.mineCraftButtons[i].getItem();
				}
			}
		}
		return null;
	}
    
	public void input(MouseHandler mouse, KeyHandler key) {
		gui.input();
	}
	
	public void render(Graphics2D g) {	
		g.drawImage(ladyPortrait, portraitContainer.getPos().x + 4, portraitContainer.getPos().y + 4, ladyPortrait.getWidth() * 2, ladyPortrait.getHeight() * 2, null);

		gui.render(g);		
		g.setColor(Color.WHITE);		
		g.drawString("XPos: " + PlayState.player.getPos().x, miniMapPanel.pos.x, miniMapPanel.pos.y + 12);
		g.drawString("YPos: " + PlayState.player.getPos().y, miniMapPanel.pos.x, miniMapPanel.pos.y + 24);
		g.drawString("MapX: " + PlayState.map.x, miniMapPanel.pos.x, miniMapPanel.pos.y + 36);
		g.drawString("MapY: " + PlayState.map.y, miniMapPanel.pos.x, miniMapPanel.pos.y + 48);
		g.drawString("TileX: " + PlayState.player.getCurrentTile().x, miniMapPanel.pos.x, miniMapPanel.pos.y + 60);
		g.drawString("TileY: " + PlayState.player.getCurrentTile().y, miniMapPanel.pos.x, miniMapPanel.pos.y + 72);
		g.drawString("FPS: " + GamePanel.FPS, miniMapPanel.pos.x, miniMapPanel.pos.y + 84);
		g.drawString("Inventory size: " + PlayState.player.inventorySize(), miniMapPanel.pos.x, miniMapPanel.pos.y + 94);

		
		g.setFont(hpFont);
		g.drawString(PlayState.player.getHP() + " / " + PlayState.player.getMaxHP(), portraitContainer.pos.x + portraitContainer.size.x / 2, portraitContainer.pos.y + 15);
		
		g.drawString("120 / 120", portraitContainer.pos.x + portraitContainer.size.x / 2, portraitContainer.pos.y + 35);
		g.drawString("140 / 140", portraitContainer.pos.x + portraitContainer.size.x / 2, portraitContainer.pos.y + 55);
		
		if(!inventoryPanel.isHidden()) {
			g.drawImage(uiOverlay, inventoryPanel.getPos().x - 4, inventoryPanel.getPos().y -4, uiOverlay.getWidth(), uiOverlay.getHeight(), null);
			g.drawImage(statsOverlay, inventoryPanel.getPos().x - 5, inventoryPanel.getPos().y -5, statsOverlay.getWidth(), statsOverlay.getHeight(), null);
		}		
		
		if(draggingIcon && dragIcon != null) {
			g.drawImage(dragIcon, MouseHandler.mouseX - 6, MouseHandler.mouseY, dragIcon.getWidth(), dragIcon.getHeight(), null);
			g.drawString(String.valueOf(tempObjects.size()), (float) MouseHandler.mouseX - 6, (float) MouseHandler.mouseY + 4);
		}
				
	}
	
	public void toggleMainRightPanel() {
		if(mainRightPanel.isHidden()) {
			mainRightPanel.setHidden(false);
		} else if(!mainRightPanel.isHidden()) {
			mainRightPanel.setHidden(true);
		}
	}
	
	public static void hideDialog() {
		UI.showDialogFrame = false;
		dialogPanel.setHidden(true);
	}
	
	public static void showDialog(NPC npc) {
		showDialogFrame = true;
		dialogPanel.setHidden(false);
		String[] lines = npc.getDialogLines();
		System.out.println(lines[0]);
	}

	public void scrollMinecraftButtonsUp() {	
		int index = -1;
		for(int i = 0; i < mineCraftButtons.length; i++) {
			if(mineCraftButtons[i].isSelected()) {
				index = i + 1;
			}
			mineCraftButtons[i].setSelected(false);
		}
		
		if(index == mineCraftButtons.length) {
			index = 0;
		}
		mineCraftButtons[index].setSelected(true);
	}
	
	public void scrollMinecraftButtonsDown() {	
		int index = -1;
		for(int i = 0; i < mineCraftButtons.length; i++) {
			if(mineCraftButtons[i].isSelected()) {
				index = i - 1;
				mineCraftButtons[i].setSelected(false);
			}
		}
		if(index < 0) {
			index = mineCraftButtons.length - 1;
		}
		
		mineCraftButtons[index].setSelected(true);
	}
	
	public static void unselectButtons() {
		for(int i = 0; i < mineCraftButtons.length; i++) {
			mineCraftButtons[i].setSelected(false);
		}
	}
	
	public void toggleInventoryPanel() {
		if(inventoryPanel.isHidden()) {
			inventoryPanel.setHidden(false);
		} else {
			inventoryPanel.setHidden(true);
		}
		inventoryPanel.setPanelsHidden(inventoryPanel.isHidden());
	}
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));	
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPressed((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyReleased((KeyReleasedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSEWHEEL, (Event e) -> onMouseWheelMoved((MouseWheelMovedEvent) e));
	}
	
	public int dragX(int x) {
		return x;
	}
	
	public int dragY(int y) {
		return y;
	}
	
	public boolean mouseClickedOnUIArea() {
		for(GPanel panel : gui.getPanels()) {
			if(!panel.isHidden()) {
				if(MouseHandler.getX() > panel.getPos().x && MouseHandler.getX() < (panel.getPos().x + panel.size.x)
					&& MouseHandler.getY() > panel.getPos().y && MouseHandler.getY() < (panel.getPos().y + panel.size.y)) {
					System.out.println("clicked on UI area");
					return true;
		 		}
			}
		}
		return false;
	}
	
	public boolean onMouseMoved(MouseMovedEvent e) {
		if(draggingIcon) {
			dragX(e.getX());
			dragY(e.getY());
			return true;
		}
		return false;
	}
	
	public boolean onKeyPressed(KeyPressedEvent e) {	
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
			System.out.println("shift");
			return true;
		}	
		if(showDialogFrame) {
			int choice = e.getKeyCode() - 48; 
			System.out.println("Choosing dialog choice" + choice);
		}
		return false;	
	}
	
	public boolean onKeyReleased(KeyReleasedEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_TAB) {
			toggleMainRightPanel();
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			gui.toggleVisible();
			return true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_B) {
			toggleInventoryPanel();
			return true;
		}
		
		return false;
	}
	
	public boolean onMousePressed(MousePressedEvent e) {
		if(draggingIcon) return true;
		
		if(mouseClickedOnUIArea() && !gui.getHidden()) {
			System.out.println("I'm working as an event - mousePressed on UI");
			return true;
		}
		return false;
	}
	
	public boolean mouseOverScrollPane() {
		for(GScrollPane pane : gui.getScrollPanes()) {
			return MouseHandler.getX() > pane.pos.x && MouseHandler.getY() > pane.pos.y
					&& MouseHandler.getX() < pane.pos.x + pane.size.x && MouseHandler.getY() < pane.pos.y + pane.size.y;
		}
		return false;
	}
	
	public boolean onMouseWheelMoved(MouseWheelMovedEvent e) {
		if(mouseOverScrollPane()) {
			System.out.println("true");
			GScrollPane pane = gui.getScrollPane(MouseHandler.getX(), MouseHandler.getY());
			if(e.movedUp()) {
				pane.movePanelDown(10);
				return true;
			} else {
				pane.movePanelUp(10);
				return true;
			}
		}
		
		if(e.movedUp()) {
			scrollMinecraftButtonsUp();
			return true;
		} else if(!e.movedUp()) {
			scrollMinecraftButtonsDown();
			return true;
		}
		return false;
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e) {
		if(MouseHandler.getX() * GamePanel.scaleWidth < miniMapPanel.getBounds().getMinX()) {
			return false;
		}	
		return false;
	}
}
