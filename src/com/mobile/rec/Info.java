package com.mobile.rec;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mobile.constants.ApiConstants;

public class Info {

	//"https://api.stackexchange.com/2.2/info?site=stackoverflow";
	//"https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged=java&site=stackoverflow";
	// "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&site=stackoverflow";
	// http://search.twitter.com/search.json?q=%40apple
	public static String tDate,fDate;
	public static String tDateDisplay,fDateDisplay;
	public static String ITEMS = "items";
	public static String LINK = "link";
	public static String TITLE = "title";
	public static String GET = "GET";
	private String youroption,options;
	private static ArrayList<String> list;
	public static ArrayList<ArrayList<String>> figures;

	public static ArrayList<ArrayList<String>> getFigures() {
		return figures;
	}

	public static ArrayList<String> getList() throws IOException {

		return list;
	}

	public static JsonObject fileToDownload;
	public static String[] split;	
	public static ArrayList<ArrayList<String>> answers ;
	public static ArrayList<ArrayList<String>> getAnswers() {
		//System.out.println("Info get Answers: "+answers.size());
		return answers;
	}

	public static List<String> answersList= null;
	public static ArrayList<String> titles = null;

	public static ArrayList<String> getTitles() {
		return titles;
	}

	public String gettDate() {
		return tDate;
	}

	public String getfDate() {
		//System.out.println("Get From date is : "+fDate);
		return fDate;
	}

	public void setfDate(String fDate) throws ParseException {
		fDateDisplay = fDate;
		fDate = dateFormat(fDate);
		//System.out.println("formated From date is : "+fDate);
		this.fDate = fDate;
	}

	public void settDate(String tDate) throws ParseException {		
		tDateDisplay = tDate;
		tDate = dateFormat(tDate);
		//System.out.println("formated TO date is : "+tDate);
		this.tDate = tDate;
	}

	private String dateFormat(String inputDate) throws ParseException {
		//System.out.println("dateFormat");
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");	
		Date date = dt.parse(inputDate);
		Date stacticDate = dt.parse("01/01/1970");
		//System.out.println("stacticDate date is: "+stacticDate);
		long output = TimeUnit.MILLISECONDS.toSeconds(date.getTime()-stacticDate.getTime()) ;
		//System.out.println(inputDate+" >> input :output <<: "+output);
		String outDate = ""+output;
		return outDate;
	}


	public String getOptions() {
		//System.out.println(" options: "+options);
		return options;
	}

	public void setYouroption(String youroption)  {
		//System.out.println("setyourOptions: "+youroption);
		split = new String[2];
		if(youroption.contains(",")){
			split = youroption.split(",");
			System.out.println(split[0]+" "+split[1]);
		}else if(youroption.contains("Questions")){
			split[0] = "Questions";
			split[1] = null;
		}else{
			split[0] = null;
			split[1] = "Answers";
		}

		this.youroption = youroption;
	}

	public Info(){
	}

