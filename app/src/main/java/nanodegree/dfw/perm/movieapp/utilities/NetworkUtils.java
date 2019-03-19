package nanodegree.dfw.perm.movieapp.utilities;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.net.Uri;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String DYNAMIC_MOVIE_URL =
//            "https://andfun-weather.udacity.com/weather";
            "https://api.themoviedb.org/3/movie";

    private final static String PARAM_KEY = "api_key";
    private final static String QUERY_PARAM = "number of movies";

    private final static String KEY_VALUE =
            "fcb4ae381c4482341fc74a85ea0b071a";


//    https://api.themoviedb.org/3/movie/550?api_key=fcb4ae381c4482341fc74a85ea0b071a       // correct Sample
//    https://api.themoviedb.org/3/movie/?api_key=fcb4ae381c4482341fc74a85ea0b071a&cnt=14   // 1st attempt api-built per below implementation 3/15

    private static final String MOVIE_BASE_URL = DYNAMIC_MOVIE_URL;

    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API.If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this course.
     */

    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 14;


    final static String DAYS_PARAM = "cnt";

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param numOfMovies The total-Movies-numbre that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String numOfMovies) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendEncodedPath(numOfMovies)
                .appendQueryParameter(PARAM_KEY, KEY_VALUE)
                .build();
//                                                               https://api.themoviedb.org/3/movie/550?api_key=fcb4ae381c4482341fc74a85ea0b071a
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildUrlPoster(String numOfMovies) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendEncodedPath(numOfMovies)
                .appendQueryParameter(PARAM_KEY, KEY_VALUE)
                .build();
//                                                               https://api.themoviedb.org/3/movie/550?api_key=fcb4ae381c4482341fc74a85ea0b071a
        URL urlPoster = null;
        try {
            urlPoster = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + urlPoster);

        return urlPoster;
    }



    /**
     * Builds the URL used to talk to the weather server using latitude and longitude of a
     * location.
     *
     * @param lat The latitude of the location
     * @param lon The longitude of the location
     * @return The Url to use to query the weather server.
     */
    public static URL buildUrl(Double lat, Double lon) {
        /** This will be implemented in a future lesson **/
        return null;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}