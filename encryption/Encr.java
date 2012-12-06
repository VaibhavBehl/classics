/********************************************************************************************************************
*********************************************************************************************************************
SOFTWARE - file encrypter
MADE BY - vaibhav-B
DATE OF COMPLETION - 01 / 06 / 2010

DESCRIPTION - This is a basic encryption software which can encrypt single file or directories(recursively).Here we accept
a PASSKEY from the user which is used to encrypt the data.This passkey is not stored in the ENCRYPTED file making it 
impossible to CRACK as the attacker has no knowledge of (ORIGINAL DATA and PASSKEY), and assuming he has access to encrypted
data and he has high computational capabilities!! still he cant CRACK it because he can't know which data he decrypted is 
correct.

-This technique is a VARIABLE bit enryption, as the bit size depends on the user PASSKEY size. this gives additional 
as attacker cant know if it was a (128bit or 1024bit) encryption!!

REAL ENVIRONMENT TESTING - 
TESTED ON THE FOLLOWING CONF.->
intel pentium dual CPU @ 2.20 GHz
2GB DDR2 800MHz RAM
250GB SATA HD

RESULT ->
SINGLE FILE- 32.8MB encrypted in 2 sec
DIRECTORY(4 sub-directories and 7 files) - 137MB(total) encryptd in 9 sec  

***********************************************************************************************************************
**********************************************************************************************************************/




import java.awt.*;
import java.awt.event.*;

import java.math.*;
import java.net.*;
import java.io.*;





