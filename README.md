# Weather App ☀️🌧️

A weather app that tells you if you need an umbrella, sunscreen, or just to stay inside and binge-watch your favorite show.

## Features

- Search for any city, town, or place on Earth (probably even Atlantis)
- Get real-time weather and hourly forecasts
- Location autocomplete so you don't have to spell "Thiruvananthapuram"
- Hamburger menu with your recent searches (because we all forget what we just typed)
- Remove any location from recents if you regret your choices

## Setup

1. Clone this repo (you know the drill)
2. Get your own API keys for OpenWeatherMap and LocationIQ
3. Add them to `local.properties` like this:
   ```
   OPENWEATHER_API_KEY=your_openweather_key_here
   LOCATIONIQ_API_KEY=your_locationiq_key_here
   ```
4. Build & run. If it doesn't work, blame the weather.

## Tech

- Jetpack Compose 
- Retrofit + Moshi 
