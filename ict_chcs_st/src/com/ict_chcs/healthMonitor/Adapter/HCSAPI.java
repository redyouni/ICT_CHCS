package com.ict_chcs.healthMonitor.Adapter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.healthMonitor.ExStatusActivity;
import com.ict_chcs.healthMonitor.SignUpActivity;

import android.content.Intent;
import android.os.AsyncTask;

public class HCSAPI {

	private static String TAG = "HCSAPI";// this.getClass().getSimpleName();

	public static final boolean ENABLE_DEBUG = true;

	public static String SERVER_URL = "http://192.168.10.168/";

	public static String GET_CONNECTOIN = "ict_chcs_get_connection.php";
	
	public static String GET_EX_USER = "ict_chcs_get_ex_user.php";
	public static String SET_EX_USER = "ict_chcs_set_ex_user.php";
	public static String DEL_EX_USER = "ict_chcs_del_ex_user.php";

	public static String GET_EX_RESULT = "ict_chcs_get_ex_result.php";
	public static String SET_EX_RESULT = "ict_chcs_set_ex_result.php";
	public static String DEL_EX_RESULT = "ict_chcs_del_ex_result.php";

	public static String GET_EX_GOAL_SETTING = "ict_chcs_get_ex_goal_setting.php";
	public static String SET_EX_GOAL_SETTING = "ict_chcs_set_ex_goal_setting.php";
	public static String DEL_EX_GOAL_SETTING = "ict_chcs_del_ex_goal_setting.php";

	public static String GET_EX_EQUIPMENT = "ict_chcs_get_ex_equipment.php";
	public static String SET_EX_EQUIPMENT = "ict_chcs_set_ex_equipment.php";
	public static String DEL_EX_EQUIPMENT = "ict_chcs_del_ex_equipment.php";
	public static String REQUEST = null;
	
/*
	public static Boolean GetServerConnection(StringBuilder retJson);
	public static Boolean GetExUser(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean SetExUser(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean DelExUser(String id, Boolean all, StringBuilder retJson);

	public static Boolean GetExResult(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean SetExResult(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean DelExResult(String id, Boolean all, StringBuilder retJson);

	public static Boolean GetExGoalSetting(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean SetExGoalSetting(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean DelExGoalSetting(String id, Boolean all, StringBuilder retJson);
	
	public static Boolean GetExEquipment(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean SetExEquipment(ArrayList<String> ArrayList, StringBuilder retJson);
	public static Boolean DelExEquipment(String name, Boolean all, StringBuilder retJson);	
*/	
	
	public static Boolean GetServerConnection(StringBuilder retJson) {

		String result = null;
		REQUEST = SERVER_URL + GET_CONNECTOIN;

		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retJson.append("server is not opened.");
		}

