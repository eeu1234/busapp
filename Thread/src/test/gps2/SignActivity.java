
package test.gps2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import DAO.BusLogDTO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import test.gps.R;

public class SignActivity extends Activity implements OnClickListener {
	
	
	
//	String urlFront = "http://192.168.1.243:8080";
//	String urlFront = "http://eeu1234.iptime.org:8080";
	String urlFront = "http://cambus.kr";
	String urlDetail = "/spring/android/signLog.action";
	String url = urlFront + urlDetail;

	
	BusLogDTO logDto = new BusLogDTO();
	int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
	int mYear2 = 0, mMonth2 = 0, mDay2 = 0, mHour2 = 0, mMinute2 = 0;

	int curMode = 1;
	private int curColor = Color.BLACK;
	private int curWidth = 5;
	// Current Mode, Color, Width variable

	final int PENMODE = 1, ERASERMODE = 2;
	// Mode Change

	private Paint mPaint;
	private Path mPath;

	MyView myView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		
		//
		DriveLog BusLogActivity = (DriveLog)DriveLog.BusLogActivity;
		DriveLog2 BusLog2Activity = (DriveLog2)DriveLog2.BusLog2Activity;
		
		
		
		//�׸��� �׸���
		myView = new MyView(this);
		setContentView(R.layout.activity_sign);

