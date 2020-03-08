package com.oysterview.frame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;


public class JsEntity{
	public String toTest(String str){
		System.out.println(str);
		return "ok,我知道了!";
	}
	
	public String getDataFromJava(String str){
		System.out.println(str);
		List<TableData> list = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 100000; i++) {
			int amount = random.nextInt(100);
			int price = random.nextInt(5);
			TableData tableData = new TableData();
			tableData.inv = i + "";
			tableData.date = new Date().toString();
			tableData.name = "name" + i;
			tableData.amount = amount;
			tableData.price = price;
			tableData.cost = price * amount;
			tableData.note = "note" + i;
			list.add(tableData);
		}
		String jsonString = JSON.toJSONString(list);
		// System.out.println(jsonString);
		return jsonString;
	}
	
	public void reload(){
		Demo4.reload();
	}
	
	public static int index = 0;
	public String getOneRow(){
		Random random = new Random();
			int amount = random.nextInt(100);
			int price = random.nextInt(5);
			TableData tableData = new TableData();
			tableData.inv = index + "";
			tableData.date = new Date().toString();
			tableData.name = "name" + index;
			tableData.amount = amount;
			tableData.price = price;
			tableData.cost = price * amount;
			tableData.note = "note" + index;
		String jsonString = JSON.toJSONString(tableData);
		// System.out.println(jsonString);
		return jsonString;
	}
	
	
	public String log(String log){
		System.out.println(log);
		return"";
	}
	
	private static class TableData {
		String inv;
		String date;
		String name;
		int amount;
		int price;
		int cost;
		String note;

		public String getInv() {
			return inv;
		}

		public void setInv(String inv) {
			this.inv = inv;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}
	}

	
}
