import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public abstract class GameDriverV2
  extends Canvas
{
  protected BufferedImage back;
  protected int FramesPerSecond = 60;
  protected long timer = 1000 / this.FramesPerSecond;
  protected Timer t1 = new Timer();
  
  public GameDriverV2()
  {
    setVisible(true);
    this.t1.scheduleAtFixedRate(new GameDriverV2.ThreadTimer(this), 0L, this.timer);
    
    setFocusable(true);
  }
  
  public void update(Graphics window)
  {
    paint(window);
  }
  
  public void setTimer(int value)
  {
    this.timer = value;
  }
  
  public void paint(Graphics window)
  {
    if (this.back == null) {
      this.back = ((BufferedImage)createImage(getWidth(), getHeight()));
    }
    Graphics2D graphToBack = this.back.createGraphics();
    
    draw(graphToBack);
    
    Graphics2D win2D = (Graphics2D)window;
    win2D.drawImage(this.back, null, 0, 0);
  }
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  private class ThreadTimer
    extends TimerTask
  {
    GameDriverV2 driver;
    
    public ThreadTimer(GameDriverV2 gameDrive)
    {
      this.driver = gameDrive;
    }
    
    public void run()
    {
      this.driver.repaint();
      System.gc();
    }
  }
  
  public class timerDriver
    extends Thread
  {
    int delay;
    
    public timerDriver(int _delayInMiliseconds)
    {
      this.delay = _delayInMiliseconds;
    }
    
    public void run() {}
  }
  
  public BufferedImage addImage(String name)
  {
    BufferedImage img = null;
    try
    {
      img = ImageIO.read(getClass().getResource(name));
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
    return img;
  }
}