	public String execute() throws Exception {	
		list = new ArrayList<String>();
		if(list.size()==0)
			list = getValueRest();
		return "success";
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getYouroption() throws IOException {
		return youroption;
	}

	private static ArrayList getValueRest() throws IOException {
		//String[] value= new String[50];	
		ArrayList<String> value = new ArrayList<String>();
		ApiConstants api = new ApiConstants();
		//System.out.println("ApiConstants.QUESTIONS: "+ api.QUESTIONS);
		URL url = new URL(ApiConstants.QUESTIONS);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		//System.out.println(request.getContentEncoding());
		InputStream jsonContent = new BufferedInputStream(new GZIPInputStream(request.getInputStream()));
		value = JsonReaderQuestions(jsonContent);
		return value;
	}

	private static ArrayList JsonReaderQuestions(InputStream jsonContent) throws IOException {
		JsonParser parser = new JsonParser();
		//System.out.println("JsonReaderQuestions");
		titles = new ArrayList<String>();
		int count =0;
		ArrayList<String> httpcontent = new ArrayList<String>();
		JsonObject jsonObj,innerObj;
		try {

			jsonObj = (JsonObject)parser.parse(new InputStreamReader(
					jsonContent, "UTF-8"));
			fileToDownload = jsonObj;
			JsonArray lang= (JsonArray) jsonObj.get(ITEMS);
			String[] quesLinks = new String[lang.size()] ;
			Iterator i = lang.iterator();
			while(i.hasNext()){
				innerObj = (JsonObject) i.next();
				
				quesLinks[count] = innerObj.get(LINK).toString().replace('"', ' ').trim();
				System.out.println(quesLinks[count]);
				titles.add(innerObj.get(TITLE).toString().replace('"', ' ').trim());
				System.out.println("titles "+count+": "+titles.get(count));
				count++;				
			}			
			httpcontent = HttpParser(quesLinks); // used for parsing HTTP content

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpcontent;
	}

	public static void setAnswersList(List<String> answersList) {
		Info.answersList = answersList;
	}

	public static void setTitles(ArrayList<String> titles) {
		Info.titles = titles;
	}

	private static ArrayList HttpParser(String[] quesLink) throws IOException {

		ArrayList<String> out = new ArrayList<String>();
		out = getHTML(quesLink);		
		return out;
	}

	public static ArrayList getHTML(String[] url) throws IOException 
	{		
		figures = new ArrayList<ArrayList<String>>();
		System.out.println("getHTML");
		System.out.println("split[0]: "+split[0]+" split[1]: "+split[1]);
		ArrayList<String> qList = new ArrayList<String>();
		URL u;
		InputStream is = null;
		DataInputStream dis;
		String s;
		StringBuffer sb ;
		int qCount = 1;
		answers = new ArrayList<ArrayList<String>>();
		for(int i=0;i<url.length;i++,qCount++){
			sb = new StringBuffer();
			u = new URL(url[i]);
			is = u.openStream();
			dis = new DataInputStream(new BufferedInputStream(is));
			while ((s = dis.readLine()) != null)
			{
				sb.append(s+"\n");
			}
			
			if(split[0]!=null && split[1]!=null){
				qList.add(Questions(sb.toString(),qCount));				
				answers.add(Answers(sb.toString()));
			}else if(split[0]!=null){
				qList.add(Questions(sb.toString(),qCount));
			}else if(split[1]!=null){
				answers.add(Answers(sb.toString()));
				//System.out.println("answers : "+answers.get(i)+"\n\n\n\n");
			}
			GetFigures(sb.toString());
		}
		return qList;
	}

	private static void GetFigures(String input) {		
		ArrayList fig = new ArrayList();		
		String astartPoint1 = "<span itemprop="+'"'+"upvoteCount"+'"'+" class="+'"'+"vote-count-post "+'"'+">";
		String aendPoint1 = "</span>";
		String replace = "<.*?>";
		String nothing = "";
		String[] output = new String[10];
		if(input.contains(astartPoint1)){
			output = StringUtils.substringsBetween(input, astartPoint1, aendPoint1);	
			for(int i=0;i<output.length;i++){				
				String temp = output[i].replaceAll(replace, nothing);
				//System.out.println("temp count: "+temp);
				if(i==0){fig.add("Question votes: "+temp);}
				else{
					fig.add("Answer"+i+" votes: "+temp);
				}
			}
		}		
		
		String astartPoint2 = "<span class="+'"'+"vote-accepted-on load-accepted-answer-date"+'"'+">";
		String aendPoint2 = "</span>";
		if(input.contains(astartPoint2)){
			fig.add("Accepted Answer:  Answer1");
		}else{
			fig.add("Accepted Answer:  None");
		}
		figures.add(fig);
		//System.out.println("figures size: "+figures.size());
		
	}

	private static ArrayList Answers(String input) {
		ArrayList ans = new ArrayList();
		// TODO Auto-generated method stub
		String[] output = new String[10];
		String astartPoint = "<td class="+'"'+"answercell"+'"'+">";
		String aendPoint = "</div>";
		String startAnswer = "\n \n\n Answer";
		String delimiterNewline = "\n\n";
		String delimSpacNew = "\n\\s*\n";
		String newLine = "\n";
		String disp = "";
		if(input.contains(astartPoint)){
			output = StringUtils.substringsBetween(input, astartPoint, aendPoint);	
			for(int i=0,count=1;i<output.length;i++,count++){				
				output[i] = output[i].replaceAll(delimSpacNew, newLine).trim();
				output[i] = output[i].replaceAll(delimiterNewline, newLine).trim();
				output[i] = startAnswer+count+":\n"+output[i]; //+output[i].replaceAll(replace, nothing);
				ans.add( output[i]);
			}
		}
		return ans;
	}

	private static String Questions(String input, int count) {
		String output ="";
		String qstartPoint = "<td class="+'"'+"postcell"+'"'+">";
		String qendPoint = "</td>";
		String delimiterNewline = "\n\n";
		String delimSpacNew = "\n\\s*\n";
		String newLine = "\n";
		String startQuestion = "\n \n\nQuestion";
		String replace = "<.*?>";
		String nothing = "";
		output = StringUtils.substringBetween(input, qstartPoint, qendPoint);		
		output = output.replaceAll(delimSpacNew, newLine).trim();
		output = output.replaceAll(delimiterNewline, newLine).trim();
		output = startQuestion+count+":\n"+output; //+output.replaceAll(replace, nothing);

		/*//retrieving answers part here
		if(split[1]!=null){
			output += Answers(input);
		}*/

		return output;
	}

}
