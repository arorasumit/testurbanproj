package com.ibm.ioes.ei.m6;

import java.util.TimerTask;

public class SchedulerForLineCancellation extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new SendCancellationXmlToM6().sendToCancellationM6Job();
	}

}
