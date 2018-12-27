package com.example.olfakaroui.android;

public class UrlConst {
    public static String SERVER = "http://10.0.2.2:3000/";
    public static String IMAGES = SERVER + "image/";

    public static String checkUser = SERVER + "user";
    public static String editRole = SERVER + "role";


    /////////////////////////// events //////////////////////////////

    public static String EVENTS_BY_CAUSE = SERVER + "events/bycause/";
    public static String ALL_EVENTS = SERVER + "allevents";


    /////////////////////////// users //////////////////////////////

    public static String USER_PREFRENCES = SERVER + "prefs/byuser/";
    public static String ADD_PREFS = SERVER + "prefs";
    public static String USER_INFOS = SERVER + "users/infos/";
    public static String FIND_A_USER= SERVER + "users/findauser/";
    public static String USER_Collabs= SERVER + "users/collabs/";
    public static String FOLLOW = SERVER + "users/follow";
    public static String UNFOLLOW = SERVER + "users/unfollow/";
    public static String FAV = SERVER + "users/fav";
    public static String UNFAV = SERVER + "users/unfav/";
    public static String CHARITY_EVENTS = SERVER + "events/byuser/";
    public static String ALL_CHARITIES= SERVER + "users/charities";


    /////////////////////////// votes //////////////////////////////
    public static String addVote = SERVER + "votecomment";
    public static String updateVote = SERVER + "vote";
    public static String addVoteEvent = SERVER + "voteevent";
    public static String addVotePost = SERVER + "votepost";

    /////////////////////////// comments //////////////////////////////
    public static String COMMENT_EVENT = SERVER + "commentevent";
    public static String COMMENT_POST = SERVER + "commentpost";
    public static String DELETE_COMMENT = SERVER + "comment/";




    public static String allCauses = SERVER + "causes";
    ;
    public static String trendingPosts = SERVER+ "poststrending/";
    public static String posts = SERVER+ "posts/";
    public static String userModif = SERVER + "usermodif/";
    public static String confirmationPhoto = SERVER + "logomodif/";
    public static String UPLOAD_FOLDER_URL = SERVER+"/uploads";
}
