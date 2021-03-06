/*
 * Copyright (C) 2018 HAWKORE S.L. (http://hawkore.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hawkore.ignite.examples.dataproviders;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.apache.ignite.lang.IgniteBiTuple;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

import com.hawkore.ignite.examples.entities.tweets.Tweet;

/**
 * TweetsProvider
 * 
 * <p>
 * 
 * This supplier will creates randomly geo located tweets for testing purpose
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public class TweetsProvider implements Supplier<Tweet> {

    private static final GeometryFactory DEFAULT_GEO_FACTORY = new GeometryFactory(new PrecisionModel(), 4326); //EPSG: 4326 - lat long
    
    private final int tweetsNumber;

    private int processed;

    private int initKey;
    
    private long init;
    
    private final String countryCode;

    private final Random random;

    private final Calendar calendar;

    private static List<String> countryCodes = Arrays.asList("ES", "FR", "PT");

    private static final int LOG_EVERY = 10000;

    private static List<IgniteBiTuple<Double, Double>> capitals = Arrays.asList(
        // SPAIN - MADRID (longitude, latitude) = -3.703790, 40.416775
        new IgniteBiTuple<Double, Double>(-3.703790, 40.416775),
        // FRANCE - PARIS (longitude, latitude) = 2.349014 48.864716
        new IgniteBiTuple<Double, Double>(2.349014, 48.864716),
        // PORTUGAL - LISBON (longitude, latitude) -9.142685, 38.736946
        new IgniteBiTuple<Double, Double>(-9.142685, 38.736946));

    /**
     * 
     * @param tweetsNumber
     * @param countryCode
     * @param initKey
     */
    public TweetsProvider(int tweetsNumber, String countryCode, int initKey) {
        this.tweetsNumber = tweetsNumber;
        this.processed = 0;
        this.init = System.currentTimeMillis();
        this.random = new Random();
        this.calendar = Calendar.getInstance();
        this.calendar.set(2018, 11, 1);
        this.countryCode= countryCode;
        this.initKey = initKey;
    }

    @Override
    public synchronized Tweet get() {

        if (processed < tweetsNumber) {

            if (processed != 0 && processed % LOG_EVERY == 0) {
                System.out.format("Injected %d tweets in %d ms\n", LOG_EVERY,
                    (System.currentTimeMillis() - init));
                init = System.currentTimeMillis();
            }

            IgniteBiTuple<Double, Double> capitalPosition = capitals.get(countryCodes.indexOf(this.countryCode));
            //generate random location within 500Km radius from capital location
            IgniteBiTuple<Double, Double> ramdomTweetPosition = genRandomLocation(random, capitalPosition.get1(),
                capitalPosition.get2(), 500000);
            Tweet t = new Tweet(initKey, this.countryCode, DEFAULT_GEO_FACTORY.createPoint(new Coordinate(ramdomTweetPosition.get1(), ramdomTweetPosition.get2())));
            t.setBody("big data gives organizations " + initKey);
            this.calendar.add(Calendar.SECOND, initKey);
            t.setTime(new Date(this.calendar.getTimeInMillis()));
            t.setUser("user_" + initKey);
            
            initKey++;
            processed++;
            
            return t;
        }
        return null;

    }

    private static IgniteBiTuple<Double, Double> genRandomLocation(Random random, double longitude, double latitude,
        int radius) {

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double newX = x / Math.cos(Math.toRadians(latitude));

        double foundLongitude = newX + longitude;
        double foundLatitude = y + latitude;

        return new IgniteBiTuple<>(foundLongitude, foundLatitude);
    }
}
