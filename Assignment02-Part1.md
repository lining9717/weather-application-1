# <center>Assignment 002------Part 1</center>



- ## Here is the demo

  ![:\AndroidWorkspace\weather-application\display\assignment002-part1.gi](E:\AndroidWorkspace\weather-application\display\assignment002-part1.gif)



						### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;------Part 1

- ## Get weather forcast from weather-forecast API

  First, I use an API to get  the weather forecasts in the form of a JSON file.

  ```
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
  ```

  Then, I create a class to save the result. The Forecast include the temperature and weather type of a day. The method "getTemperature()" is to get the temperature in this day by caculating the average of the highest and lowest temperature in the day. And the method "getType()" is to get weather type of the day by using regular expression to analysis the String mType and return the information.

  ```
  public class Forecast {
      private String mlowest_Temperature;
      private String mhighest_Temperature;
      private String mType;
      
      public Forecast(String mlowest_Temperature, String mhighest_Temperature, String mType) {
          this.mlowest_Temperature = mlowest_Temperature;
          this.mhighest_Temperature = mhighest_Temperature;
          this.mType = mType;
      }

      public int getTemperature(){
          String lt = null,
                  ht = null;
          int temprerature;
          Matcher matcher = Pattern.compile("\\d+.\\d+").matcher(mlowest_Temperature);
          while(matcher.find())
              lt = matcher.group().trim();

          matcher = Pattern.compile("\\d+.\\d+").matcher(mhighest_Temperature);
          while(matcher.find())
              ht = matcher.group().trim();
          temprerature = (int)(Float.valueOf(lt)+Float.valueOf(ht))/2;
          return temprerature;
      }

      public int getType(){
          Pattern pattern = Pattern.compile("[雨]|[雷]");
          Matcher matcher = pattern.matcher(mType);
          if (matcher.find())
              return RAINY;
          pattern = Pattern.compile("[风]|[云]");
          matcher = pattern.matcher(mType);
          if (matcher.find())
              return WINDY;
          pattern = Pattern.compile("[阴]");
          matcher = pattern.matcher(mType);
          if (matcher.find())
              return CLOUDY;
          return SUNNY;
      }
  }
  ```

  ​

  ​





