package test.gps2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import DAO.BusStopDTO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PlaceActivity extends Activity implements Serializable {
	private static final long serialVersionUID = 1L;
	int SPLASH_TIME = 2000;

	public static ArrayList<BusStopDTO> list;

	private AQuery aq = new AQuery(this);
	String str = null;
	 String url ="http://ybus.kro.kr/ybus/ybus/exchangeJson/getBsList.do";  // deviceId ��
	 
	 
	// "http://asmr.kro.kr:8181/ybus/ybus/exchangeJson/getBsList.do?busId=gs1";
	//String url = "http://eeu1234.iptime.org:8090/parameter/getparameter.do";
	
	String deviceId = "";
	HashMap<String, Object> map = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		//deviceId �ҷ��´�.
		deviceId = device();

		// json ��� ����
		// id�� ������
		map.put("busId", deviceId);
		//url += deviceId;
		//Log.d("adam", "deviceId"+deviceId);
		//Log.d("adam", "url=   "+url);
		data();

	}

	public void data() {
			//url�� map (Post)Ÿ������ ������.
		aq.ajax(url, map, String.class, new AjaxCallback<String>() {
			@Override
			public void callback(String url, String object, AjaxStatus status) {

				if (object != null) {
				//Log.d("adam", "obj=   "+object);

					try {

						try {
							JSONObject jsonResponse = new JSONObject(object);
							JSONObject tempJson = jsonResponse.getJSONObject("businfo");
							
							//Log.d("adam", "tempJson=   "+tempJson);
							
							String busNum = tempJson.getString("bpk");
							String intervalTime = tempJson.getString("term");
							String newUrl = tempJson.getString("url");

							Log.d("adam", "busNum=   "+busNum);
							Log.d("adam", "intervalTime=   "+intervalTime);
							Log.d("adam", "newUrl=   "+newUrl);
							
							Intent intent = new Intent(getBaseContext(), MainActivity.class);
							intent.putExtra("busNum", busNum);// Ű��,������ ����
							intent.putExtra("intervalTime", intervalTime);// Ű��,������ ����
							intent.putExtra("newUrl", newUrl);// Ű��,������ ����
							startActivity(intent);
							finish();

						} catch (JSONException e) {
							e.printStackTrace();// ���� �޽���

							// ���� �� ���û �Լ�
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {

									data();
									System.out.println("���û");
								}
							}, 5000);

						}
					} catch (Exception e) {
						Log.d("adam", "���ܹ߻�:" + e.toString());

						// ���� �� ���û �Լ�
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {

								data();
								System.out.println("���û");
							}
						}, 5000);

					}

				} else {
					Log.d("adam", "object null"); 
					// ���� �� ���û �Լ�
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {

							data();
							System.out.println("���û");
						}
					}, 5000);
				}
			}//callback ��

		});//ajax ��
	}

	public String device() { // ��� ���� �� ã�ƿ���
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		deviceId = deviceUuid.toString();

		return deviceId;
	}
}
