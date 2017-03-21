package com.ibm.fx.mq;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;





public class Main {/*
	public static void main(String[] args) {
		System.out.println("starting main...");
		
		try {
			Runtime.getRuntime().exec("cmd /c start C:\\sh_ravneet\\code_DB2\\app.bat");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			

		//Timer timer=new Timer();
		//timer.schedule(new MyTimerTask(),new Date(new Date().getTime()+5*1000) , 10*1000);
		args=new String[1];
		args[0]="askoption";
		if(args==null || args.length==0 || args[0].equals("sequence"))
		{
			runInSequence();	
			System.exit(0);
		}
		else if(args[0].equals("askoption"))
		{
			askOption();
		}
		else if(args[0].equals("schedular"))
		{
			int delay=Integer.parseInt(args[1]);
			while(true)
			{
				runInSequence();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("ending main...");
	}
	
	
	private static void runInSequence()
	{
		try {
			System.out.println("Creating Accounts .......");
			new CreateAccount().createAccount();
			System.out.println("Creating Services .......");
			new CreateServiceWith_RC_or_NRC().createServiceWith_RCs_or_NRCs();
			System.out.println("Creating ServiceNRC .......");
			
			
			
		} catch (FXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void askOption()
	{
		char ch='0';
		do
		{
			try {
				
				System.out.println("Please Select A Option :-");
				System.out.println("Account - 1");
				System.out.println("Service - 2");
				System.out.println("NRC - 3");
				System.out.println("Packages & Components - 4");
				System.out.println("Delete Packages - 5");
				System.out.println("Exit - 0");
				System.out.println("Enter option :");
				
				int a=System.in.read();
				System.in.skip(2);
				ch=(char)a;
				
				switch(ch)
				{
					case '1' : {
									System.out.println("Creating Accounts .......");
									new CreateAccount().createAccount();
									break;
								}
					case '2' : {
									System.out.println("Creating Services .......");
									new CreateServiceWith_RC_or_NRC().createServiceWith_RCs_or_NRCs();
									break;
								}
				
					default	:	{
									System.out.println("Invalid option ...");
									continue;
								}
				
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(ch!='0');
	}
	
}

class MyTimerTask extends TimerTask
{
	
	@Override
	public void run() {
		System.err.println("Job Start at :"+new Date());
		
	
		
		System.err.println("Job End at :"+new Date());
		
	}*/
	
}