import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class mario extends Applet implements Runnable,KeyListener
{
	int xp,yp,lastx,lasty;int movd,mova;char mp,mr,lastk;
	int screen_end_flag,flag,state;
	int w,h;
	int wm,hm;
	Dimension d;
	Image img[];int sel;
	Thread t=null;
	String msg;
	int obj_wall[][],obj_wall_no;
	
	public void init()
	{
		wm=32;hm=33;

		msg=" ";
		d=getSize();
		w=d.width;
		h=d.height;
		xp=50;yp=50;mp='z';mr='z';

		movd=0;mova=0;

		img=new Image[7];
		for(int i=0;i<7;i++)
			img[i]=getImage(getDocumentBase(),(i+".jpg"));
		sel=0;
		
		addKeyListener(this);
		requestFocus();
		
		flag=0;
		
		obj_wall_no=50;
		obj_wall=new int[w][h];
		for(int i=0;i<w;i++)
			for(int j=0;j<h;j+=32)
			{	
				if(i==100)obj_wall[j][i]=1;
			}			

			
			
				
		

		t=new Thread(this);
		t.start();
	}
	public void run()
	{
		while(true)
		{
			try{
			
			if(mp=='z'){}
			else if(mp=='d')
			{
				xp+=3;			
				if(flag==0){sel=2;flag=1;}
				else if(flag==1){sel=0;flag=0;}
				
			}
			else if(mp=='a')
			{
				xp-=3;			
				if(flag==0){sel=3;flag=1;}
				else if(flag==1){sel=1;flag=0;}
				
			}
			else if(mp=='w')
			{
				int dd=movd,aa=mova;
				for(int k=0;k<=7;k++)
				{
					if(dd==1)sel=4;
					else sel=5;
					if(k<4)
					{
						if(dd==1 || movd==1)xp+=wm/3;
						else if(aa==1 || mova==1)xp-=wm/3;
						yp-=hm;
						
					}
					else 
					{
						if(dd==1 || movd==1)xp+=wm/3;
						else if(aa==1 || mova==1)xp-=wm/3;
						yp+=hm;
					}
					if(stable()==0)break;
					repaint();
					Thread.sleep(80);
				}
				if(dd==1)sel=0;
				else sel=1;
				mp='z';
				
			}
				
			repaint();
			Thread.sleep(30);}catch(InterruptedException e){}
		}
	}
	int stable()
	{
		state=3;
		for(int i=0;i<obj_wall_no;i++)
		{
			if(xp+wm/2>=obj_wall[i][0] && xp<=obj_wall[i][0]+wm && yp+hm==obj_wall[i][1])
			{
				return(0);
			}
		}
			
		return state;
	}	

	public void paint(Graphics g)
	{
		/*g.setColor(Color.white);
		g.fillRect(lastx,lasty,32,33);*/
		
		for(int i=0;i<w;i++)
			for(int j=0;j<h;j++)
			if(obj_wall[i][j]==1)
			g.drawImage(img[6],i,j,null);
		
		g.drawImage(img[sel],xp,yp,null);
		g.drawString(msg,10,10);
		lastx=xp;
		lasty=yp;
	}
	/*public void update(Graphics g)
	{
		paint(g);
	}*/
///////////////////////////////////////////////////////////////////////
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		mp=e.getKeyChar();
		if(mp=='d')movd=1;
		if(mp=='a')mova=1;
		msg+=mp+" press ";
		lastk=mp;
	}
	public void keyReleased(KeyEvent e)
	{
		mr=e.getKeyChar();
		msg+=mr+"release ";
		
		if(mr=='d'){sel=0;mp='z';}
		else if(mr=='a'){ sel=1;mp='z';}
		movd=0;mova=0;
		
	}
}

		