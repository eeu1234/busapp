package test.gps2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import test.gps.R;

public class SplashActivity extends Activity {
	int SPLASH_TIME = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				overridePendingTransition(0, android.R.anim.fade_in);
				startActivity(new Intent(SplashActivity.this, PlaceActivity.class));
				finish();
			}
		}, SPLASH_TIME);
	}
}