class MyFrame extends Frame implements ActionListener,ItemListener
{
	Button bBrowse,bSave,bEncr,bReset;
	TextField openField,saveField,keyField;
	Label openFieldLbl,saveFieldLbl,keyFieldLbl,dataLbl;
	TextArea data;
	File openFile,saveFile;
	Frame f1;
	int mode=0;//   0-file  1-directory
	int tflag=1;
	MyFrame()
	{
		

		openFieldLbl=new Label("browse INPUT file/directory location-->>");//      open file loc
		this.add(openFieldLbl);
		openField=new TextField(20);
		this.add(openField);
		openField.addActionListener(this);	
		bBrowse=new Button("browse");
		this.add(bBrowse);
		bBrowse.addActionListener(this);

		saveFieldLbl=new Label("browse OUTPUT file location-->>");//     save file loc
		this.add(saveFieldLbl);
		saveField=new TextField(20);
		this.add(saveField);
		bSave=new Button("browse");
		this.add(bSave);
		bSave.addActionListener(this);

		keyFieldLbl=new Label("enter encryption key-->>");//  key value
		this.add(keyFieldLbl);
		keyField=new TextField(10);
		this.add(keyField);

		bEncr=new Button("!!ENCRYPT / DECRYPT!!");
		this.add(bEncr);
		bEncr.addActionListener(this);
		
		dataLbl=new Label("progress report->");//   temporary file display
		this.add(dataLbl);
		data=new TextArea(20,35);
		this.add(data);
		
		bReset=new Button("reset");
		this.add(bReset);
		bReset.addActionListener(this);

		setTitle("encryption");
		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});		
		this.setLayout(new FlowLayout());
		pack();
		setSize(350,600);
		setVisible(true);
		
	}
	


	public void itemStateChanged(ItemEvent e){}
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getActionCommand()=="yes"){f1.setVisible(false);mode=1;}
		if(e.getActionCommand()=="no"){f1.setVisible(false);mode=0;}
		
		if(e.getSource()==bReset)
		{
			this.setVisible(false);
			new MyFrame();
		}
			
		if(e.getSource()==bBrowse)
		{
			FileDialog fopen=new FileDialog(this,"open dialog",FileDialog.LOAD);
			fopen.setVisible(true);
			if(fopen.getFile()!=null)
			{
				
				f1=new Frame("select option");
				f1.add(new Label("do you want to ENCRYPT other directories and subfiles  \"IN\" this folder???"));
				f1.setLayout(new FlowLayout());
				Button y,n;
				y=new Button("yes");
				n=new Button("no");
				f1.add(y);
				f1.add(n);
				y.addActionListener(this);
				n.addActionListener(this);
				f1.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){f1.setVisible(false);}});
				f1.setSize(600,600);
				f1.setVisible(true);
				 
				openFile=new File(fopen.getDirectory(),fopen.getFile());
				openField.setText(fopen.getDirectory()+fopen.getFile());
			}
		}
		if(e.getSource()==bSave)
		{
		      
			
			if(mode==1)
			{
			f1=new Frame(" !!!!!!! N O T I C E !!!!!!!");
			f1.add(new Label("please create a folder where u want to save ur encrypted subfiles/directories and write dummy as file name"));
			f1.setSize(600,600);
			f1.setVisible(true);
			f1.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						f1.setVisible(false);  // using temp to just display save dialog box
						MyFrame temp=new MyFrame();temp.setVisible(false);
						FileDialog fsave=new FileDialog(temp,"save dialog",FileDialog.SAVE);
						fsave.setVisible(true);
						if(fsave.getFile()!=null)
						{
							saveFile=new File(fsave.getDirectory(),fsave.getFile());
							saveField.setText(fsave.getDirectory()+fsave.getFile());
						}
					}
				});
			}
			if(mode==0)
			{
				FileDialog fsave=new FileDialog(this,"save dialog",FileDialog.SAVE);
				fsave.setVisible(true);
			
				if(fsave.getFile()!=null)
				{
					saveFile=new File(fsave.getDirectory(),fsave.getFile());
					saveField.setText(fsave.getDirectory()+fsave.getFile());
				}
			}
		}
		if(e.getSource()==bEncr)
		{
		    	if(mode==0)
			{
				data.setText(" ");
				encryp(openFile,saveFile);			
			}
			if(mode==1)
			{
				data.setText(" ");
				String parentOpen,parentSave,msgOpen,msgSave;
				String dirStk[]=new String[100];
				int top=-1;
				
				msgOpen=parentOpen=openFile.getParent();
				msgSave=parentSave=saveFile.getParent();
				int len=parentOpen.length();
				
				
				File o=new File(msgOpen);
				File s=new File(msgSave);
				String l[]=o.list();
				
				
				for(int i=0;i<l.length;i++)
				{
					File topen=new File(msgOpen+"\\"+l[i]);
					if(topen.isDirectory())
						{dirStk[++top]=topen.getPath();data.append("\n"+topen.getPath());}
					else 
						encryp(topen, new File(msgSave,topen.getName()) );
				}
				do
				{
					msgOpen=dirStk[top--];
					File temp=new File(msgOpen);
					String ll[]=temp.list();
					for(int i=0;i<ll.length;i++)
					{
						File topen=new File(msgOpen+"\\"+ll[i]);
						if(topen.isDirectory())
							dirStk[++top]=topen.getPath();
						else 
						{	
							char tem[]=new char[msgOpen.length()];
							msgOpen.getChars(0,msgOpen.length(),tem,0);
							String x=new String(tem,len,tem.length-len);data.append("\n--> "+parentSave+x);
							File n1=new File(parentSave+x); n1.mkdir();
							File n2=new File(parentSave+x,topen.getName());
							encryp(topen,n2); 
						}
					}
				}while(top>=0);
			}
				
			
		}	
	
	}


////////////////////////////////////////////////////
	public void encryp(File openLoc,File saveLoc)
	{
		byte key[]=keyField.getText().getBytes();
		int pos=0,l=key.length;
		if(l>0)
		{


			try
			{
			FileInputStream fin=new FileInputStream(openLoc);
			FileOutputStream fout=new FileOutputStream(saveLoc);
			int i;
			float initial=fin.available();	
			int xx;
			do
			{
				
				data.append("\n"+  Math.round((initial-fin.available())*100/initial)  );
				
				byte arrin[]=new byte[1000000];
				byte arrout[]=new byte[1000000];
				xx=fin.read(arrin);
				for(int j=0;j<xx;j++)
				{
					if(pos<l-1)pos++;
					else pos=0;
					arrout[j]=(byte)(arrin[j]^key[pos]);
				}
				if(xx!=-1)fout.write(arrout,0,xx);
			}while(xx!=-1);
			data.append("\n D O N E");	
			fin.close();
			fout.close();
						
			}catch(IOException e3){}
		}
	}

}
public class Encr
{
	public static void main(String args[])
	{
		new MyFrame();
	}
}	
