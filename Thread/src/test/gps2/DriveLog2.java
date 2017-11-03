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
	        
	      //Ÿ��Ʋ�� ���ֱ�
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.activity_buslog2);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			
	        
	        // �Ѿ�µ�����
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
    
			//����  ������ ��ư ����
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
	        
	        
	        
	    	//����
			
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

	        //�ؽ�Ʈ�� 2�� ����
	        mTxtDate = (TextView)findViewById(R.id.txtdate);

	        mTxtTime = (TextView)findViewById(R.id.txttime);
	      
	        
	        mTxtDate2 = (TextView)findViewById(R.id.txtdate2);
	        
	        mTxtTime2 = (TextView)findViewById(R.id.txttime2);

	        

	        //���� ��¥�� �ð��� ������������ Calendar �ν��Ͻ� ����

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

	        

	        UpdateNow();//ȭ�鿡 �ؽ�Ʈ�信 ������Ʈ ����.

	    }

	 

	    public void mOnClick(View v){

	        switch(v.getId()){

	            //��¥ ��ȭ���� ��ư�� ������ ��ȭ���ڸ� ������

	            case R.id.btnchangedate:

	                //���⼭ �����ʵ� �����

	                new DatePickerDialog(DriveLog2.this, mDateSetListener, mYear,

	                        mMonth, mDay).show();

	                break;

	                

	            //�ð� ��ȭ���� ��ư�� ������ ��ȭ���ڸ� ������

	            case R.id.btnchangetime:

	                //���⼭ �����ʵ� �����

	                new TimePickerDialog(DriveLog2.this, mTimeSetListener, mHour,

	                        mMinute, false).show();

	                break;
	                
	                //��¥ ��ȭ���� ��ư�� ������ ��ȭ���ڸ� ������
	                
	            case R.id.btnchangedate2:
	            	
	            	//���⼭ �����ʵ� �����
	            	
	            	new DatePickerDialog(DriveLog2.this, mDateSetListener2, mYear2,
	            			
	            			mMonth2, mDay2).show();
	            	
	            	break;
	            	
	            	
	            	
	            	//�ð� ��ȭ���� ��ư�� ������ ��ȭ���ڸ� ������
	            	
	            case R.id.btnchangetime2:
	            	
	            	//���⼭ �����ʵ� �����
	            	
	            	new TimePickerDialog(DriveLog2.this, mTimeSetListener2, mHour2,
	            			
	            			mMinute2, false).show();
	            	
	            	break;

	        }

	    }

	    

	    //��¥ ��ȭ���� ������ �κ�

	    DatePickerDialog.OnDateSetListener mDateSetListener =

	            new DatePickerDialog.OnDateSetListener() {

	                

	                @Override

	                public void onDateSet(DatePicker view, int year, int monthOfYear,

	                        int dayOfMonth) {

	                    // TODO Auto-generated method stub

	                    //����ڰ� �Է��� ���� �����µ�

	                    mYear = year;

	                    mMonth = monthOfYear;

	                    mDay = dayOfMonth;

	                    //�ؽ�Ʈ���� ���� ������Ʈ��

	                    UpdateNow();

	                }

	            };

	    

	    //�ð� ��ȭ���� ������ �κ�

	    TimePickerDialog.OnTimeSetListener mTimeSetListener =

	            new TimePickerDialog.OnTimeSetListener() {

	                

	                @Override

	                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

	                    // TODO Auto-generated method stub

	                    //����ڰ� �Է��� ���� �����µ�

	                    mHour = hourOfDay;

	                    mMinute = minute;

	                    

	                    //�ؽ�Ʈ���� ���� ������Ʈ��

	                    UpdateNow();

	                    

	                }

	            };

	            
	            //��¥ ��ȭ���� ������ �κ�
	            
	            DatePickerDialog.OnDateSetListener mDateSetListener2 =
	            		
	            		new DatePickerDialog.OnDateSetListener() {
	            	
	            	
	            	
	            	@Override
	            	
	            	public void onDateSet(DatePicker view, int year, int monthOfYear,
	            			
	            			int dayOfMonth) {
	            		
	            		// TODO Auto-generated method stub
	            		
	            		//����ڰ� �Է��� ���� �����µ�
	            		
	            		mYear2 = year;
	            		
	            		mMonth2 = monthOfYear;
	            		
	            		mDay2 = dayOfMonth;
	            		
	            		//�ؽ�Ʈ���� ���� ������Ʈ��
	            		
	            		UpdateNow2();
	            		
	            	}
	            	
	            };
	            
	            
	            
	            //�ð� ��ȭ���� ������ �κ�
	            
	            TimePickerDialog.OnTimeSetListener mTimeSetListener2 =
	            		
	            		new TimePickerDialog.OnTimeSetListener() {
	            	
	            	
	            	
	            	@Override
	            	
	            	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            		
	            		// TODO Auto-generated method stub
	            		
	            		//����ڰ� �Է��� ���� �����µ�
	            		
	            		mHour2 = hourOfDay;
	            		
	            		mMinute2 = minute;
	            		
	            		
	            		
	            		//�ؽ�Ʈ���� ���� ������Ʈ��
	            		
	            		UpdateNow2();
	            		
	            		
	            		
	            	}
	            	
	            };
	            
	            

	    //�ؽ�Ʈ���� ���� ������Ʈ �ϴ� �޼ҵ�

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
	    
	    
	    
	    //�˾������
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if(requestCode==1){
	            if(resultCode==RESULT_OK){
	               finish();
	            }
	        }
	    }


	    

	}

	 