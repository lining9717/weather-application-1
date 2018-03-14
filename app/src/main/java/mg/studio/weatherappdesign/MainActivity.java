package mg.studio.weatherappdesign;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

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

    private class DownloadUpdate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://mpianatra.com/Courses/info.txt";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String temperature) {
            temperature = temperature.replaceAll("\\s+", "");
            //Update the temperature displayed
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
        }
    }
}
