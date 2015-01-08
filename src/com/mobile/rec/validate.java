package com.mobile.rec;

import java.util.ArrayList;
import java.util.List;

public class validate {
	private String names,youroption ;
	

	public String getNames() {
		System.out.println(names);
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getYouroption() {
		return youroption;
	}

	public void setYouroption(String youroption) {
		this.youroption = youroption;
	}

	public List<String> options;

	public List<String> getOptions() {
		System.out.println("get options: "+ options);
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public validate(){
		options = new ArrayList<String>();
		options.add("Questions");
		options.add("Answers");
		options.add("Badges");
		options.add("Posts");
	}

	public String execute() throws Exception {
		return "success";
	}



}
