package com.oysterview.frame;

import java.awt.Frame;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.oysterview.util.FileUtils;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.NetError;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.internal.ipc.message.ReloadIgnoringCacheMessage;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class Demo4 {
	private static Browser browser;
	
	static {
		try {
			Class claz = null;
			// 6.5.1版本破解 兼容xp
			// claz = Class.forName("com.teamdev.jxbrowser.chromium.aq");
			// 6.21版本破解 默认使用最新的6.21版本
			claz = Class.forName("com.teamdev.jxbrowser.chromium.ba");

			Field e = claz.getDeclaredField("e");
			Field f = claz.getDeclaredField("f");

			e.setAccessible(true);
			f.setAccessible(true);
			Field modifersField = Field.class.getDeclaredField("modifiers");
			modifersField.setAccessible(true);
			modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
			modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			e.set(null, new BigInteger("1"));
			f.set(null, new BigInteger("1"));
			modifersField.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("执行jxbrowser破解程序时出现异常");
		}
	}

	public static void main(String[] args) {

		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		Frame frame = SWT_AWT.new_Frame(composite);

		browser = new Browser();
		BrowserView view = new BrowserView(browser);

		frame.add(view);

		String fileName = "html\\demo3\\demo3.html";
		String url = "file:///" + new File(fileName).getAbsolutePath();
		System.out.println(new File(fileName).getAbsolutePath());
		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onStartLoadingFrame(StartLoadingEvent event) {
				if (event.isMainFrame()) {
					System.out.println("Main frame has started loading.");
				}
			}

			@Override
			public void onProvisionalLoadingFrame(ProvisionalLoadingEvent event) {
				if (event.isMainFrame()) {
					System.out.println("Provisional load was committed for a frame.");
				}
			}

			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				if (event.isMainFrame()) {
					System.out.println("Main frame has finished loading.");
				}
				JSValue window = browser.executeJavaScriptAndReturnValue("window");

				JsEntity jsEntity = new JsEntity();
				window.asObject().setProperty("jsEntity", jsEntity);

			}

			@Override
			public void onFailLoadingFrame(FailLoadingEvent event) {
				NetError errorCode = event.getErrorCode();
				if (event.isMainFrame()) {
					System.out.println("Main frame has failed loading: " + errorCode);
				}
			}

			@Override
			public void onDocumentLoadedInFrame(FrameLoadEvent event) {
				System.out.println("Frame document is loaded.");
			}

			@Override
			public void onDocumentLoadedInMainFrame(LoadEvent event) {
				System.out.println("Main frame document is loaded.");
			}
		});

		browser.loadURL(url);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		browser.dispose();
		display.dispose();
	}

	
	public static void reload(){
		browser.reload();
	}

}
