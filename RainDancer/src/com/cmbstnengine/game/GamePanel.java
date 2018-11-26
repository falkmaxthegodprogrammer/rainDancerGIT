package com.cmbstnengine.game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.audio.MasterVolume;
import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.states.GameStateManager;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;

public class GamePanel extends Canvas implements Runnable, EventListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int width;
	public static int height;
	public static int oldFrameCount;
	public static int oldTickCount;
	
	public static boolean ticked = false;
	public static boolean fullscreen;
	
	private Thread thread;
	private boolean running;
	
	public static BufferedImage img;
	private Graphics2D g;
	
	public static boolean newSecond;
	
	private MouseHandler mouse;
	private KeyHandler key;
	private static GameStateManager gsm;
	public static MasterVolume masterVol;
	
	public static double masterVolume = -0.48;
	
	public static double scaleWidth = 1;
	public static double scaleHeight = 1;
	
	public static int screenWidth;
	public static int screenHeight;
	public static float interpolation;
	
	public static int FPS;
	public int frameCount = 0;
	
	public GamePanel(int width, int height) {
		GamePanel.width = width;
		GamePanel.height = height;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocus();
		setIgnoreRepaint(true);
				
		masterVol = new MasterVolume();

		GamePanel.screenWidth = width;
		GamePanel.screenHeight = height;	
	}

	public static void setMasterVolume(float f) {
		//MasterVolume.setMasterOutputVolume(f);
	}
	
	public synchronized void addNotify() {
		super.addNotify();
		
		if(thread == null) {
			thread = new Thread(this, "GameThread");
			thread.start();
		}
	}
	
	public void init() {
		running = true;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();		
		g.setClip(new Rectangle(width, height));
		gsm = new GameStateManager();
		mouse = new MouseHandler(this, this);
		key = new KeyHandler(this, this);
	}
	
    public void run() {
        init();

            //This value would probably be stored elsewhere.
            final double GAME_HERTZ = 60.0;
            //Calculate how many ns each frame should take for our target game hertz.
            final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
            //At the very most we will update the game this many times before a new render.
            //If you're worried about visual hitches more than perfect timing, set this to 1.
            final int MAX_UPDATES_BEFORE_RENDER = 5;
            //We will need the last update time.
            double lastUpdateTime = System.nanoTime();
            //Store the last time we rendered.
            double lastRenderTime = System.nanoTime();
            
            //If we are able to get as high as this FPS, don't render again.
            final double TARGET_FPS = 60;
            final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
            
            //Simple way of finding FPS.
            int lastSecondTime = (int) (lastUpdateTime / 1000000000);
            
            while (running)
            {
               double now = System.nanoTime();
               int updateCount = 0;
               
                   //Do as many game updates as we need to, potentially playing catchup.
                  while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
                  {
                	 gsm.update();
                	 gsm.input(mouse, key);
                	 lastUpdateTime += TIME_BETWEEN_UPDATES;
                     updateCount++;
                  }
         
                  //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                  //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                  if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                  {
                     lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                  }
               
                  //Render. To do so, we need to calculate interpolation for a smooth render.
            //      float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
 
                  render();
                  draw();
                  lastRenderTime = now;
                  frameCount++;

                  //Update the frames we got.
                  int thisSecond = (int) (lastUpdateTime / 1000000000);
                  if (thisSecond > lastSecondTime)
                  {
                     lastSecondTime = thisSecond;
                     FPS = frameCount;
                     frameCount = 0;
                  }
               
                  //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                  while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
                  {
                     Thread.yield();
                  
                     //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                     //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                     //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
                     try {Thread.sleep(1);} catch(Exception e) {} 
                  
                     now = System.nanoTime();
                  }
               } 
               
          //        now = System.nanoTime();

         }
   // }
    
	public void update() {
		gsm.update();
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		gsm.input(mouse, key);		
	}
	
	@Override
	public void onEvent(Event event) {
		gsm.getCurrentState().onEvent(event);
	}
	
	public void render() {
		if(g != null) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
			gsm.render(g);
		}
	}
	
	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}		
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		if(fullscreen) {
			g2.scale(1.0 * GamePanel.screenWidth / width, 1.0 * GamePanel.screenHeight / height);
		}
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();

		bs.show();		
	}
	
	public static GameStateManager getGsm() {
		return gsm;
	}
	
	public void setFullscreen(boolean fullscreen, int screenWidth, int screenHeight) {
		this.fullscreen = fullscreen;
		if(fullscreen) {
			GamePanel.screenWidth = screenWidth;
			GamePanel.screenHeight = screenHeight;		
			GamePanel.scaleWidth = 1.0 * GamePanel.screenWidth / width;
			GamePanel.scaleHeight = 1.0 * GamePanel.screenHeight / height;
		} else if(!fullscreen) {
			GamePanel.scaleWidth = 1;
			GamePanel.scaleHeight = 1;
			GamePanel.screenWidth = width;
			GamePanel.screenHeight = height;
		}
	}

	public static BufferedImage getLastFrame() {
		BufferedImage imageToReturn = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);		
		for(int y = 0; y < img.getHeight(); y++) {
			for(int x = 0; x < img.getWidth(); x++) {
				imageToReturn.setRGB(x, y, img.getRGB(x, y));
			}	
		}	
		return imageToReturn;	
	}
}
