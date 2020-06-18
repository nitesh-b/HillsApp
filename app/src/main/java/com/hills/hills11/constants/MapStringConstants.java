package com.hills.hills11.constants;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;

public class MapStringConstants {
    public static String[] mapStateSelectionOptions = {"NSW", "VIC", "QLD", "WA", "SA", "TAS", "ACT", "NT"};
    public static String[] nsw = {"Alexandria Branch", "Silverwater - AV repairs", "Seven Hills Branch"};
    public static String[] vic = {"Coburg Branch"};
    public static String[] qld = {"Nerang Branch"};
    public static String[] wa = {"Balcatta Branch"};
    public static String[] nt = {"canberra Branch"};

    public static String[] getCity(String city){
        if( city.toLowerCase ( ).equals ( "nsw" ) ) {
            return nsw;
        }
        else if ( city.toLowerCase ( ).equals ( "vic" ) ){
            return vic;
        }else if ( city.toLowerCase ( ).equals ( "qld" ) ){
            return qld;
        }else if ( city.toLowerCase ( ).equals ( "wa" ) ){
            return wa;
        }else{
            return nt;
        }
    }
    public static class Alexandria{
        public static final LatLng alexandriaLatLng = new LatLng ( -33.9177997 , 151.1899165 );
        public static String name = "Alexandria Branch";
        public static String address = "Building 5, 85 O'Roirdan Street, ALEXANDRIA, NSW 2015, Australia";
        public static String phone = "02 9311 8700";
        public static Map<String, String> openingHours = new HashMap<> ();
            public Alexandria() {
                openingHours.put ( "Monday" , "07:30 - 17:00" );
                openingHours.put ( "Tuesday" , "07:30 - 17:00" );
                openingHours.put ( "Wednesday" , "07:30 - 17:00" );
                openingHours.put ( "Thrusday" , "07:30 - 17:00" );
                openingHours.put ( "Friday" , "07:30 - 17:00" );
                openingHours.put ( "Saturday" , "Closed" );
                openingHours.put ( "Sunday" , "Closed" );

            }


    }
    public static class Sevenhills{
        public static final LatLng sevenhills = new LatLng ( -33.7682097 , 150.9486464 );
       public static String name = "Seven Hills Branch";
        public static String address = "18/24 Abbott Road, via Costello Place, SEVEN HILLS, NSW 2147, Australia";
        public static String phone = "02 9749 0994";
        public static Map<String, String> openingHours = new HashMap<> ();
        public Sevenhills() {
            openingHours.put ( "Monday" , "07:30 - 17:00" );
            openingHours.put ( "Tuesday" , "07:30 - 17:00" );
            openingHours.put ( "Wednesday" , "07:30 - 17:00" );
            openingHours.put ( "Thrusday" , "07:30 - 17:00" );
            openingHours.put ( "Friday" , "07:30 - 17:00" );
            openingHours.put ( "Saturday" , "Closed" );
            openingHours.put ( "Sunday" , "Closed :)" );
        }

    }

    public static class Silverwater{



    }
    public static class Coburg{



    }
    public static class Nerang{



    }
    public static class Balcatta{



    }
    public static class Canberra{



    }
}