		return false;
	}
	
	public static Boolean GetExUser(ArrayList<String> ArrayList, StringBuilder retJson) {

		String result = null;
		REQUEST = SERVER_URL + GET_EX_USER + "?" + "id=" + ArrayList.get(0) + "&" + "password=" + ArrayList.get(1);

		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static Boolean SetExUser(ArrayList<String> ArrayList, StringBuilder retJson) {

		if (ArrayList.size() == 7) {
			REQUEST = SERVER_URL + SET_EX_USER + "?" + "id=" + ArrayList.get(0) + "&" + "password=" + ArrayList.get(1)
					+ "&name=" + ArrayList.get(2) + "&age=" + ArrayList.get(3) + "&gender=" + ArrayList.get(4)
					+ "&weight=" + ArrayList.get(5) + "&rfid=" + ArrayList.get(6);

			String result = null;
			try {
				result = new Query().execute(REQUEST).get();
				if(retJson != null) retJson.append(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				JSONObject root = new JSONObject(result);
				String num_results = root.getString("status");

				if (num_results.equalsIgnoreCase("OK")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
	
	public static Boolean GetExUserWRfid(ArrayList<String> ArrayList, StringBuilder retJson) {

		String result = null;
		REQUEST = SERVER_URL + GET_EX_USER + "?" + "rfid=" + ArrayList.get(0);

		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static Boolean DelExUser(String id, Boolean all, StringBuilder retJson) {

		if(all) {
			REQUEST = SERVER_URL + DEL_EX_USER + "?" + "all=1";
		}
		else {
			REQUEST = SERVER_URL + DEL_EX_USER + "?" + "id=" + id;
		}

		String result = null;
		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static Boolean GetExResult(ArrayList<String> ArrayList, StringBuilder retJson) {

		if (ArrayList.size() == 3) {
			REQUEST = SERVER_URL + GET_EX_RESULT + "?" + "id=" + ArrayList.get(0) + "&ex_variety=" + ArrayList.get(1)	 + "&st_time=" + ArrayList.get(2);

			String result = null;
			try {
				result = new Query().execute(REQUEST).get();
				if(retJson != null) retJson.append(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				JSONObject root = new JSONObject(result);
				String num_results = root.getString("status");

				if (num_results.equalsIgnoreCase("OK")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public static Boolean SetExResult(ArrayList<String> ArrayList, StringBuilder retJson) {


		if (ArrayList.size() == 14) {
			REQUEST = SERVER_URL + SET_EX_RESULT + "?" + "id=" + ArrayList.get(0) + "&ex_variety=" + ArrayList.get(1)
					+ "&st_time=" + ArrayList.get(2) + "&en_time=" + ArrayList.get(3) + "&ex_time="+ ArrayList.get(4) 
					+ "&ex_heartplus=" + ArrayList.get(5) + "&ex_calories=" + ArrayList.get(6) 	+ "&ex_distance=" + ArrayList.get(7)
					+ "&ex_speed=" + ArrayList.get(8) + "&ex_incline=" + ArrayList.get(9) + "&ex_level=" + ArrayList.get(10)
					+ "&ex_rpm=" + ArrayList.get(11)+ "&ex_watts=" + ArrayList.get(12)+ "&ex_mets=" + ArrayList.get(13);

			String result = null;
			try {
				result = new Query().execute(REQUEST).get();
				if(retJson != null) retJson.append(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				JSONObject root = new JSONObject(result);
				String num_results = root.getString("status");

				if (num_results.equalsIgnoreCase("OK")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public static Boolean DelExResult(String id, Boolean all, StringBuilder retJson) {

		if(all) {
			REQUEST = SERVER_URL + DEL_EX_RESULT + "?" + "all=1";
		}
		else {
			REQUEST = SERVER_URL + DEL_EX_RESULT + "?" + "id=" + id;
		}

		String result = null;
		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static Boolean GetExGoalSetting(ArrayList<String> ArrayList, StringBuilder retJson) {

		if (ArrayList.size() == 2) {
			REQUEST = SERVER_URL + GET_EX_GOAL_SETTING + "?" + "id=" + ArrayList.get(0) + "&" + "ex_variety=" + ArrayList.get(1);

			String result = null;
			try {
				result = new Query().execute(REQUEST).get();
				if(retJson != null) retJson.append(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				JSONObject root = new JSONObject(result);
				String num_results = root.getString("status");

				if (num_results.equalsIgnoreCase("OK")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public static Boolean SetExGoalSetting(ArrayList<String> ArrayList, StringBuilder retJson) {

		if (ArrayList.size() == 6) {
			REQUEST = SERVER_URL + SET_EX_GOAL_SETTING + "?" + "id=" + ArrayList.get(0) + "&" + "ex_variety=" + ArrayList.get(1)
					+ "&ex_heartplus=" + ArrayList.get(2) + "&ex_calories=" + ArrayList.get(3) + "&ex_distance=" + ArrayList.get(4)
					+ "&ex_speed=" + ArrayList.get(5);

			String result = null;
			try {
				result = new Query().execute(REQUEST).get();
				if(retJson != null) retJson.append(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				JSONObject root = new JSONObject(result);
				String num_results = root.getString("status");

				if (num_results.equalsIgnoreCase("OK")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public static Boolean DelExGoalSetting(String id, Boolean all, StringBuilder retJson) {

		if(all) {
			REQUEST = SERVER_URL + DEL_EX_GOAL_SETTING + "?" + "all=1";
		}
		else {
			REQUEST = SERVER_URL + DEL_EX_GOAL_SETTING + "?" + "id=" + id;
		}

		String result = null;
		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public static Boolean GetExEquipment(ArrayList<String> ArrayList, StringBuilder retJson) {

		String result = null;
		REQUEST = SERVER_URL + GET_EX_EQUIPMENT + "?" + "name=" + ArrayList.get(0);

		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static Boolean SetExEquipment(ArrayList<String> ArrayList, StringBuilder retJson) {

		if (ArrayList.size() == 2) {
			REQUEST = SERVER_URL + SET_EX_EQUIPMENT + "?" + "name=" + ArrayList.get(0) + "&" + "in_date=" + ArrayList.get(1);

			String result = null;
			try {
				result = new Query().execute(REQUEST).get();
				if(retJson != null) retJson.append(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				JSONObject root = new JSONObject(result);
				String num_results = root.getString("status");

				if (num_results.equalsIgnoreCase("OK")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public static Boolean DelExEquipment(String name, Boolean all, StringBuilder retJson) {

		if(all) {
			REQUEST = SERVER_URL + DEL_EX_EQUIPMENT + "?" + "all=1";
		}
		else {
			REQUEST = SERVER_URL + DEL_EX_EQUIPMENT + "?" + "name=" + name;
		}

		String result = null;
		try {
			result = new Query().execute(REQUEST).get();
			if(retJson != null) retJson.append(result);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			JSONObject root = new JSONObject(result);
			String num_results = root.getString("status");

			if (num_results.equalsIgnoreCase("OK")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}


	static class Query extends AsyncTask<String, Integer, String> {

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
			 * try { JSONObject root = new JSONObject(str); JSONArray ja =
			 * root.getJSONArray("results");
			 * 
			 * for (int i = 0; i < ja.length(); i++) { JSONObject jo =
			 * ja.getJSONObject(i); id = jo.getString("id"); // listitem.add(new
			 * ListItem(id)); } } catch (JSONException e) { e.printStackTrace();
			 * } // txtView.setText("id : "+listitem.get(0).getData(0));
			 * 
			 */
		}

	}
	/*
	 * public static class Insert extends AsyncTask<String, Integer, String> {
	 * 
	 * @Override protected String doInBackground(String... urls) { StringBuilder
	 * resultText = new StringBuilder(); try { URL url = new URL(urls[0]);
	 * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 * 
	 * if (conn != null) { conn.setConnectTimeout(10000);
	 * conn.setUseCaches(false);
	 * 
	 * if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { BufferedReader
	 * br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
	 * "UTF-8")); for (;;) { String line = br.readLine(); if (line == null)
	 * break; resultText.append(line); } br.close(); } else { InputStream is =
	 * conn.getErrorStream(); ByteArrayOutputStream baos = new
	 * ByteArrayOutputStream(); byte[] byteBuffer = new byte[1024]; byte[]
	 * byteData = null; int nLength = 0; while ((nLength = is.read(byteBuffer,
	 * 0, byteBuffer.length)) != -1) { baos.write(byteBuffer, 0, nLength); }
	 * byteData = baos.toByteArray(); String response = new String(byteData); //
	 * Log.d(TAG, "response = " + response); } conn.disconnect(); } } catch
	 * (Exception ex) { ex.printStackTrace(); } return resultText.toString();
	 * 
	 * }
	 * 
	 * protected void onPostExecute(String str) {
	 * 
	 * } }
	 */
}