package com.oysterview.frame;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.oysterview.util.FileUtils;

public class Demo1 {
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

		String fileName = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo1.html";
		String html1 = FileUtils.readFileToString(new File(fileName));
		browser.setText(html1);
		new BrowserFunction(browser, "get") {

			@Override
			public Object function(Object[] arguments) {
				System.out.println("get: " + ((String) arguments[0]));
				return new String[] { "666", "777", "888" };
			}

		};

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
