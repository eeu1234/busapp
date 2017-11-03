package test.gps2;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import test.gps.R;

public class DriveLog2 extends Activity{
	public static Activity BusLog2Activity;
	
		String staffName="";
		String driverName="";
		String distance="";
		String destination="";
		String purpose="";
	
		String deviceSeq="";
	
	 	int mYear, mMonth, mDay, mHour, mMinute;
	 	int mYear2, mMonth2, mDay2, mHour2, mMinute2;

	    TextView mTxtDate;

	    TextView mTxtTime;

	    
	    TextView mTxtDate2;
	    
	    TextView mTxtTime2;

	    

	    @Override

	    protected void onCreate(Bundle savedInstanceState) {

	        super.onCreate(savedInstanceState);
			
	        
	        BusLog2Activity  = DriveLog2.this;
	        
	      //타이틀바 없애기
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.activity_buslog2);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			
	        
	        // 넘어온데이터
			Intent intent = getIntent();
			if(getIntent().getStringExtra("staffName") != null ||
					getIntent().getStringExtra("driverName") != null ||
							getIntent().getStringExtra("distance") != null	||
									getIntent().getStringExtra("destination") != null	||
											getIntent().getStringExtra("purpose") != null	
				){
				
				staffName=getIntent().getStringExtra("staffName");
				

				driverName=getIntent().getStringExtra("driverName");
				distance=getIntent().getStringExtra("distance");
				destination=getIntent().getStringExtra("destination");
				purpose=getIntent().getStringExtra("purpose");
				deviceSeq=getIntent().getStringExtra("deviceSeq");
			}
			Log.d("paraa", staffName);
    
			//이전  페이지 버튼 연결
			findViewById(R.id.backBtn).setOnClickListener(
					new Button.OnClickListener() {
						public void onClick(View v) {
							
							Intent postIntent = new Intent(getApplicationContext(), DriveLog.class);
							postIntent.putExtra("staffName", staffName);
							postIntent.putExtra("driverName", driverName);
							postIntent.putExtra("distance", distance);
							postIntent.putExtra("destination", destination);
							postIntent.putExtra("purpose", purpose);
							postIntent.putExtra("deviceSeq", deviceSeq);
							
							startActivity(postIntent);

							finish();

							
						}
					}
					
					
			);
	        
	        
	        
	    	//다음
			
			findViewById(R.id.nextBtn).setOnClickListener(
					new Button.OnClickListener() {
						public void onClick(View v) {
							
							Intent postIntent = new Intent(getApplicationContext(), SignActivity.class);
							postIntent.putExtra("staffName", staffName);
							postIntent.putExtra("driverName", driverName);
							postIntent.putExtra("distance", distance);
							postIntent.putExtra("destination", destination);
							postIntent.putExtra("purpose", purpose);
							
							
							
							postIntent.putExtra("mYear", mYear);
							postIntent.putExtra("mMonth", mMonth+1);
							postIntent.putExtra("mDay", mDay);
							postIntent.putExtra("mHour", mHour);
							postIntent.putExtra("mMinute", mMinute);

							
							postIntent.putExtra("mYear2", mYear2);
							postIntent.putExtra("mMonth2", mMonth2+1);
							postIntent.putExtra("mDay2", mDay2);
							postIntent.putExtra("mHour2", mHour2);
							postIntent.putExtra("mMinute2", mMinute2);
							
							
							postIntent.putExtra("deviceSeq", deviceSeq);
							
							  startActivityForResult(postIntent, 1);


							
						}
					}
					
					
			);

	        //텍스트뷰 2개 연결
	        mTxtDate = (TextView)findViewById(R.id.txtdate);

	        mTxtTime = (TextView)findViewById(R.id.txttime);
	      
	        
	        mTxtDate2 = (TextView)findViewById(R.id.txtdate2);
	        
	        mTxtTime2 = (TextView)findViewById(R.id.txttime2);

	        

	        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언

	        Calendar cal = new GregorianCalendar();

	        mYear = cal.get(Calendar.YEAR);

	        mMonth = cal.get(Calendar.MONTH);

	        mDay = cal.get(Calendar.DAY_OF_MONTH);

	        mHour = cal.get(Calendar.HOUR_OF_DAY);

	        mMinute = cal.get(Calendar.MINUTE);
	        
	        
	        mYear2 = cal.get(Calendar.YEAR);
	        
	        mMonth2 = cal.get(Calendar.MONTH);
	        
	        mDay2 = cal.get(Calendar.DAY_OF_MONTH);
	        
	        mHour2 = cal.get(Calendar.HOUR_OF_DAY);
	        
	        mMinute2 = cal.get(Calendar.MINUTE);

	        

