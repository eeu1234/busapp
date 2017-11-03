package test.gps2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import test.gps.R;

public class DriveLog extends Activity {
	public static Activity BusLogActivity; 
	EditText staffNameText;
	EditText driverNameText;
	EditText distanceText;
	EditText destinationText;
	EditText purposeText;

	String staffName="";
	String driverName="";
	String distance="";
	String destination="";
	String purpose="";
	
	
	String deviceSeq="";
	@Override

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		BusLogActivity = DriveLog.this;

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_buslog);

		staffNameText = (EditText) findViewById(R.id.staffName);
		driverNameText = (EditText) findViewById(R.id.driverName);
		distanceText = (EditText) findViewById(R.id.distance);
		destinationText = (EditText) findViewById(R.id.destination);
		purposeText = (EditText) findViewById(R.id.purpose);

		
		
		
		
		
		//뒤로가기로 넘어온경우
				Intent intent = getIntent();
				if(getIntent().getStringExtra("staffName") != null ||
						getIntent().getStringExtra("driverName") != null ||
								getIntent().getStringExtra("distance") != null	||
									getIntent().getStringExtra("destination") != null	||
									getIntent().getStringExtra("purpose") != null	
					){
					
					staffName=getIntent().getStringExtra("staffName");
					System.out.println(staffName);
					driverName=getIntent().getStringExtra("driverName");
					distance=getIntent().getStringExtra("distance");
					destination=getIntent().getStringExtra("destination");
					purpose=getIntent().getStringExtra("purpose");
					deviceSeq=getIntent().getStringExtra("deviceSeq");
					
					staffNameText.setText(staffName);
					driverNameText.setText(driverName);
					distanceText.setText(distance);
					destinationText.setText(destination);
					purposeText.setText(purpose);
				}
				//다음버튼  연결
				findViewById(R.id.backBtn).setOnClickListener(
						new Button.OnClickListener() {
							public void onClick(View v) {
								
								finish();
								
								
							}
						}
						
						
						);
				

				
		
		//다음버튼  연결
		findViewById(R.id.nextBtn).setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent postIntent = new Intent(getApplicationContext(), DriveLog2.class);
						postIntent.putExtra("staffName", staffNameText.getText().toString());
						postIntent.putExtra("driverName", driverNameText.getText().toString());
						postIntent.putExtra("distance", distanceText.getText().toString());
						postIntent.putExtra("destination", destinationText.getText().toString());
						postIntent.putExtra("purpose", purposeText.getText().toString());
						postIntent.putExtra("deviceSeq", deviceSeq);
						startActivity(postIntent);


						
					}
				}
				
				
		);
		
	}

}
