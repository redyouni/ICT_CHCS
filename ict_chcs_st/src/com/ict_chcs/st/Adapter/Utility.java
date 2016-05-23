package com.ict_chcs.st.Adapter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Utility {
	final static String TAG = "Utility";

	/* Utils .... */

	private static AlertDialog mMsgeDialog = null;

	/* Debug mode */
	public static int getBuildMode(Context context) {
		return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
	}

	public static void screenshot(Context context, View view) throws Exception {

		view.setDrawingCacheEnabled(true);
		Bitmap screenshot = view.getDrawingCache();

		String filename = "screenshot_" + System.currentTimeMillis() + ".png";

		try {

			File f = new File("/mnt/sdcard", filename);

			f.createNewFile();
			OutputStream outStream = new FileOutputStream(f);
			screenshot.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.close();

			Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show();

		} catch (IOException e) {
			e.printStackTrace();
		}

		view.setDrawingCacheEnabled(false);
	}

	public static String getFileExternalStorage(String fileName) {

		String findFile = null;
		String sVersion = Build.VERSION.RELEASE;
		String[] temp = sVersion.split("[.]");
		Float version = Float.valueOf(temp[0] + "." + temp[1]);

		if (version >= 4.2f) {
			findFile = "/storage/external_storage/sdcard1/";
		} else {
			findFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
		}

		File files = new File(findFile);

		try {
			if (files.listFiles(new fileFilter(fileName)).length > 0) {
				for (File file : files.listFiles(new fileFilter(fileName))) {

					if (file.getName() != null) {
						findFile += file.getName();
						return findFile;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Debug.d(TAG, "Exception : " + e.getMessage());
		}

		return null;
	}

	private static class fileFilter implements FilenameFilter {

		String findFile = null;

		fileFilter(String fileName) {
			findFile = fileName;
		}

		@Override
		public boolean accept(File dir, String filename) {
			// TODO Auto-generated method stub

			Debug.d(TAG, "filename = " + filename + ", findFile = " + findFile);

			if (filename.equals(findFile)) {
				return true;
			}
			return false;
		}

	}

	public static void startActivity(Context context, Class<?> cls) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClass(context.getApplicationContext(), cls);
		context.startActivity(intent);
		// ((Activity) context).overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		// ((Activity) context).finish();
	}

	// SET VERSION
	/*
	 * public static void setVersion(Context context) { try {
	 * Log.d("Application", "versionName : " +
	 * context.getPackageManager().getPackageInfo(context.getPackageName(),
	 * 0).versionName); Log.d("Application", "getEXTRA_VERSION() : " +
	 * context.getSharedPrefs_VERSION());
	 * 
	 * if(getSharedPrefs_VERSION().equalsIgnoreCase(context.getPackageManager().
	 * getPackageInfo(context.getPackageName(), 0).versionName) == false) {
	 * setSharedPrefs_VERSION(context.getPackageManager().getPackageInfo(context
	 * .getPackageName(), 0).versionName); } } catch (NameNotFoundException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	public static ArrayList<String> CheckUsbExist() {
		String storage = "";
		storage = "/storage/external_storage";

		File[] files = new File(storage).listFiles();
		ArrayList<String> list = new ArrayList<String>();

		if (files != null) {
			for (File file : files) {
				if (file.getPath().startsWith(
						storage + "/sd") /*
											 * && !file.getPath().startsWith(
											 * storage+"/sdcard")
											 */) { // usb & sdcard
					if (file.canRead()) {
						if (list.indexOf(file.getAbsolutePath()) == -1) {
							list.add(file.getAbsolutePath() + "/");
							Log.e(TAG, file.getAbsolutePath());
						}
					}
				}
			}

			if (list.size() != 0)
				return list;
			else
				return null;
		}
		return null;
	}

	public static boolean isOnline(Context context) { // network 연결 상태 확인
		try {
			WifiManager mWifiManager;
			ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			Debug.i(TAG, "isOnline !! ");

			State wifi = conMan.getNetworkInfo(1).getState(); // wifi
			if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
				Debug.i(TAG, "wifi isOnline !! ");
				return true;
			}

			State mobile = conMan.getNetworkInfo(0).getState(); // mobile
																// ConnectivityManager.TYPE_MOBILE
			if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
				Debug.i(TAG, "mobile isOnline !! ");
				return true;
			}

		} catch (NullPointerException e) {
			return false;
		}

		return false;
	}

	/**
	 * Convert byte array to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder sbuf = new StringBuilder();
		for (int idx = 0; idx < bytes.length; idx++) {
			int intVal = bytes[idx] & 0xff;
			if (intVal < 0x10)
				sbuf.append("0");
			sbuf.append(Integer.toHexString(intVal).toUpperCase());
		}
		return sbuf.toString();
	}

	/**
	 * Get utf8 byte array.
	 * 
	 * @param str
	 * @return array of NULL if error was found
	 */
	public static byte[] getUTF8Bytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Load UTF8withBOM or any ansi text file.
	 * 
	 * @param filename
	 * @return
	 * @throws java.io.IOException
	 */
	public static String loadFileAsString(String filename) throws java.io.IOException {
		final int BUFLEN = 1024;
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
			byte[] bytes = new byte[BUFLEN];
			boolean isUTF8 = false;
			int read, count = 0;
			while ((read = is.read(bytes)) != -1) {
				if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
					isUTF8 = true;
					baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
				} else {
					baos.write(bytes, 0, read);
				}
				count += read;
			}
			return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
		} finally {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * Returns MAC address of the given interface name.
	 * 
	 * @param interfaceName
	 *            eth0, wlan0 or NULL=use first interface
	 * @return mac address or empty string
	 */
	@SuppressLint("NewApi")
	public static String getMACAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName))
						continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac == null)
					return "";
				StringBuilder buf = new StringBuilder();
				for (int idx = 0; idx < mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length() > 0)
					buf.deleteCharAt(buf.length() - 1);
				return buf.toString();
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}

	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param interfacename
	 *            eth0, ipv4 true=return ipv4, false=return ipv6
	 * @return address or empty string
	 */
	public static String getIPAddress(String interfaceName, boolean useIPv4) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName))
						continue;
				}
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 port
																// suffix
								return delim < 0 ? sAddr : sAddr.substring(0, delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}

	public static String getIPAddress(boolean useIPv4) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 port
																// suffix
								return delim < 0 ? sAddr : sAddr.substring(0, delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}

	public static String getNowDateTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	public static void msgbox(final Context context, String msg) {
		final String output = msg;
		Handler handler = new Handler();

		handler.post(new Runnable() {
			public void run() {
				Log.d(TAG, output);
				Toast.makeText(context.getApplicationContext(), output, Toast.LENGTH_LONG).show();
			}
		});
	}

	public static String getWifiInfo(Context context) {
		WifiManager wifimanager;

		if (isAvailableNetwork(context)) {
			wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifimanager.getConnectionInfo();
			String ssid = info.getSSID();
			int speed = info.getLinkSpeed();
			DhcpInfo dhcpInfo = wifimanager.getDhcpInfo();
			int ip = dhcpInfo.ipAddress;

			return ("SSID : " + ssid + ",  LinkSpeed : " + speed + ",  IP : " + String.format("%d.%d.%d.%d", (ip & 0xff),
					(ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff)));
		}
		return "WiFi disconnected !";
	}

	public static boolean isAvailableNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = manager.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
