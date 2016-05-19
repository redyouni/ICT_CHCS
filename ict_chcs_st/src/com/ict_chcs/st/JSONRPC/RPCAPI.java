package com.ict_chcs.st.JSONRPC;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.healthMonitor.R;
import com.ict_chcs.st.Application;
import com.ict_chcs.st.Adapter.Debug;
import com.ict_chcs.st.Adapter.Utility;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class RPCAPI {
	private static String TAG = "CJAPI";// this.getClass().getSimpleName();

	public static final boolean ENABLE_DEBUG = true;

	// public static final String TAG = "JSONRCPClient";
	public static String API_URL = "http://192.168.0.168/2.rpc";
	public static final int HTTP_CONNECTION_TIMEOUT = 5000;
	public static final int HTTP_SOCKET_TIMEOUT = 5000;

	public static final String Machine_Treadmill = "TYT0001";
	public static final String Machine_Bike = "TYB0002";
	public static final String Machine_Senior = "TYW0003";

	public static final String Code_Treadmill = "1200";
	public static final String Code_Bike = "1700";

	public static final String Code_Chest_Press = "1023";
	public static final String Code_Soulder_Press = "1012";
	public static final String Code_Leg_Extension = "1004";

	// public static String mCard_UID = "1234567A";
	public static String mCard_UID = null;
	public static String Member_Auth_Key = "";

	// ----------------------------------------------
	public static final int MSG_GetServerTime = 1;
	public static final int MSG_GetAppSetting = 2;
	public static final int MSG_AuthMemberByCard = 3;
	public static final int MSG_GetMemberInfo = 4;

	public static final int MSG_SetMachineStatus = 5;

	public static final int MSG_GetCardioExGuide = 6;
	public static final int MSG_SetCDExResult = 7;

	public static final int MSG_GetWTExGuide = 8;
	public static final int MSG_SetWTExResult = 9;

	private SendMassgeHandler mHandler = null;
	private MonitorThread mThread = null;

	public static String mSerial = "";
	public static String mExtCode = "";
	public static String mMachine = "";
	public static String mMac = "";
	public static String mIP = "";

	private static boolean mWaiting = false;
	private static Context mContext = null;

	public RPCAPI(Context context) {

		mContext = context;
		//mSerial = Utility.getDroidSerial();
		mMachine = Machine_Senior + mSerial;
		mExtCode = Code_Treadmill;
		mWaiting = false;

		/*
		 * mHandler = new SendMassgeHandler(); mThread = new MonitorThread();
		 * mThread.start();
		 */

		String eth0 = Utility.getMACAddress("eth0").replace(":", "");
		String eth0Ip = Utility.getIPAddress("eth0", true);

		String wlan0 = Utility.getMACAddress("wlan0").replace(":", "");
		String wlan0Ip = Utility.getIPAddress("wlan0", true);

		if (!eth0Ip.equalsIgnoreCase("")) {
			mMac = eth0;
			mIP = eth0Ip;
		} else if (!wlan0Ip.equalsIgnoreCase("")) {
			mMac = wlan0;
			mIP = wlan0Ip;
		}

		if (!((Application) mContext).getSharedPrefs_STRING(Application.ICT_CHCS_EX_SERVER)
				.equalsIgnoreCase("")) {
			API_URL = ((Application) mContext).getSharedPrefs_STRING(Application.ICT_CHCS_EX_SERVER);
		}

	}

	public static String GetCardUID() {
		return mCard_UID;
	}

	public static void SetCardUID(String cardUID) {
		mCard_UID = cardUID;
	}

	public static void Waiting(boolean status) {
		mWaiting = status;
	}

	public void Release() {

		mHandler = null;
		if (mThread != null) {
			mThread.stopThread();
			mThread = null;
		}
	}

	public void sendMessage(int msgId) {
		if (mHandler != null) {
			Message msg = mHandler.obtainMessage();
			msg.what = msgId;
			mHandler.sendMessage(msg);
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// ------------------------- Starting Application
	// ---------------------------------------------------//
	// ------------------------------------------------------------------------------------------------------------//

	public static class GetServerTime {
		private String TAG = this.getClass().getSimpleName();

		public String currServerTime = null;
		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public GetServerTime() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("machineSN", mMachine);
				jsonObj.put("isInit", "1");
				JSONObject result = client.callJSONObject("GetServerTime", jsonObj);

				if (result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);

					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result.has("currServerTime")) {
					// success
					successMsg = result.getString("currServerTime");
					Debug.e(TAG, successMsg);

					currServerTime = result.getString("currServerTime");
					setNowDateTime(currServerTime);
					success = true;

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	public static class GetAppSetting {
		private String TAG = this.getClass().getSimpleName();

		public int resultTime = 0;
		public int satusInterval = 0;
		public int restTime = 0;
		public int autoFinishTime = 0;
		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public GetAppSetting() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("machineSN", mMachine);
				JSONObject result = client.callJSONObject("GetAppSetting", jsonObj);

				if (result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result.has("resultTime") && result.has("statusInterval") && result.has("restTime")
						&& result.has("autoFinishTime")) {
					// success
					successMsg = "resultTime : " + result.getString("resultTime") + "\n statusInterval : "
							+ result.getString("statusInterval") + "\n restTime : " + result.getString("restTime")
							+ "\n autoFinishTime : " + result.getString("autoFinishTime");
					Debug.e(TAG, successMsg);

					resultTime = result.getInt("resultTime");
					satusInterval = result.getInt("statusInterval");
					restTime = result.getInt("restTime");
					autoFinishTime = result.getInt("autoFinishTime");

					success = true;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public String toResult() {

			if (success) {

				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// ------------------------- User login
	// ---------------------------------------------------//
	// ------------------------------------------------------------------------------------------------------------//

	public static class AuthMemberByCard {
		private String TAG = this.getClass().getSimpleName();

		public String memAuthKey = "";
		public String memName = "";
		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public AuthMemberByCard() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();

				String eth0 = Utility.getMACAddress("eth0").replace(":", "");
				String eth0Ip = Utility.getIPAddress("eth0", true);

				String wlan0 = Utility.getMACAddress("wlan0").replace(":", "");
				String wlan0Ip = Utility.getIPAddress("wlan0", true);

				if (!eth0Ip.equalsIgnoreCase("")) {
					mMac = eth0;
					mIP = eth0Ip;
				} else if (!wlan0Ip.equalsIgnoreCase("")) {
					mMac = wlan0;
					mIP = wlan0Ip;
				}

				jsonObj.put("UID", mCard_UID);
				jsonObj.put("machineSN", mMachine);
				jsonObj.put("macAddr", mMac);
				jsonObj.put("ipAddr", mIP);

				JSONObject result = client.callJSONObject("AuthMemberByCard", jsonObj);

				if (result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result.has("memAuthKey") && result.has("memName")) {
					// success
					successMsg = "memAuthKey : " + result.getString("memAuthKey") + "\n memName : "
							+ result.getString("memName");
					Debug.e(TAG, successMsg);

					memAuthKey = result.getString("memAuthKey");
					memName = result.getString("memName");

					Member_Auth_Key = memAuthKey;
					success = true;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	public static class GetMemberInfo {
		private String TAG = this.getClass().getSimpleName();

		public String memName = "";
		public String memGender = "";
		public String memWeight = "";
		public int memAge = 0;
		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public GetMemberInfo() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("memAuthKey", Member_Auth_Key);

				JSONObject result = client.callJSONObject("GetMemberInfo", jsonObj);

				if (result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result.has("memName") && result.has("memGender") && result.has("memWeight")
						&& result.has("memAge")) {
					// success
					successMsg = "memName : " + result.getString("memName") + "\n memGender : "
							+ result.getString("memGender") + "memWeight : " + result.getString("memWeight")
							+ "\n memAge : " + result.getString("memAge");
					Debug.e(TAG, successMsg);

					memName = result.getString("memName");
					memGender = result.getString("memGender");
					memWeight = result.getString("memWeight");
					memAge = result.getInt("memAge");
					success = true;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// ------------------------- Waiting
	// ---------------------------------------------------//
	// ------------------------------------------------------------------------------------------------------------//

	public static class SetMachineStatus {
		private String TAG = this.getClass().getSimpleName();

		public Boolean isRestart = false;
		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public SetMachineStatus() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("machineSN", mMachine);
				jsonObj.put("ipAddr", mIP);
				jsonObj.put("status", "1");
				jsonObj.put("exKey", "0");

				JSONObject result = client.callJSONObject("SetMachineStatus", jsonObj);

				if (result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result.has("isRestart")) {
					// success
					successMsg = "isRestart : " + result.getString("isRestart");
					Debug.e(TAG, successMsg);
					isRestart = result.getBoolean("isRestart");
					success = true;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// ------------------------- Aerobic Exercise
	// ---------------------------------------------------//
	// ------------------------------------------------------------------------------------------------------------//

	public static class GetCardioExGuide {
		private String TAG = this.getClass().getSimpleName();

		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;
		public String userName = "";
		public int age = 0;
		public String gender = "";
		public int memWeight = 0;
		public int tgHr = 0;
		public String tgCalDay = "";
		public int tgDayTime = 0;
		public int tgV = 0;
		public String tgDistance = "";
		public String currServerTime = "";

		public GetCardioExGuide() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("UID", mCard_UID);
				jsonObj.put("exSortCode", mExtCode);
				jsonObj.put("machineSN", mMachine);
				jsonObj.put("macAddr", mMac);
				jsonObj.put("ipAddr", mIP);

				if (mCard_UID == null || mExtCode == null || mMachine == null || mMac == null || mIP == null) {
					Debug.e(TAG, "Parameter is null");
					return;
				}

				JSONObject result = client.callJSONObject("GetCardioExGuide", jsonObj);

				if (result != null && result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result != null && result.has("userName")) {
					// success
					successMsg = "userName : " + result.getString("userName") + " age : " + result.getString("age")
							+ " gender : " + result.getString("gender") + " memWeight : "
							+ result.getString("memWeight") + " tgHr : " + result.getString("tgHr") + " tgCalDay : "
							+ result.getString("tgCalDay") + " tgDayTime : " + result.getString("tgDayTime") + " tgV : "
							+ result.getString("tgV") + " tgDistance : " + result.getString("tgDistance")
							+ " currServerTime : " + result.getString("currServerTime");
					Debug.e(TAG, successMsg);

					userName = result.getString("userName");
					age = result.getInt("age");
					gender = result.getString("gender");
					memWeight = result.getInt("memWeight");
					tgHr = result.getInt("tgHr");
					tgCalDay = result.getString("tgCalDay");
					String[] arrTgCalDay = tgCalDay.split("\\.");
					tgDayTime = result.getInt("tgDayTime");
					tgV = result.getInt("tgV");
					tgDistance = result.getString("tgDistance").toString();
					String[] arrTgDistance = tgDistance.split("\\.");

					currServerTime = result.getString("currServerTime");
					setNowDateTime(currServerTime);

					/*
					Context curContext = LexcoProtocolAdapter.getLexcoContext();

					if (curContext != null) {
						if (((Activity) curContext).findViewById(R.id.txtView_cj_username) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_username))
									.setText(result.getString("userName"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_age) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_age))
									.setText(result.getString("age"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_gender) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_gender))
									.setText(result.getString("gender"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_weight) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_weight))
									.setText(result.getString("memWeight"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_tghr) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_tghr))
									.setText(result.getString("tgHr"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_tgcalday) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_tgcalday))
									.setText(result.getString("tgCalDay"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_tgdaytime) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_tgdaytime))
									.setText(result.getString("tgDayTime"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_tgv) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_tgv))
									.setText(result.getString("tgV"));
						}
						if (((Activity) curContext).findViewById(R.id.txtView_cj_tgdistance) != null) {
							((TextView) ((Activity) curContext).findViewById(R.id.txtView_cj_tgdistance))
									.setText(result.getString("tgDistance"));
						}

						Globals.mStartTime = Utility.getNowDateTime();

						Gui2LexcoGetCardioExGuide.setInitValue();
						Gui2LexcoGetCardioExGuide.setAge(age);
						if (gender.equalsIgnoreCase("M")) {
							Gui2LexcoGetCardioExGuide.setGender(0x00);
						} else if (gender.equalsIgnoreCase("F")) {
							Gui2LexcoGetCardioExGuide.setGender(0x01);
						}
						Gui2LexcoGetCardioExGuide.setMemWeight(memWeight);
						Gui2LexcoGetCardioExGuide.setTgHr(tgHr);
						if (arrTgCalDay.length == 2) {
							Gui2LexcoGetCardioExGuide.setTgCalDay(Integer.parseInt(arrTgCalDay[0]),
									Integer.parseInt(arrTgCalDay[1]));
						} else if (arrTgCalDay.length == 1) {
							Gui2LexcoGetCardioExGuide.setTgCalDay(Integer.parseInt(arrTgCalDay[0]), 0x00);
						}

						Gui2LexcoGetCardioExGuide.setTgDayTime(tgDayTime);
						Gui2LexcoGetCardioExGuide.setTgV(tgV);

						if (arrTgDistance.length == 2) {
							Gui2LexcoGetCardioExGuide.setTgCalDay(Integer.parseInt(arrTgDistance[0]),
									Integer.parseInt(arrTgDistance[1]));
						} else if (arrTgDistance.length == 1) {
							Gui2LexcoGetCardioExGuide.setTgCalDay(Integer.parseInt(arrTgDistance[0]), 0x00);
						}
						
						 if(LexcoProtocol.getLexcoProtocol() != null) {
						 LexcoProtocol.getLexcoProtocol().setSerialSender(
						 LexcoProtocol.Gui2LexcoGetCardioExGuide, null,
						 (int)0); }
						/
					}
					*/				
				}

			} catch (Exception e) {
				// TODO: handle exception
				Debug.e(TAG, "Exception");
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	public static class SetCDExResult {
		private String TAG = this.getClass().getSimpleName();

		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public SetCDExResult(String param5, String param6, String param7, String param8, String param9, String param10,
				String param11) {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("UID", mCard_UID);
				jsonObj.put("exSortCode", mExtCode);
				//jsonObj.put("exStartTime", Globals.mStartTime);
				//jsonObj.put("exEndTime", Globals.mEndTime);
				jsonObj.put("exAvgHr", param5);
				jsonObj.put("exMaxHr", param6);
				jsonObj.put("exCalConsume", param7);
				jsonObj.put("exTotalDistance", param8);
				jsonObj.put("exTime", param9);
				jsonObj.put("exAvgV", param10);
				jsonObj.put("exMaxV", param11);

				if (mCard_UID == null || mExtCode == null /*|| Globals.mStartTime == null || Globals.mEndTime == null*/
						|| param5 == null || param6 == null || param7 == null || param8 == null || param9 == null
						|| param10 == null || param11 == null) {
					Debug.e(TAG, "Parameter is null");
					return;
				}

				JSONObject result = client.callJSONObject("SetCDExResult", jsonObj);

				if (result != null && result.has("code")) {
					// error

					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else {
					// success
					successMsg = "success";
					Debug.e(TAG, successMsg);
				}

			} catch (Exception e) {
				// TODO: handle exception

				Debug.e(TAG, "exception");
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// ------------------------- weights Exercise
	// ---------------------------------------------------//
	// ------------------------------------------------------------------------------------------------------------//

	public static class GetWTExGuide {
		private String TAG = this.getClass().getSimpleName();

		public String userName = "";
		public int age = 0;
		public String gender = "";
		public String memWeight = "";
		public int setNum = 0;
		public String weight = "";
		public int repetitions = 0;
		public String currServerTime = "";
		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public GetWTExGuide() {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("UID", mCard_UID);
				jsonObj.put("exSortCode", mExtCode);
				jsonObj.put("machineSN", mMachine);
				jsonObj.put("macAddr", mMac);
				jsonObj.put("ipAddr", mIP);

				if (mCard_UID == null || mExtCode == null || mMachine == null || mMac == null || mIP == null) {
					Debug.e(TAG, "Parameter is null");
					return;
				}

				JSONObject result = client.callJSONObject("GetWTExGuide", jsonObj);

				if (result != null && result.has("code")) {
					// error
					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else if (result != null && result.has("userName")) {
					// success
					successMsg = "userName : " + result.getString("userName") + " age : " + result.getString("age")
							+ " gender : " + result.getString("gender") + " memWeight : "
							+ result.getString("memWeight") + " setNum : " + result.getString("setNum") + " weight : "
							+ result.getString("weight") + " repetitions : " + result.getString("repetitions")
							+ " currServerTime : " + result.getString("currServerTime");
					Debug.e(TAG, successMsg);

					userName = result.getString("userName");
					age = result.getInt("age");
					gender = result.getString("gender");
					memWeight = result.getString("memWeight");
					String[] arrMemWeight = memWeight.split(".");

					setNum = result.getInt("setNum");
					weight = result.getString("weight");
					String[] arrWeight = weight.split(".");

					repetitions = result.getInt("repetitions");
					currServerTime = result.getString("currServerTime");
					setNowDateTime(currServerTime);

					/*
					Gui2LexcoGetWTExGuide.setInitValue();
					Gui2LexcoGetWTExGuide.setAge(age);
					Gui2LexcoGetWTExGuide.setGender(Integer.parseInt(gender));
					if (arrMemWeight.length == 2) {
						Gui2LexcoGetWTExGuide.setMemWeight(Integer.parseInt(arrMemWeight[0]),
								Integer.parseInt(arrMemWeight[1]));
					} else {
						Gui2LexcoGetWTExGuide.setMemWeight(0x00, Integer.parseInt(arrMemWeight[0]));
					}
					Gui2LexcoGetWTExGuide.setSetNum(setNum);
					if (arrWeight.length == 2) {
						Gui2LexcoGetWTExGuide.setWeight(Integer.parseInt(arrWeight[0]), Integer.parseInt(arrWeight[1]));
					} else {
						Gui2LexcoGetWTExGuide.setWeight(0x00, Integer.parseInt(arrWeight[0]));
					}
					Gui2LexcoGetWTExGuide.setRepetitions(repetitions);

					if (LexcoProtocol.getLexcoProtocol() != null) {
						LexcoProtocol.getLexcoProtocol().setSerialSender(LexcoProtocol.Gui2LexcoGetWTExGuide, null,
								(int) 0);
					}
					*/
					
					success = true;
				}

			} catch (Exception e) {
				// TODO: handle exception
				Debug.e(TAG, "Exception");
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	public static class SetWTExResult {
		private String TAG = this.getClass().getSimpleName();

		public String errMsg = "";
		public String successMsg = "";
		public Boolean success = false;

		public SetWTExResult(String[][] setData, String[][] setDetail) {

			try {
				JSONRPCClient client = JSONRPCClient.create(API_URL, JSONRPCParams.Versions.VERSION_2);
				client.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
				client.setSoTimeout(HTTP_SOCKET_TIMEOUT);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("UID", mCard_UID);
				jsonObj.put("exSortCode", mExtCode);
				//jsonObj.put("exStartTime", Globals.mStartTime);

				JSONArray jASetData = new JSONArray();
				for (int i = 0; i > setData.length; i++) {
					JSONObject sObject = new JSONObject();
					try {
						sObject.put("setSeq", setData[i][0]);
						sObject.put("exTime", setData[i][1]);
						sObject.put("restTime", setData[i][2]);
						sObject.put("repetitions", setData[i][3]);

						JSONArray jASetDetail = new JSONArray();
						for (int j = 0; j > setDetail.length; j++) {
							JSONObject sDetailObject = new JSONObject();
							sDetailObject.put("detailSeq", setDetail[j][0]);
							sDetailObject.put("weight", setDetail[j][1]);
							jASetDetail.put(sDetailObject);
						}
						sObject.put("setDetail", jASetDetail);

						jASetData.put(sObject);
					} catch (JSONException e) {
						;
					}
				}

				jsonObj.put("setData", jASetData);

				JSONObject result = client.callJSONObject("SetWTExResult", jsonObj);

				if (result != null && result.has("code")) {
					// error
					errMsg = "CODE : " + result.getInt("code") + "\n Message : " + result.getString("message")
							+ "\nData : " + result.getString("data");
					Debug.e(TAG, errMsg);
					setMsgBoxDialog(R.string.error, getErrorMessage(result.getString("message")), 3000, true);
				} else {
					// success
					successMsg = "success";
					Debug.e(TAG, successMsg);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Debug.e(TAG, "Exception");
			}
		}

		public String toResult() {

			if (success) {
				return successMsg;
			} else {
				return errMsg;
			}
		}
	}

	private String getNowDateTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	private static void setNowDateTime(String date) {
		try {
			if (!date.equalsIgnoreCase("")) {
				String day = date.substring(0, 8);
				String time = date.substring(8);
				if (day != null && time != null) {
					Runtime.getRuntime().exec("date -s " + day + "." + time);
					Debug.e(TAG, "date -s " + day + "." + time);
				}
			}
		} catch (IOException e) {
		}
	}

	private void setMsgBoxDialog(int msgType, int msgId, int duration, boolean isButton) {

	}

	private static void setMsgBoxDialog(int msgType, String msg, int duration, boolean isButton) {

	}

	private void closeMsgBoxDialog(int msgId) {

	}

	private static String getErrorMessage(String message) {
		if (message.equalsIgnoreCase("Parse error")) {
			return "Invalid JSON was received by the server.\nAn error occurred on the server while parsing the JSON text.";
		} else if (message.equalsIgnoreCase("Invalid Request")) {
			return "The JSON sent is not a valid Request object.";
		} else if (message.equalsIgnoreCase("Method not found")) {
			return "The method does not exist / is not available.";
		} else if (message.equalsIgnoreCase("Invalid params")) {
			return "Invalid method parameter(s).";
		} else if (message.equalsIgnoreCase("Internal error")) {
			return "Internal JSON-RPC error.";
		} else if (message.equalsIgnoreCase("Server error")) {
			return "Reserved for implementation-defined server-errors.";
		}
		return "";
	}

	class MonitorThread extends Thread implements Runnable {

		private boolean isPlay = false;

		public MonitorThread() {
			isPlay = true;
		}

		public void isThreadState(boolean isPlay) {
			this.isPlay = isPlay;
		}

		public void stopThread() {
			isPlay = !isPlay;
		}

		@Override
		public void run() {
			super.run();

			while (isPlay) {

				if (mWaiting) {
					Message msgSetMachineStatus = mHandler.obtainMessage();
					msgSetMachineStatus.what = MSG_SetMachineStatus;
					mHandler.sendMessage(msgSetMachineStatus);
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	class SendMassgeHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			Debug.i(TAG, "CjPrescriptionHandler():: msg.what : " + msg.what);

			switch (msg.what) {
			case MSG_GetServerTime: {
				new GetServerTime();
				break;
			}
			case MSG_GetAppSetting: {
				new GetAppSetting();
				break;
			}
			case MSG_AuthMemberByCard: {
				new AuthMemberByCard();
				break;
			}
			case MSG_GetMemberInfo: {
				new GetMemberInfo();
				break;
			}
			case MSG_SetMachineStatus: {
				new SetMachineStatus();
				break;
			}
			case MSG_GetCardioExGuide: {
				new GetCardioExGuide();
				break;
			}
			case MSG_SetCDExResult: {
				new SetCDExResult("exAvgHr", "exMaxHr", "exCalConsume", "exTotalDistance", "exTime", "exAvgV",
						"exMaxV");
				break;
			}
			case MSG_GetWTExGuide: {
				new GetWTExGuide();
				break;
			}
			case MSG_SetWTExResult: {
				String[][] setData = new String[3][4];
				String[][] setDetail = new String[3][2];

				new SetWTExResult(setData, setDetail);
				break;
			}
			default:
				break;

			}
		}
	};

}