package com.mobile.rec;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class Trim {

	public static ArrayList<String> answersList ;
	public static ArrayList<String> QuestionsList ;
	public static ArrayList<String> display;

	public static ArrayList<ArrayList<String>> figureCount;
	public static String count;
	public static String item;
	public static String submit;
	public static String QUESTION = "Q";
	public static String ANSWER = "A";
	public static String CONTENT = "Content";
	public static String CODE = "Code";
	public static String BACK = "GoBack";


	public static String getSubmit() throws Exception {
		//System.out.println("get Submit: "+submit);

		return submit;
	}

	public static ArrayList<String> getFigureCount() {
		figureCount = new ArrayList<ArrayList<String>>();
		System.out.println("figures: "+figureCount.size()+" count: "+count);
		figureCount = Info.getFigures();

		int index = Integer.parseInt(count);
		return figureCount.get(index);
	}

	public static void setSubmit(String submit) {
		//System.out.println("set submit:"+submit);
		Trim.submit = submit;
	}

	public static String getItem() {
		return item;
	}

	public static void setItem(String item) {
		//System.out.println("set Item: "+item);
		Trim.item = item;
	}

	public static String getCount() {
		System.out.println("get count: "+count);
		return count;
	}
	public static void setCount(String count) {
		//System.out.println("set count: "+count);
		Trim.count = count;
	}

	static boolean flag = false;
	public static ArrayList<String> getDisplay() throws Exception {

		//System.out.println("getDisplay: getCount: "+getCount());

		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> qlist = new ArrayList<String>();
		ArrayList<ArrayList<String>> newlist = new ArrayList<ArrayList<String>>();
		display = new ArrayList<String>();
		int index = Integer.parseInt(count);
		System.out.println("getDisplay: index: "+index);

		if(getItem().equalsIgnoreCase(QUESTION)){
			System.out.println("getDisplay: getItem: "+getItem());
			list = Info.getList();
			//System.out.println("list.get(index): \n"+list.get(index));
			qlist.add(list.get(index));
			newlist.add(qlist);
			display= Format(newlist.get(0));
		}else if(getItem().equalsIgnoreCase(ANSWER)){
			System.out.println("getDisplay: getItem: "+getItem());				
			newlist = Info.getAnswers();
			System.out.println("list.size: \n"+list.size());
			//System.out.println("list.get(index): \n"+list.get(index));
			flag = true;
			display= Format(newlist.get(index));
		}else{
			System.out.println("getDisplay: getItem: "+getItem());
			ArrayList<ArrayList<String>> qns = new ArrayList<ArrayList<String>>();
			list = Info.getList();
			//System.out.println("list.get(index): \n"+list.get(index));
			qlist.add(list.get(index));
			qns.add(qlist);
			display.addAll( Format(qns.get(0)));
			newlist = Info.getAnswers();
			flag = true;
			System.out.println("list.size: \n"+list.size());
			//System.out.println("list.get(index): \n"+list.get(index));
			display.addAll(Format(newlist.get(index)));

		}		
		return display;
	}

	private static ArrayList<String> Format(ArrayList<String> arrayList) throws Exception {
		// TODO Auto-generated method stub
		ArrayList output = new ArrayList();
		//System.out.println("format: getsubmit: "+getSubmit());
		String result ="";
		if(getSubmit().equalsIgnoreCase(CODE)){
			output = CodePart(arrayList);
		}else if(getSubmit().equalsIgnoreCase(CONTENT)){
			output = Content(arrayList);
		}else{
			//System.out.println("total code+content:\n"+input);			
			String replace = "<.*?>";
			String nothing = "";
			for(int i=0;i<arrayList.size();i++){
				result = arrayList.get(i).replaceAll(replace, nothing);
				output.add(result);
			}

		}	
		flag =false;
		return output;
	}

	private static ArrayList Content(ArrayList<String> list) {
		//System.out.println("input: \n"+input );
		ArrayList output = new ArrayList();
		String replace = "<.*?>";
		String nothing = "";
		String codeStart = "<code>";
		String codeEnd = "</code>";
		String[] codePart = new String[10];
		for(int j=0;j<list.size();j++){
			String input = list.get(j);
			if(input.contains("<code>")){
				System.out.println("coming!!!!!");
				codePart= StringUtils.substringsBetween(input, codeStart, codeEnd);
				for(int i=0;i<codePart.length;i++ ){
					System.out.println(codePart[i]);
					input = input.replace("<code>"+codePart[i]+"</code>", "");				
				}
			}
			input = input.replaceAll(replace, nothing);
			System.out.println("MODIFIED input: \n"+input );
			output.add(input);
		}
		return output;
	}

	public static ArrayList<String> getQuestionsList()  {

		return QuestionsList;
	}


	public static void setQuestionsList(ArrayList<String> questionsList) {
		QuestionsList = questionsList;
	}


	public static ArrayList<String> getAnswersList() {
		return answersList ;
	}



	private static ArrayList<String> CodePart(ArrayList<String> input)
	{
		System.out.println("CodePart");
		String codeStart = "<pre>";
		String codeEnd = "</pre>";
		String[] codePart = new String[10];
		ArrayList codeList = new ArrayList();
		String delimiterNewline = "\n\n";
		String delimSpacNew = "\n\\s*\n";
		String newLine = "\n";
		for(int i=0,count =1;i<input.size();i++,count++){
			String temp = input.get(i);

			if(temp.contains(codeStart)){

				codePart= StringUtils.substringsBetween(temp, codeStart, codeEnd);
				System.out.println("lenght: "+codePart.length);
				for(int i1=0;i1<codePart.length;i1++ ){
					codePart[i1] = codePart[i1].replaceAll(delimSpacNew, newLine).trim();
					codePart[i1] = codePart[i1].replaceAll(delimiterNewline, newLine).trim();
					if(flag)
						codePart[i1] = "Answer"+count+newLine+newLine+codePart[i1];
					codeList.add(codePart[i1].trim());
				}
				System.out.println("codePart size: \n"+codeList.get(0));
			}else{
				if(flag)
					codeList.add("Answer"+count+newLine+"No Code Avaliable");
				else
					codeList.add("Question: \n"+"No Code Avaliable");
			}
		}
		return codeList;
	}

	public  String execute() throws Exception {
		System.out.println("count execute: "+count);
		System.out.println("submit execute: "+submit);

		return "success";
	}
}
