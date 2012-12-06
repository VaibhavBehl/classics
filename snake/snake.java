import java.awt.*;
import java.awt.event.*;
import java.applet.*;
/*<applet code="snake" width=788 height=600>
</applet>*/
public class snake extends Applet implements Runnable
{
	int pos[][],index,lastx,lasty,w,h,ws,hs,startx,starty;
	char mov;
	Thread t=null;
	int changex,changey,tt;
	Dimension d;
	
	public void init()
	{

		ws=18;hs=18;
		
		d=getSize();
		w=d.width;h=d.height;
		
		tt=1;
		mov='d';
		pos=new int[40][2];
		index=40;
		
		startx=700;starty=300;
		pos[0][0]=startx;pos[0][1]=starty;
		for(int l=1;l<index;l++)
		{
			pos[l][0]=pos[l-1][0]-ws;pos[l][1]=pos[l-1][1];////[][0]->x-axis [][1]->y-axis
		}
				
		addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e)
			{
				mov=e.getKeyChar();
			}
		});
		requestFocus();
		t=new Thread(this);
		t.start();

	}
	public void paint(Graphics g)
	{
		
			
		if(tt==1)
		{
			g.setColor(Color.black);

			for(int i=0;i<index;i++)
			g.fillRect(pos[i][0],pos[i][1],ws,hs);
			tt=0;	
		}

		g.setColor(Color.black);
		g.fillRect(pos[0][0],pos[0][1],ws,hs);
		g.setColor(Color.white);
		g.fillRect(lastx,lasty,ws,hs);
			
			
	}
	public void run()
	{
		while(true)
		{
			try{Thread.sleep(100);}catch(java.lang.InterruptedException e){}
			
			if(mov=='d')
			{
				changex=ws;
				changey=0;
			}
			else if(mov=='w')
			{
				changex=0;
				changey=-hs;
			}
			else if(mov=='a')
			{
				changex=-ws;
				changey=0;
			}
			else if(mov=='s')
			{
				changex=0;
				changey=hs;
			}
			
			lastx=pos[index-1][0];lasty=pos[index-1][1];
			for(int j=index-1;j>0;j--)
			{
				pos[j][0]=pos[j-1][0];
				pos[j][1]=pos[j-1][1];
			}						
			pos[0][0]+=changex;
			pos[0][1]+=changey;
			
			repaint();
			if(pos[0][0]>=w && mov=='d')pos[0][0]=0;
			if(pos[0][0]<=0 && mov=='a')pos[0][0]=w;
			if(pos[0][1]<=0 && mov=='w')pos[0][1]=h;
			if(pos[0][1]>=h && mov=='s')pos[0][1]=0;
					
		}
		
	}
	public void update(Graphics g)
	{
		paint(g);
	}
}		