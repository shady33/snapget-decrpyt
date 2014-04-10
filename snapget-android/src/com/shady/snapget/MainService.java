package com.shady.snapget;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import android.annotation.SuppressLint;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainService extends Service {
	public static final String UPDATEMOOD = "UpdateMood";

	public MainService() {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStart(intent, startId);
		updateClick(intent);
		stopSelf(startId);
		return START_STICKY;
	}

	@SuppressLint("SimpleDateFormat")
	private void updateClick(Intent intent) {
		if (intent != null) {
			String requestedAction = intent.getAction();
			if (requestedAction != null && requestedAction.equals(UPDATEMOOD)) {
				int widgetId = intent.getIntExtra(
						AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

				File fileList = new File(Environment
						.getExternalStorageDirectory().getPath()
						+ "/Snapget/cache/");
				fileList.mkdirs();
				String comando = "cp -r /data/data/com.snapchat.android/cache/received_image_snaps/* /sdcard/Snapget/cache/";
				try {
					Process suProcess = Runtime.getRuntime().exec("su");
					DataOutputStream os = new DataOutputStream(
							suProcess.getOutputStream());
					os.writeBytes(comando + "\n");
					os.flush();
					os.writeBytes("exit\n");
					os.flush();
					suProcess.waitFor();
				} catch (Exception ex) {
				}
				if (fileList != null) {
					File[] filenames = fileList.listFiles();
					for (File file : filenames) {
						byte[] data = null;
						try {
							FileInputStream fis = new FileInputStream(file);
							data = new byte[(int) file.length()];
							fis.read(data);
							fis.close();
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						byte[] buffer = a(data);
						try {
							FileOutputStream f = new FileOutputStream(file);
							f.write(buffer);
							f.close();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyyMMdd_HHmmss");
							String currentDateandTime = sdf.format(new Date(0));
							File to = new File(Environment
									.getExternalStorageDirectory().getPath()
									+ "/Snapget/", currentDateandTime + ".jpg");
							do {
								Random rand = new Random();
								int n = rand.nextInt(50) + 1;
								to = new File(Environment
										.getExternalStorageDirectory()
										.getPath()
										+ "/Snapget/", currentDateandTime + n
										+ ".jpg");
							} while (to.exists());
							file.renameTo(to);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
				AppWidgetManager appWidgetMan = AppWidgetManager
						.getInstance(this);
				RemoteViews views = new RemoteViews(this.getPackageName(),
						R.layout.widgetlayout);
				appWidgetMan.updateAppWidget(widgetId, views);
				Toast.makeText(getApplicationContext(), "copied to sdcard ! Check gallery !",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public byte[] a(byte abyte0[]) {
		String b = "M02cnQ51Ji97vwT4";
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(2, new SecretKeySpec(b.getBytes(), "AES"));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte abyte2[] = null;
		try {
			abyte2 = cipher.doFinal(abyte0);
		} catch (OutOfMemoryError outofmemoryerror) {
			byte abyte1[] = null;
			try {
				System.gc();
				abyte1 = cipher.doFinal(abyte0);
			} catch (Throwable throwable) {

			}
			return abyte1;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abyte2;
	}
}
