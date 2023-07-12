# Weather app

Design a Android app to show the current wheather by city.
The app should have two screens: "Home" and "Weather Details".
The "Home" screen should have a search field and a list of suitable cities.
The following information should be displayed on the "Weather Details" screen: city, temperature, wind speed, wind direction.

Additionally:
1. Remember city
2. Search history

Use the following links and APIs:
1. https://developer.android.com/studio/install - install Android studio.
2. https://open-meteo.com/en/docs/geocoding-api - search city.
3. https://open-meteo.com/en/docs - get weaher forecast.

## Remarks
1. Need to add error handling to the UI. It is also necessary to add loading spinner wherever necessary.
2. Show hourly weather forecast relative to the current time (not from midnight).
3. Use retrofit instead of manual json paring.

## Libraries
Use the latest versions of languages and libraries: gradle 8.1.1, kotlin 1.8.2, retrofit 2.9.0, Java 17.
