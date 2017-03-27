package test.gps2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DAO.BusStopDTO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import test.gps.R;

//nox_adb.exe connect 127.0.0.1:62001
public class MainActivity extends Activity {

	private LocationListener locListenD;
	LocationManager lm;

	public EditText etLatitude;
	public EditText etLongitude;
	public EditText etAccuracy;
	public EditText etSpeed;
	public EditText situation;
	public EditText myStop;
	public EditText countLocation;
	public EditText turnBusStopNum;
	public EditText upDown;
	public EditText distanceText;

	String busNum = "";
	String intervalTime = "";
	String newUrl = "";
	Location locationA = new Location("point A");// 현재 위치

	int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etLatitude = (EditText) findViewById(R.id.etLatitude);
		etLongitude = (EditText) findViewById(R.id.etLongitude);
		etAccuracy = (EditText) findViewById(R.id.etAccuracy);
		etSpeed = (EditText) findViewById(R.id.etSpeed);
		situation = (EditText) findViewById(R.id.situation);
		myStop = (EditText) findViewById(R.id.myStop);
		countLocation = (EditText) findViewById(R.id.countLocation);
		turnBusStopNum = (EditText) findViewById(R.id.turnBusStopNum);
		upDown = (EditText) findViewById(R.id.upDown);
		distanceText = (EditText) findViewById(R.id.distanceText);

		// GPS 켜져있는 지 여부 확인 후 안되있다면 설정창 뜸
		chkGpsService();

		// 화면 항상 켜지
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Intent intent = getIntent();
		busNum = getIntent().getStringExtra("busNum");
		intervalTime = getIntent().getStringExtra("intervalTime");
		newUrl = getIntent().getStringExtra("newUrl");

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		} else {
			Location ll = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (ll != null) {
			}

			locListenD = new MyLocationListener();

			try {

				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Integer.parseInt(intervalTime), 50, locListenD);
				

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("request error");

			}

		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onStop() {
		super.onStop();
		// locListenD = new MyLocationListener();
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		// lm.removeUpdates(locListenD);

	}

	@Override
	protected void onDestroy() {
		// back key로 나갔을때
		super.onStop();
		// locListenD = new MyLocationListener();
		// lm.removeUpdates(locListenD);

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locListenD = new MyLocationListener();
		try {//										GPS_PROVIDER  ,	 intervalTime(인터벌타임)         , 미터 ,            );
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Integer.parseInt(intervalTime), 1000, locListenD);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			try {
				count++;
				// 정확도 50m 이하일 때 만 실행
				if (location.getAccuracy() < 50) {
					// 현재 위도, 경도 받아서 SET 시킴
					locationA.setLatitude(location.getLatitude());
					locationA.setLongitude(location.getLongitude());
					



					new HttpTask().execute();


					
					// 각각 ui세팅
					etLatitude.setText(Double.toString(location.getLatitude()));
					etLongitude.setText(Double.toString(location.getLongitude()));
					etAccuracy.setText(Float.toString(location.getAccuracy()));
					etSpeed.setText(Float.toString(location.getSpeed()));
					countLocation.setText(count+"");
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// Toast.makeText(MainActivity.this, provider + " Disabled",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			// Toast.makeText(MainActivity.this, provider + " Enabled",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			case GpsStatus.GPS_EVENT_STARTED:
				Toast.makeText(MainActivity.this, "GPS_EVENT_STARTED", Toast.LENGTH_SHORT).show();
				break;

			case GpsStatus.GPS_EVENT_STOPPED:
				Toast.makeText(MainActivity.this, "GPS_EVENT_STOPPED", Toast.LENGTH_SHORT).show();
				break;

			case GpsStatus.GPS_EVENT_FIRST_FIX:
				Toast.makeText(MainActivity.this, "GPS_EVENT_FIRST_FIX", Toast.LENGTH_SHORT).show();
				break;

			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				Toast.makeText(MainActivity.this, "GPS_EVENT_SATELLITE_STATUS", Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};

	// GPS 설정 체크
	private boolean chkGpsService() {

		String gps = android.provider.Settings.Secure.getString(getContentResolver(),
				android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


		if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

			// GPS OFF 일때 Dialog 표시
			AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
			gsDialog.setTitle("위치 서비스 설정");
			gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
			gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// GPS설정 화면으로 이동
					Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					startActivity(intent);
				}
			}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			}).create().show();
			return false;

		} else {
			return true;
		}
	}

	// 서버전송
	class HttpTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... voids) {
			// TODO Auto-generated method stub
			try {
				HttpPost request = new HttpPost(newUrl);
				
				
				// 전달할 인자들
				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
				nameValue.add(new BasicNameValuePair("bpk", busNum));
				nameValue.add(new BasicNameValuePair("blat", locationA.getLatitude() + ""));
				nameValue.add(new BasicNameValuePair("blng", locationA.getLongitude() + ""));
				System.out.println("자료전송 성공");

				// 웹 접속 - utf-8 방식으로
				HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
				request.setEntity(enty);

				HttpClient client = new DefaultHttpClient();
				HttpResponse res = client.execute(request);
				// 웹 서버에서 값받기
				HttpEntity entityResponse = res.getEntity();
				InputStream im = entityResponse.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

				String total = "";
				String tmp = "";
				// 버퍼에있는거 전부 더해주기
				// readLine -> 파일내용을 줄 단위로 읽기
				while ((tmp = reader.readLine()) != null) {
					if (tmp != null) {
						total += tmp;
					}
				}
				im.close();
				// 결과창뿌려주기 - UI 변경시 에러
				// result.setText(total);
				return total;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 오류시 null 반환
			return null;
		}
		// asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
		// AsynoTask 는 preExcute - doInBackground - postExcute 순으로 자동으로 실행됩니다.

		protected void onPostExecute(String value) {// ui는 여기서 변경
			super.onPostExecute(value);
			// result=value;
			// System.out.println(value);
			// Toast.makeText(getApplicationContext(), value,
			// Toast.LENGTH_LONG).show();

		}
	}

}// class