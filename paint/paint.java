import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.math.*;
import java.net.*;
import java.awt.image.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;

/*<applet code="paint" width=788 height=488>
</applet>*/

public class paint extends Applet implements MouseListener,MouseMotionListener,ActionListener,ItemListener 
{
	int x1,x2,x3,y1,y2,y3,mclick,current,mpress,brushsize,currentcolor;
	String msg=" ";
	int xl[],yl[];
	Button b1,b2,b3,b4,b5,open,save;
	Choice col;
	CheckboxGroup cbg;
	Checkbox c1;
	Checkbox c2;
	Checkbox c3;
	Checkbox c4;
	Label lbl;
	Color mycolor[]={Color.black,Color.green,Color.blue,Color.cyan,Color.pink,Color.orange,Color.yellow,Color.red,Color.magenta};

	Canvas c=null;
	Image img=null;
	Graphics gi=null;
	Graphics gg=null;
	Dimension d=null;Dimension ddd=null;

	public void init()
	{
		open=new Button("OPEN");
		save=new Button("SAVE");
		add(open);add(save);
		open.addActionListener(this);
		save.addActionListener(this);
		
			
		gg=getGraphics();
		Dimension d=getSize();ddd=getSize();
		img=createImage(d.width,d.height-5);
		gi=img.getGraphics();
		gg.drawImage(img,0,0,null);
		

		xl=new int[4];
		col=new Choice();
		yl=new int[4];
		cbg=new CheckboxGroup();
		
		currentcolor=0;
		current=0;
		mclick=0;
		brushsize=1;
		mpress=0;
		
		c1=new Checkbox("1x",cbg,true);
		c2=new Checkbox("4x",cbg,false);
		c3=new Checkbox("8x",cbg,false);
		b1=new Button("LINE");
		b2=new Button("RECTANGLE");
		b3=new Button("ERASER");
		b4=new Button("BRUSH");
		b5=new Button("CLEAR");
		lbl=new Label("BRUSH/ERASER SIZE");

		col.add("default");col.add("GREEN");col.add("BLUE");col.add("CYAN");
		col.add("pink");col.add("orange");col.add("yellow");col.add("red");col.add("magenta");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		col.addItemListener(this);
		c1.addItemListener(this);
		c2.addItemListener(this);
		c3.addItemListener(this);
		
		add(b5);
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(col);
		add(lbl);add(c1);add(c2);add(c3);
	}
	public void start()
	{
		current=0;
		mclick=0;
		mpress=0;
		msg="CURRENT MODE-> none selected";
		gg.drawImage(img,0,0,null); 
	}
	public void paint(Graphics g)
	{
		gg.drawImage(img,0,0,null);
		
		showStatus(msg);
		if(mclick==1)
		{
			gi.setColor(mycolor[currentcolor]);
			if(current==1)
			{	
				gi.drawLine(x1,y1,x2,y2);
				
			} 
			else if(current==2)
			{	
				
				xl[0]=x1;yl[0]=y1;
				xl[1]=x2;xl[2]=x2;xl[3]=x1;yl[1]=y1;yl[2]=y2;yl[3]=y2;
				gi.drawPolygon(xl,yl,4);					
				
			}	 
		}
		if(mpress==1)
		{
			int t=brushsize;
			if(current==3)
			{
				
				gi.setColor(Color.white);
				gi.fillRect(x1-4*t,y1-4*t,8*t,8*t);
				gi.fillRect(x3-4*t,y3-4*t,8*t,8*t);
				
			}
			if(current==4)
			{
				 
				gi.setColor(mycolor[currentcolor]);
				gi.fillRect(x1-4*t,y1-4*t,8*t,8*t);
				gi.fillRect(x3-4*t,y3-4*t,8*t,8*t);
			}
		}
		else if(current==5)
		{
			gi.setColor(Color.white);
			gi.fillRect(0,0,ddd.width,ddd.height-5);
		}
		gg.drawImage(img,0,0,null);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
	public void mouseClicked(MouseEvent e)
	{}
	public void mouseMoved(MouseEvent e)
	{
		if(mpress==1)
		{
			x2=e.getX();
			y2=e.getY();
			repaint();
		}
	}
	public void mouseDragged(MouseEvent e)
	{
		if(mpress==1)
		{
		x3=e.getX();
		y3=e.getY();
		repaint();
		}
	}
	public void mouseEntered(MouseEvent e)
	{}
	public void mouseExited(MouseEvent e)
	{}
	public void mousePressed(MouseEvent e)
	{
		
		mpress=1;
		x1=e.getX();
		y1=e.getY();
		repaint();
	}
	public void mouseReleased(MouseEvent e)
	{
		
		mpress=0;x3=0;y3=0;//x1=0;y1=0;x2=0;y2=0;  //important
		if(mclick==0)
		{
			x2=e.getX();
			y2=e.getY();
			mclick=1;
		}
		else if(mclick==1)
		{
			mclick=0;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1){current=1;msg="CURRENT MODE->**L I N E**";}
		else if(e.getSource()==b2){current=2;msg="CURRENT MODE->**R E C T A N G L E**";} 
		else if(e.getSource()==b3){current=3;msg="CURRENT MODE->**E R A S E R**";} 
		else if(e.getSource()==b4){current=4;msg="CURRENT MODE->**P A I N T  B R U S H**";}
		else if(e.getSource()==b5){current=5;msg="*****C L E A R E D*****";}
		
		else if(e.getSource()==open)		
		{
			FileDialog fopen=new FileDialog(new Frame(),"open dialog",FileDialog.LOAD);
			fopen.setVisible(true);
			
			msg+=fopen.getDirectory();
			msg+=fopen.getFile();
			Image img1=getImage(getDocumentBase(),fopen.getFile());
			gi.drawImage(img1,0,0,null);
			
			repaint();
		}
		else if(e.getSource()==save)
		{
			int w=img.getWidth(null);
			int h=img.getHeight(null);
			int pix[]=new int[w*h];
			PixelGrabber pg=new PixelGrabber(img,0,0,w,h,pix,0,w);
			
			try{pg.grabPixels();}
			catch(InterruptedException ie){}
			
			BufferedImage bimg=null;
			bimg=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			bimg.setRGB(0,0,w,h,pix,0,w);
			
			
			FileDialog fsave=new FileDialog(new Frame(),"save dialog",FileDialog.SAVE);
			fsave.setVisible(true);
			
			try{FileOutputStream fos=new FileOutputStream(fsave.getFile());
			JPEGImageEncoder jpeg=JPEGCodec.createJPEGEncoder(fos);
			jpeg.encode(bimg);
			fos.close();}
			catch( java.io.FileNotFoundException fe){}
			catch( java.io.IOException ee){}
			
		}
		repaint();
	}
	public void itemStateChanged(ItemEvent e)
	{
		currentcolor=col.getSelectedIndex();
		
		Checkbox temp=cbg.getSelectedCheckbox();
		if(temp==c1)brushsize=1;
		else if(temp==c2)brushsize=4;
		else brushsize=8;
		repaint();
	}
	public void update(Graphics g)
	{
		paint(g);
	}	
		
}
	