		// �Ѿ�µ�����
		Intent intent = getIntent();
		if (getIntent().getStringExtra("staffName") != null || getIntent().getStringExtra("driverName") != null
				|| getIntent().getStringExtra("distance") != null || getIntent().getStringExtra("destination") != null
				|| getIntent().getStringExtra("purpose") != null) {

			logDto.setStaffName("");
			logDto.setDriverName("");
			logDto.setDistance("");
			logDto.setDestination("");
			logDto.setPurpose("");
			logDto.setDeviceSeq("");

			logDto.setStaffName(getIntent().getStringExtra("staffName"));
			logDto.setDriverName(getIntent().getStringExtra("driverName"));
			logDto.setDistance(getIntent().getStringExtra("distance"));
			logDto.setDestination(getIntent().getStringExtra("destination"));
			logDto.setPurpose(getIntent().getStringExtra("purpose"));
			logDto.setDeviceSeq(getIntent().getStringExtra("deviceSeq"));

			
			logDto.setStartTime(
				getIntent().getIntExtra("mYear", 0)			+"-"+
				getIntent().getIntExtra("mMonth", 0)		+"-"+
				getIntent().getIntExtra("mDay", 0)			+" "+
				getIntent().getIntExtra("mHour", 0)			+":"+
				getIntent().getIntExtra("mMinute", 0)
					); 
			logDto.setEndTime(
							getIntent().getIntExtra("mYear2", 0)			+"-"+
							getIntent().getIntExtra("mMonth2", 0)			+"-"+
							getIntent().getIntExtra("mDay2", 0)				+" "+
							getIntent().getIntExtra("mHour2", 0)			+":"+
							getIntent().getIntExtra("mMinute2", 0)
					); 

			
			
			

			Log.d("paraa", logDto.getStaffName() + "/" + logDto.getDriverName() + "/" + logDto.getDestination() + "/" + logDto.getDestination()+ "/" + logDto.getPurpose());
			Log.d("paraa", logDto.getStartTime());
			Log.d("paraa", logDto.getEndTime());
			
		}

	}

	// Setting for Pen-Mode
	protected void penInit() {
		mPaint = new Paint();
		mPaint.setDither(true); // ����� ȿ��, ������ �������� ��Ȯ�� ������
		mPaint.setColor(curColor); // ���� ����
		mPaint.setStyle(Paint.Style.STROKE); // STROKE�� �Ѹ� �׸���
		mPaint.setStrokeJoin(Paint.Join.ROUND); // �� �κ��� �ձ۰�
		mPaint.setStrokeCap(Paint.Cap.ROUND); // �𼭸��� �ձ۰�
		mPaint.setStrokeWidth(curWidth); // �� ����
		mPaint.setAntiAlias(true); // ��迡 �߰��� �����ؼ� ǰ�� �����ϱ�
	}

	// Setting for Eraser-Mode
	protected void eraserInit() {
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(curWidth);
		mPaint.setAntiAlias(true);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR)); // ��������
																			// �����
	}

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_sign);

		LinearLayout myLinear = (LinearLayout) findViewById(R.id.myLinear);
		myLinear.addView(myView);

		penInit();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
	}

	class MyView extends View {

		public Bitmap mBitmap;
		private Canvas mCanvas;
		private Paint mBitmapPaint;
		float startX, startY;
		float stopX, stopY;

		public MyView(Context context) {
			super(context);

			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			mBitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);

			mBitmapPaint = new Paint(Paint.DITHER_FLAG);

			mCanvas = new Canvas(mBitmap);

			mPath = new Path();
			mCanvas.drawColor(Color.WHITE); // Background Color
			curMode = PENMODE;
		}

		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.BLUE);
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			// �� �κ��� ���� ��� ���찳 ��� �� �Ͼ� ���� �׷���

			if (curMode != ERASERMODE)
				canvas.drawPath(mPath, mPaint);
		}

		private float mX, mY;

		// Touch Start, Drag Start
		private void touch_start(float x, float y) {
			mPath.reset();
			switch (curMode) {
			case PENMODE:
			case ERASERMODE:
				mPath.moveTo(x, y);
				mX = x;
				mY = y;
				break;

			}
		}

		// Dragging...
		private void touch_move(float x, float y) {

			switch (curMode) {
			case PENMODE:
			case ERASERMODE:
				mPath.lineTo(x, y);
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;

			}
		}

		// Drag Finish
		private void touch_up(float x, float y) {

			switch (curMode) {
			case PENMODE:
			case ERASERMODE:
				mPath.lineTo(mX, mY);
				break;

			}
			mCanvas.drawPath(mPath, mPaint);
		}

		// Event Processing
		public boolean onTouchEvent(MotionEvent event) {

			int action = event.getAction();

			switch (action) {

			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				touch_start(startX, startY);
				break;

			case MotionEvent.ACTION_MOVE:
				stopX = event.getX();
				stopY = event.getY();
				touch_move(stopX, stopY);
				break;

			case MotionEvent.ACTION_UP:
				stopX = event.getX();
				stopY = event.getY();
				touch_up(stopX, stopY);
				break;
			}
			invalidate(); // onDraw�� ȣ���ϰ� �ȴ�. �� �������� �׸���
			return true;
		}
	}

	// Option
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	// Option Event Processing
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.initialize: // ��� �ʱ�ȭ ��Ų��.
			curColor = Color.BLACK;
			mPath.reset();
			onResume();
			break;

		}
		return true;
	}

	// Ȯ�� ��ư Ŭ��
	public void mOnClose(View v) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(SignActivity.this);
		dialog.setMessage("Send now?").setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 'YES'
						String filePath = "/sdcard/";
						String fName = "sign_" + System.currentTimeMillis() + ".png";
						logDto.setImgName(fName);
						logDto.setFileName(filePath + fName);

						FileOutputStream fos = null;

						try {
							fos = new FileOutputStream(logDto.getFileName());
							if (fos != null) {

								myView.mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
								fos.close();
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

						try {

							//����������
							new HttpTask().execute();

							 Intent intent = new Intent();
						        intent.putExtra("result", "Close Popup");
						        setResult(RESULT_OK, intent);


							finish();
							

						} catch (Exception e) {
							// TODO: handle exception
							Log.d("adam", e.toString());
						}

					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 'No'
						finish();
						return;
					}
				});
		AlertDialog alert = dialog.create();
		alert.show();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// �ٱ����̾� Ŭ���� �ȴ�����
		if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			return false;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		// �ȵ���̵� ���ư ����
		return;
	}

	
	// ��������
		class HttpTask extends AsyncTask<Void, Void, String> {

			@Override
			protected String doInBackground(Void... voids) {
				// TODO Auto-generated method stub
				try { 
					// ������ ������ ������ �κ� try { 
					HttpPost request = new HttpPost(url);//FileBody ��ü�� �̿��ؼ� ������ �޾ƿ�
					Log.d("paraa","url:"+url);
					File signImg = new File(logDto.getFileName());
					
				  
//					�̹��� ����
					FileBody bin = new FileBody(signImg);	
					MultipartEntity multipart = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE);
					multipart.addPart("signImg", bin); 
					multipart.addPart("staffName", new StringBody(logDto.getStaffName()));
					multipart.addPart("driverName", new StringBody(logDto.getDriverName()));
					multipart.addPart("distance", new StringBody(logDto.getDistance()));
					multipart.addPart("purpose", new StringBody(logDto.getPurpose()));
					multipart.addPart("destination", new StringBody(logDto.getDestination()));
					multipart.addPart("startTime", new StringBody(logDto.getStartTime()));
					multipart.addPart("endTime", new StringBody(logDto.getEndTime()));
					multipart.addPart("deviceSeq", new StringBody(logDto.getDeviceSeq()));
					multipart.addPart("imgName", new StringBody(logDto.getImgName()));
					

					
					
					request.setEntity(multipart); // Multipart�� post ���Ŀ� ���� 

					
					// �� ���� - utf-8 �������

					HttpClient client = new DefaultHttpClient();
					HttpResponse res = client.execute(request);
					
					
					
					signImg.delete();
					System.out.println("�ڷ����� ����");

				
				} catch (IOException e) {
					e.printStackTrace();
					Log.d("adam",e.toString());
				}
				return null;
			}
				// ������ null ��ȯ
			protected void onPostExecute(String value) {// ui�� ���⼭ ����
				super.onPostExecute(value);
				// result=value;
				// System.out.println(value);
				// Toast.makeText(getApplicationContext(), value,
				// Toast.LENGTH_LONG).show();

			}
		}
	
}