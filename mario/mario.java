import java.applet.*;
import java.awt.*;
import java.awt.event.*;
/*<applet code="mario" width=448 height=429>
</applet>*/
public class mario extends Applet implements Runnable,KeyListener
{
	int xp,yp,xx,lastx,lasty;int movd,mova;char mp,mr,lastk;
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
		d=getSize();
		w=d.width;
		h=d.height;
		xp=1;yp=2;xx=1;mp='z';mr='z';
		
		msg=" ";
		
		movd=0;mova=0;
		sel=0;flag=0;
		
		img=new Image[7];
		for(int i=0;i<7;i++)
			img[i]=getImage(getDocumentBase(),(i+".jpg"));
		
		obj_wall_no=50;
		obj_wall=new int[w/wm][h/hm];//w/wm=14
		for(int i=0;i<w/hm;i++)////////h/hm=13
			for(int j=0;j<h/wm;j++)
			{	
				
				if(i==0)obj_wall[i][j]=1;
				if(i==6 && j>h/(wm*2))obj_wall[i][j]=1;
				if(i==9 && j<h/(wm*2))obj_wall[i][j]=1;
				if(i==11 && j>h/(wm*2))obj_wall[i][j]=1;
				obj_wall[8][3]=1;obj_wall[8][5]=1;obj_wall[8][7]=1;
			}			

			
		addKeyListener(this);
		requestFocus();
		t=new Thread(this);
		t.start();
	}
	public void run()
	{
		int ground=0;
		while(true)
		{
			try{
			//xx=xp/32;
			xx=(xp+wm/2)/32;
			if(obj_wall[yp+1][xx]!=1)yp=yp+1;
			if(mp=='z'){}
			else if(mp=='d')
			{
				if(obj_wall[yp][xx+1]!=1){xp+=3;xx=(xp+wm/2)/32;}			
				
				if(flag==0){sel=2;flag=1;}
				else if(flag==1){sel=0;flag=0;}
				
			}
			else if(mp=='a')
			{
				
				if(obj_wall[yp][xx-1]!=1){xp-=3;xx=(xp+wm/2)/32;}			
				
				if(flag==0){sel=3;flag=1;}
				else if(flag==1){sel=1;flag=0;}
				
			}
			else if(mp=='w')
			{
				int dd=movd,aa=mova;
				for(int k=0;k<=7;k++)
				{
					if(sel==0 || sel==2 || sel==4)sel=4;
					else sel=5;
					if(k<4)
					{
						if(dd==1 || movd==1){xp+=wm/3;xx=(xp+wm/2)/32;}
						else if(aa==1 || mova==1){xp-=wm/3;xx=(xp+wm/2)/32;}
						yp-=1;
						if(obj_wall[yp-1][xx]==1)k=4;
					}
					else 
					{
						if(dd==1 || movd==1){xp+=wm/3;xx=(xp+wm/2)/32;}
						else if(aa==1 || mova==1){xp-=wm/3;xx=(xp+wm/2)/32;}
						yp+=1;
						if(obj_wall[yp+1][xx]==1)break;
					}
					repaint();
					Thread.sleep(80);
				}
				if(sel==4)sel=0;
				else sel=1;
				mp='z';
				
			}
				
			repaint();
			Thread.sleep(30);}catch(InterruptedException e){}
		}
	}
	public void paint(Graphics g)
	{
		/*g.setColor(Color.white);
		g.fillRect(lastx,lasty,32,33);*/
		
		for(int i=0;i<w/hm;i++)
			for(int j=0;j<h/wm;j++)
			if(obj_wall[i][j]==1)
			g.drawImage(img[6],j*wm,i*hm,null);
		
		g.drawImage(img[sel],xp,yp*hm,null);
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

		