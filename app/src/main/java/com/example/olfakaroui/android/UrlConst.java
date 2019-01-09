package com.example.olfakaroui.android;

public class UrlConst {
    public static String SERVER = "http://192.168.43.172:3000/";
    public static String IMAGES = SERVER + "image/";

    public static String checkUser = SERVER + "user";
    public static String editRole = SERVER + "role";


    /////////////////////////// events //////////////////////////////

    public static String EVENTS_BY_CAUSE = SERVER + "events/bycause/";
    public static String ADD_EVENT = SERVER + "events/add";
    public static String ADD_POST = SERVER + "posts/add";
    public static String ADD_EVENT_LOCATION = SERVER + "eventmodif/";
    public static String EVENTS_BY_TYPE = SERVER + "events/bytype/";
    public static String ALL_EVENTS = SERVER + "allevents";
    public static String DONATION_TAYPES = SERVER + "donationtypes";
    public static String ADD_DONATION_EVENT = SERVER + "addTypes";
    public static String ADD_PIC = SERVER + "eventphotos/";


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
    public static String ADD_CHARITY_LOCATION = SERVER + "userloc/";

    /////////////////////////// collabs //////////////////////////////
    public static String ADD_COLLAB = SERVER + "collabs";
    public static String TOTAL_COLLAB = SERVER + "collab/total/";
    public static String PENDING_COLLABS = SERVER+ "collabs/bycharitypending/";
    public static String UPDATE_COLLAB = SERVER+ "collab/update/";
    public static String HIDE_COMMENT = SERVER+ "comment/hide/";
    public static String EVENT_COLLABS = SERVER + "collabs/byevent/";
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
    public static String trendingPosts = SERVER+ "poststrending/";
    public static String posts = SERVER+ "posts/";
    public static String userModif = SERVER + "usermodif/";
    public static String confirmationPhoto = SERVER + "logomodif/";
    public static String UPLOAD_FOLDER_URL = SERVER+"/uploads";
}