	        UpdateNow();//화면에 텍스트뷰에 업데이트 해줌.

	    }

	 

	    public void mOnClick(View v){

	        switch(v.getId()){

	            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌

	            case R.id.btnchangedate:

	                //여기서 리스너도 등록함

	                new DatePickerDialog(DriveLog2.this, mDateSetListener, mYear,

	                        mMonth, mDay).show();

	                break;

	                

	            //시간 대화상자 버튼이 눌리면 대화상자를 보여줌

	            case R.id.btnchangetime:

	                //여기서 리스너도 등록함

	                new TimePickerDialog(DriveLog2.this, mTimeSetListener, mHour,

	                        mMinute, false).show();

	                break;
	                
	                //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌
	                
	            case R.id.btnchangedate2:
	            	
	            	//여기서 리스너도 등록함
	            	
	            	new DatePickerDialog(DriveLog2.this, mDateSetListener2, mYear2,
	            			
	            			mMonth2, mDay2).show();
	            	
	            	break;
	            	
	            	
	            	
	            	//시간 대화상자 버튼이 눌리면 대화상자를 보여줌
	            	
	            case R.id.btnchangetime2:
	            	
	            	//여기서 리스너도 등록함
	            	
	            	new TimePickerDialog(DriveLog2.this, mTimeSetListener2, mHour2,
	            			
	            			mMinute2, false).show();
	            	
	            	break;

	        }

	    }

	    

	    //날짜 대화상자 리스너 부분

	    DatePickerDialog.OnDateSetListener mDateSetListener =

	            new DatePickerDialog.OnDateSetListener() {

	                

	                @Override

	                public void onDateSet(DatePicker view, int year, int monthOfYear,

	                        int dayOfMonth) {

	                    // TODO Auto-generated method stub

	                    //사용자가 입력한 값을 가져온뒤

	                    mYear = year;

	                    mMonth = monthOfYear;

	                    mDay = dayOfMonth;

	                    //텍스트뷰의 값을 업데이트함

	                    UpdateNow();

	                }

	            };

	    

	    //시간 대화상자 리스너 부분

	    TimePickerDialog.OnTimeSetListener mTimeSetListener =

	            new TimePickerDialog.OnTimeSetListener() {

	                

	                @Override

	                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

	                    // TODO Auto-generated method stub

	                    //사용자가 입력한 값을 가져온뒤

	                    mHour = hourOfDay;

	                    mMinute = minute;

	                    

	                    //텍스트뷰의 값을 업데이트함

	                    UpdateNow();

	                    

	                }

	            };

	            
	            //날짜 대화상자 리스너 부분
	            
	            DatePickerDialog.OnDateSetListener mDateSetListener2 =
	            		
	            		new DatePickerDialog.OnDateSetListener() {
	            	
	            	
	            	
	            	@Override
	            	
	            	public void onDateSet(DatePicker view, int year, int monthOfYear,
	            			
	            			int dayOfMonth) {
	            		
	            		// TODO Auto-generated method stub
	            		
	            		//사용자가 입력한 값을 가져온뒤
	            		
	            		mYear2 = year;
	            		
	            		mMonth2 = monthOfYear;
	            		
	            		mDay2 = dayOfMonth;
	            		
	            		//텍스트뷰의 값을 업데이트함
	            		
	            		UpdateNow2();
	            		
	            	}
	            	
	            };
	            
	            
	            
	            //시간 대화상자 리스너 부분
	            
	            TimePickerDialog.OnTimeSetListener mTimeSetListener2 =
	            		
	            		new TimePickerDialog.OnTimeSetListener() {
	            	
	            	
	            	
	            	@Override
	            	
	            	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            		
	            		// TODO Auto-generated method stub
	            		
	            		//사용자가 입력한 값을 가져온뒤
	            		
	            		mHour2 = hourOfDay;
	            		
	            		mMinute2 = minute;
	            		
	            		
	            		
	            		//텍스트뷰의 값을 업데이트함
	            		
	            		UpdateNow2();
	            		
	            		
	            		
	            	}
	            	
	            };
	            
	            

	    //텍스트뷰의 값을 업데이트 하는 메소드

	    void UpdateNow(){

	        mTxtDate.setText(String.format("%d/%d/%d", mYear,

	                mMonth + 1, mDay));

	        mTxtTime.setText(String.format("%d:%d", mHour, mMinute));
	
	        mTxtDate2.setText(String.format("%d/%d/%d", mYear,
	        		
	        		mMonth + 1, mDay));
	        
	        mTxtTime2.setText(String.format("%d:%d", mHour, mMinute));
	        
	        
	        mYear2 = mYear;
	        
	        mMonth2 = mMonth;
	        
	        mDay2 = mDay;
	        
	        mHour2 = mHour;
	        
	        mMinute2 = mMinute;

	    }
	    void UpdateNow2(){
	    	
	    	
	    	mTxtDate2.setText(String.format("%d/%d/%d", mYear2,
	    			
	    			mMonth2 + 1, mDay2));
	    	
	    	mTxtTime2.setText(String.format("%d:%d", mHour2, mMinute2));
	    	
	    }
	    
	    
	    
	    //팝업결과로
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if(requestCode==1){
	            if(resultCode==RESULT_OK){
	               finish();
	            }
	        }
	    }


	    

	}

	 