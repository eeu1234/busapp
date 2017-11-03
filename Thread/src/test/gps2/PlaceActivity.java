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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import test.gps.R;

public class PlaceActivity extends Activity implements Serializable {
	private static final long serialVersionUID = 1L;
	int SPLASH_TIME = 2000;

	public static ArrayList<BusStopDTO> list;
	public EditText deviceIdText;

	private AQuery aq = new AQuery(this);
	String str = null;
	 //String url ="http://ybus.kro.kr/ybus/ybus/exchangeJson/getBsList.do";  // deviceId 전
	 //String url ="http:// .63.89.33:8090/spring/android/ajaxFirst.action";  // deviceId 전
//	 String url ="http://eeu1234.iptime.org:8080/spring/android/ajaxFirst.action";  // deviceId 전
	 String url ="http://cambus.kr/spring/android/ajaxFirst.action";  // deviceId 전
	// String url ="http://192.168.1.243:8080/spring/android/ajaxFirst.action";  // miu
	 
	 
	// "http://asmr.kro.kr:8181/ybus/ybus/exchangeJson/getBsList.do?busId=gs1";
	
	String deviceId = "";
	HashMap<String, Object> map = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		deviceIdText = (EditText) findViewById(R.id.deviceId);
		// 화면 항상 켜지
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


		
		//deviceId 불러온다.
		deviceId = device();
		deviceIdText.setText(deviceId);

		// json 통신 시작
		// id값 날리기
		map.put("deviceId", deviceId);
		//url += deviceId;
		//Log.d("adam", "deviceId"+deviceId);
		//Log.d("adam", "url=   "+url);
		data();

	}

	public void data() {
			//url로 map (Post)타입으로 보낸다.
		aq.ajax(url, map, String.class, new AjaxCallback<String>() {
			@Override
			public void callback(String url, String object, AjaxStatus status) {

				if (object != null) {
				Log.d("adam", "obj=   "+object);

					try {

						try {

							JSONObject jsonResponse = new JSONObject(object);

							JSONObject tempJson = jsonResponse.getJSONObject("businfo");
							
							Log.d("adam", "tempJson=   "+tempJson);
							
							String deviceSeq = tempJson.getString("deviceSeq");
							String intervalTime = tempJson.getString("intervalTime");
							String intervalDistance = tempJson.getString("intervalDistance");
							String newUrl = tempJson.getString("newUrl");

							Log.d("adam", "deviceSeq=   "+deviceSeq);
							Log.d("adam", "intervalTime=   "+intervalTime);
							Log.d("adam", "intervalDistance=   "+intervalTime);
							Log.d("adam", "newUrl =   "+newUrl);
							
							Intent intent = new Intent(getBaseContext(), MainActivity.class);
							intent.putExtra("deviceSeq", deviceSeq);// 버스 seq
							intent.putExtra("intervalTime", intervalTime);// 전송 인터벌시간
							intent.putExtra("intervalDistance", intervalDistance);// 전송 인터벌거리오차
							intent.putExtra("newUrl", newUrl);// 전송 경로
							intent.putExtra("deviceId", deviceId);// 버스 키 값
							startActivity(intent);
							finish();

						} catch (JSONException e) {
							e.printStackTrace();// 에러 메시지

							// 실패 시 재요청 함수
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {

									data();
									System.out.println("재요청");
								}
							}, 5000);

						}
					} catch (Exception e) {
						Log.d("adam", "예외발생:" + e.toString());

						// 실패 시 재요청 함수
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {

								data();
								System.out.println("재요청");
							}
						}, 5000);

					}

				} else {
					Log.d("adam", "object null"); 
					// 실패 시 재요청 함수
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {

							data();
							Toast.makeText(getApplicationContext(), "기기를 등록 해주세요 ", 2000).show();

							System.out.println("재요청");
						}
					}, 5000);
				}
			}//callback 끝

		});//ajax 끝
	}

	public String device() { // 기기 고유 값 찾아오기
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
