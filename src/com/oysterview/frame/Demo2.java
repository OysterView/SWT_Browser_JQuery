package com.oysterview.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.alibaba.fastjson.JSON;
import com.oysterview.util.FileUtils;

public class Demo2 {
	public static void main(String[] args) {

		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		final Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}

		String fileName = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo2\\demo2.html";
		String html1 = FileUtils.readFileToString(new File(fileName));
		browser.setText(html1);
		new BrowserFunction(browser, "getDataFromJava") {

			@Override
			public Object function(Object[] arguments) {
//				System.out.println("get: " + ((String) arguments[0]));
				List<TableData> list = new ArrayList<>();
				Random random = new Random();
				for(int i=0;i<1000;i++){
					int amount = random.nextInt(100);
					int price = random.nextInt(5);
					TableData tableData = new TableData();
					tableData.inv = i+"";
					tableData.date = new Date().toString();
					tableData.name = "name"+i;
					tableData.amount = amount;
					tableData.price = price;
					tableData.cost = price*amount;
					tableData.note = "note"+i;
					list.add(tableData);
				}
				String jsonString = JSON.toJSONString(list);
				System.out.println(jsonString);
				return jsonString;
			}

		};

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	private static class TableData{
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
