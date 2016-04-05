package com.ict_chcs.healthMonitor.Adapter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Php {

	public static class Down extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				URL url = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						for (;;) {
							String line = br.readLine();
							if (line == null)
								break;
							jsonHtml.append(line + "\n");
						}
						br.close();
					}
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return jsonHtml.toString();
		}

		protected void onPostExecute(String str) {
/*
			try {
				JSONObject root = new JSONObject(str);
				JSONArray ja = root.getJSONArray("results");

				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					id = jo.getString("id");
					// listitem.add(new ListItem(id));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// txtView.setText("id : "+listitem.get(0).getData(0));
			 
			 */
		}
		

	}

	public static class Insert extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder resultText = new StringBuilder();
			try {
				URL url = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);

					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						for (;;) {
							String line = br.readLine();
							if (line == null)
								break;
							resultText.append(line);
						}
						br.close();
					} else {
						InputStream is = conn.getErrorStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						byte[] byteBuffer = new byte[1024];
						byte[] byteData = null;
						int nLength = 0;
						while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
							baos.write(byteBuffer, 0, nLength);
						}
						byteData = baos.toByteArray();
						String response = new String(byteData);
						//Log.d(TAG, "response = " + response);
					}
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return resultText.toString();

		}

		protected void onPostExecute(String str) {
			/*
			if (str.equals("1")) {
				Toast.makeText(getApplicationContext(), "DB Insert Complete.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "DB Insert Failed.", Toast.LENGTH_LONG).show();
			}
			*/
		}
	}
}