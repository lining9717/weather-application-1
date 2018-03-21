package mg.studio.weatherappdesign;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Forecast[] forecast;
    private int[] image = {R.drawable.sunny_small,R.drawable.windy_small,
            R.drawable.rainy_small, R.drawable.partly_sunny_small};
    public final static int SUNNY = 0;
    public final static int WINDY = 1;
    public final static int RAINY = 2;
    public final static int CLOUDY = 3;
    private final  static String location = "沙坪坝";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {

    }


    public String getWeekOfDate(Date date) {
        String[] weekDays = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public String[] getNextFourDays(Date date){
        String[] weekDays = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
        String[] nextDays = new String[4];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
       for(int i=0;i<4;i++){
           nextDays[i] = weekDays[(w+i+1)%7];
       }
        return nextDays;
    }


    public void refreshClick(View view) {
        forecast = new Forecast[5];
        new DownloadUpdate().execute();
        rotateRefesh();
    }

    //旋转refresh按钮
    public void rotateRefesh(){
        RotateAnimation  mFlipAnimation = new RotateAnimation(0, -360, RotateAnimation.RELATIVE_TO_SELF,
                0.5f,RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(1000);
        mFlipAnimation.setFillAfter(true);
        findViewById(R.id.img_refresh).startAnimation(mFlipAnimation);

    }

    //获取日期及天气信息
    public void setInfo(int i){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        calendar.add(Calendar.DAY_OF_YEAR, i);
        Date date = calendar.getTime();
        String now_date = sdf.format(date);
        ((TextView)findViewById(R.id.tv_date)).setText(now_date);
        ((TextView)findViewById(R.id.tv_weekday)).setText(getWeekOfDate(date));

        int temperature = forecast[i].getTemperature();
        Log.i("temperature","------------>"+temperature);

        ((TextView)findViewById(R.id.temperature)).setText(temperature+"");
        ((ImageView)findViewById(R.id.img_weather_condition)).setImageResource(image[forecast[i].getType()]);
    }

    public void choose_day(View view) {
        init();
        view.setBackground(getResources().getDrawable(R.drawable.select_background));
        ((TextView)view).setTextColor(Color.WHITE);
        if(view == findViewById(R.id.first_day)){
            setInfo(1);
        }
        if(view == findViewById(R.id.second_day)){
            setInfo(2);
        }
        if(view == findViewById(R.id.third_day)){
            setInfo(3);
        }
        if(view == findViewById(R.id.fourth_day)){
            setInfo(4);
        }
    }


    public void init(){
        ((TextView)findViewById(R.id.tv_location)).setText(location);
        findViewById(R.id.first_day).setBackgroundColor(Color.WHITE);
        findViewById(R.id.second_day).setBackgroundColor(Color.WHITE);
        findViewById(R.id.third_day).setBackgroundColor(Color.WHITE);
        findViewById(R.id.fourth_day).setBackgroundColor(Color.WHITE);
        ((TextView)findViewById(R.id.first_day)).setTextColor(getResources().getColor(R.color.colorText));
        ((TextView)findViewById(R.id.second_day)).setTextColor(getResources().getColor(R.color.colorText));
        ((TextView)findViewById(R.id.third_day)).setTextColor(getResources().getColor(R.color.colorText));
        ((TextView)findViewById(R.id.fourth_day)).setTextColor(getResources().getColor(R.color.colorText));
        Date date = new Date();
        String[] nextDays = getNextFourDays(date);
        ((TextView)findViewById(R.id.first_day)).setText(nextDays[0]);
        ((TextView)findViewById(R.id.second_day)).setText(nextDays[1]);
        ((TextView)findViewById(R.id.third_day)).setText(nextDays[2]);
        ((TextView)findViewById(R.id.fourth_day)).setText(nextDays[3]);
        ((ImageView)findViewById(R.id.icon_first)).setImageResource(image[forecast[1].getType()]);
        ((ImageView)findViewById(R.id.icon_second)).setImageResource(image[forecast[2].getType()]);
        ((ImageView)findViewById(R.id.icon_third)).setImageResource(image[forecast[3].getType()]);
        ((ImageView)findViewById(R.id.icon_fourth)).setImageResource(image[forecast[4].getType()]);
        setInfo(0);
    }

    private class DownloadUpdate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                String city = null;
                city = java.net.URLEncoder.encode(location, "utf-8");
                String apiUrl = String.format("https://www.sojson.com/open/api/weather/json.shtml?city=%s",city);
                URL url= new URL(apiUrl);
                URLConnection open = url.openConnection();
                InputStream input = open.getInputStream();
                result = IOUtils.toString(input,"utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String json) {
            if(json == null){
                Toast.makeText(MainActivity.this,"No infomation",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject obj = new JSONObject(json);
                JSONObject data = obj.getJSONObject("data");
                JSONArray array = data.getJSONArray("forecast");
                for(int i=0;i<array.length();i++){
                    JSONObject temp = array.getJSONObject(i);
                    forecast[i] = new Forecast(temp.getString("low"),temp.getString("high"),temp.getString("type"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            init();
        }
    }
}
