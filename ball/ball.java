import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/*<applet code="ball" width=300 height=300>
</applet>*/

public class ball extends Applet implements Runnable,MouseMotionListener
{
	int xp[],yp[],m[],xmax,ymax;
	Thread t1=null;

	public void init()
	{	
		xp=new int[20];
		yp=new int [20];
		m=new int[20];
		xmax=300;ymax=300;
		addMouseMotionListener(this);
		t1=new Thread(this);
		
	}
	public void start()
	{
		for(int i=0;i<20;i++)
		{	xp[i]=15*(2+i);yp[i]=10*(3+i);m[0]=((4-i)+1)/5;}
		
		t1.start();
	}
	public void run()
	{
		while(true)
		{
			try{
			for(int j=0;j<20;j++)
			{
				xp[j]=updx(xp[j],m[j]);
				yp[j]=updy(yp[j],m[j]);
				m[j]=check(xp[j],yp[j],m[j]);
			}
			
			repaint();
			Thread.sleep(4);}catch( java.lang.InterruptedException e){}
		}
	}
	public int check(int xp,int yp,int m)
	{
		if(xp==0)
		{
			if(yp==0 && m==4)m=2;
			else if(yp==ymax && m==3)m=1;
			else if(m==3)m=2;
			else m=1;				 	
		}
		else if(xp==xmax)
		{
			if(yp==0 && m==1)m=3;
			else if(yp==ymax && m==2)m=4;
			else if(m==2)m=3;
			else m=4;
		}
		else if(yp==0)
		{
			if(m==1)m=2;
			else if(m==4)m=3;
		}
		else if(yp==ymax)
		{
			if(m==2)m=1;
			else if(m==3)m=4;
		}
		return(m);
	}
	public int updx(int xp,int m)
	{
			if(m==1)xp++;
			else if(m==2)xp++;
			else if(m==3)xp--;
			else if(m==4)xp--;
			return(xp);
	}
	public int updy(int yp,int m)
	{
			if(m==1)yp--;
			else if(m==2)yp++;
			else if(m==3)yp++;
			else if(m==4)yp--;
			return(yp);
	}
	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e)
	{
		xmax=e.getX();
		ymax=e.getY();
	
		repaint(0,0,xmax-2,ymax-2);
	}	
	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		
		for(int k=0;k<20;k++)
		g.fillOval(xp[k]-5,yp[k]-5,10,10);
		
		g.drawLine(0,ymax,xmax,ymax);
		g.drawLine(xmax,0,xmax,ymax);
	}
}



