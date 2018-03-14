
#Assignment 001


**Here is my Weather Application Demo**

  ![demo](https://github.com/lining9717/weather-application/blob/master/display/assignment01_demo.gif?raw=true)
    
**1. Modifying the activity_main.xml.**

			<TextView
                android:id="@+id/first_day"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:gravity="center"
                android:text="mon"
                android:textAllCaps="true"
                android:textColor="#ffffff"
                android:onClick="choose_day"
                android:background="@drawable/select_background"
                />

            <TextView
                android:id="@+id/second_day"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:gravity="center"
                android:text="tue"
                android:textAllCaps="true"
                android:textColor="#909090"
                android:onClick="choose_day"
                />

			<TextView
                android:id="@+id/third_day"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:gravity="center"
                android:text="wed"
                android:textAllCaps="true"
                android:textColor="#909090"
                android:onClick="choose_day"
                />

			<TextView
                android:id="@+id/fourth_day"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:gravity="center"
                android:text="thu"
                android:textAllCaps="true"
                android:textColor="#909090"
                android:onClick="choose_day"
                />

**2. The methord of choose_day.**

		 public void choose_day(View view) {
	        findViewById(R.id.first_day).setBackgroundColor(Color.WHITE);
	        findViewById(R.id.second_day).setBackgroundColor(Color.WHITE);
	        findViewById(R.id.third_day).setBackgroundColor(Color.WHITE);
	        findViewById(R.id.fourth_day).setBackgroundColor(Color.WHITE);
	        ((TextView)findViewById(R.id.first_day)).setTextColor(getResources().getColor(R.color.colorText));
	        ((TextView)findViewById(R.id.second_day)).setTextColor(getResources().getColor(R.color.colorText));
	        ((TextView)findViewById(R.id.third_day)).setTextColor(getResources().getColor(R.color.colorText));
	        ((TextView)findViewById(R.id.fourth_day)).setTextColor(getResources().getColor(R.color.colorText));
	        view.setBackground(getResources().getDrawable(R.drawable.select_background));
	        ((TextView)view).setTextColor(Color.WHITE);
	    }



**3. Adding the new icon to the application.** 
  
&nbsp;&nbsp;&nbsp;&nbsp;Here is my icon:![demo](https://github.com/lining9717/weather-application/blob/master/app/src/main/res/drawable/logo.png?raw=true)

And modifying AndroidManifest.xml:

	<?xml version="1.0" encoding="utf-8"?>
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="mg.studio.weatherappdesign">
    <uses-permission android:name="android.permission.INTERNET"/>
    <application
        android:allowBackup="true"
        android:icon="@drawable/logo"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.NoActionBar">
        <activity android:name=".MainActivity"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
	</manifest>

**4. Adding the refresh button.**

&nbsp;&nbsp;&nbsp;&nbsp;In activity_main.xml:
	
	<ImageView
       android:id="@+id/img_refresh"
       android:layout_width="24dp"
       android:layout_height="24dp"
       android:layout_centerVertical="true"
       android:layout_toLeftOf="@+id/tv_temperature"
       android:onClick="refreshClick"
       app:srcCompat="@drawable/refresh_icon" />	

&nbsp;&nbsp;&nbsp;&nbsp;refresh icon:![demo](https://github.com/lining9717/weather-application/blob/master/app/src/main/res/drawable/refresh_icon.png?raw=true)

**5. when the refresh button is pressed, the temperature, the date and the day of the week are all updated.**

&nbsp;&nbsp;&nbsp;&nbsp;In MainActivity.java:
	
	public String getWeekOfDate(Date date) {
        String[] weekDays = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public void refreshClick(View view) {
        RotateAnimation  mFlipAnimation = new RotateAnimation(0, -360, RotateAnimation.RELATIVE_TO_SELF,
                0.5f,RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(1000);
        mFlipAnimation.setFillAfter(true);
        findViewById(R.id.img_refresh).startAnimation(mFlipAnimation);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String now_date = dateFormat.format(date);
        ((TextView)findViewById(R.id.tv_date)).setText(now_date);
        ((TextView)findViewById(R.id.tv_weekday)).setText(getWeekOfDate(date));
        new DownloadUpdate().execute();
    }






