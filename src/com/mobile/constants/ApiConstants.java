package com.mobile.constants;



import com.mobile.rec.Info;


public class ApiConstants {
	
	static String fDate;
	static String tDate;
	static{
		
		Info info = new Info();
		fDate  = info.getfDate();
		tDate = info.gettDate();
		
		System.out.println(tDate+ " :Apiconstants values f date anf t date: "+fDate);
	}
	
	public static final String key = "2LPKeqys0JRDuPqHMt59MQ";
	public static String QUESTIONS = "https://api.stackexchange.com/2.2/questions?fromdate="+fDate+"&todate="+tDate+"&order=desc&sort=month&tagged=java&site=stackoverflow";
	
}
