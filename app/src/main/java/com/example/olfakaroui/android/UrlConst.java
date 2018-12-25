package com.example.olfakaroui.android;

public class UrlConst {
    public static String SERVER = "http://10.0.2.2:3000/";
    public static String IMAGES = SERVER + "image/";

    public static String checkUser = SERVER + "user";
    public static String editRole = SERVER + "role";


    /////////////////////////// events //////////////////////////////

    public static String EVENTS_BY_CAUSE = SERVER + "events/bycause/";


    /////////////////////////// users //////////////////////////////

    public static String USER_PREFRENCES = SERVER + "prefs/byuser/";
    public static String ADD_PREFS = SERVER + "prefs";
    public static String USER_INFOS = SERVER + "users/infos/";
    public static String FIND_A_USER= SERVER + "users/findauser/";
    public static String USER_Collabs= SERVER + "users/collabs/";
    public static String FOLLOW = SERVER + "users/follow";
    public static String UNFOLLOW = SERVER + "users/unfollow/";

    /////////////////////////// votes //////////////////////////////
    public static String addVote = SERVER + "votes";
    public static String updateVote = SERVER + "vote";

    public static String allCauses = SERVER + "causes";
    ;
    public static String trendingPosts = SERVER+ "poststrending/";
    public static String posts = SERVER+ "posts/";
    public static String userModif = SERVER + "usermodif/";
    public static String confirmationPhoto = SERVER + "logomodif/";
    public static String UPLOAD_FOLDER_URL = SERVER+"/uploads";
}
