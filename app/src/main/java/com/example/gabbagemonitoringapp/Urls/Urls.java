package com.example.gabbagemonitoringapp.Urls;

public class Urls {
    public String https = "http://172.16.0.76/garbage_collection/";


    public String LOGIN_URL = https + "login.php";///
    public String ADMINLOGIN_URL = https + "admin_login.php";///g
    public String FIRSTREG_URL = https + "register.php";///g
    public String USERDETS_URL = https + "read_user.php";///g
    public String EDITACCOUNT = https + "edit_profile.php";// half

    /*Admin urls*/
    public String LOAD_BINS = https + "load_bins.php";///g
    public String UPLOAD_BIN = https + "register_bins.php";///g
    public String EDIT_BIN = https + "edit_bins.php";/// issues
    public String LOAD_PICKUP_ORDERS = https + "load_orders.php";///g
    public String LOAD_USERS = https + "load_registered_users.php";///g comes with the bin and extra information + the user dets
    public String DELETE_BIN = https + "delete_bin.php";///g
    public String LOAD_REWARD_REQUESTS = https + "load_reward_requests.php";///g
    public String LOAD_ALL_REWARD_REQUESTS = https + "all_reward_requests.php";///g
    public String DENY_REWARD_REQUEST = https + "deny_reward_request.php";///g
    public String CONFIRM_REWARD_REQUEST = https + "confirm_reward_request.php";///g
    public String UPDATE_WORK_ORDER = https + "update_work_order.php";///g
    public String COUNTBINS = https + "count_bins.php";/// g
    public String COUNTUSERS = https + "count_users.php";///g
    public String COUNTPICKUPS = https + "count_pickups.php";///g
    public String CHECKFIRST_REWARD = https + "check_reward.php";
    public String COUNT_ORDERS = https + "count_requests.php";//g

    public String LOAD_TODAY_REPORT = https + "count_report_today.php";//g
    public String LOAD_WEEKLY_REPORT = https + "count_report_weekly.php";//g
    public String LOAD_MONTHLY_REPORT = https + "count_report_monthly.php";//g

    public String LOAD_DAYSMONTHLY_REPORT = https + "count_days_monthly.php";//g
    public String LOAD_DAYWEEKLY_REPORT = https + "count_days_weekly.php";//g


    public String DAYTODAY_REPORT = https + "count_today_orders.php";//
    public String DAYWEEK_REPORT = https + "count__week_orders.php";//
    public String DAYMONTH_REPORT = https + "count_monthly_orders.php";//



    public String SEE_MY_ORDERS = https + "users_orders.php"; ////g
    public String UPLOAD_ORDER = https + "upload_order.php"; ///g
    public String HELPMESSAGE_URL = https + "help_message.php"; // half
    public String CLAIM_REWARD = https + "claim_reward.php"; //g








    public String CONFIRM_REQUEST = https + "confirm_requests.php";
    public String DENY_REQUEST = https + "deny_requests.php";
    public String READER_NOTIFICATION = https + "reader_notifications.php";


    /*private chats*/
    public String MESSAGES_LIST = https + "message_list.php";///
    public String SEND_MESSAGE = https + "send_message.php";///

    public String GET_CHAT_USERS = https + "read_message_users.php";///use status to see if they have un read shit
    public String SEND_SINGLE_CHAT = https + "send_private_message.php";
    public String GET_POST_USERS = https + "read_post_users.php";
    public String GET_SINGLE_MESSAGES = https + "message_private_list.php";

    /*posts*/
    public String SEND_SINGLE_POST = https + "send_private_post.php";
    public String SEND_GROUP_POST = https + "send_group_post.php";
    public String GET_GROUP_POST = https + "message_posts.php";
    public String GET_SINGLE_POST = https + "message_private_posts.php";
    //later
    public String SEND_COMMENT_POST = https + "send_post_comments.php";
    public String GET_COMMENT_POST = https + "get_post_comments.php";
    public String UPDATE_LIKES_POST = https + "update_likes.php";
    public String UPDATE_DISLIKES_POST = https + "update_dislikes.php";

    //total new messages and posts
    public String GET_NO_NEW_MESSAGES = https + "get_no_new_messages.php";//
    public String GET_NO_NEW_POSTS = https + "get_no_new_posts.php";//
    public String GET_NO_COMMENTS = https + "get_no_comments.php";//
    public String GET_LAST_MESSAGES = https + "get_last_messages.php";//


    /*updating seen messages and posts*/
    public String USEENPRIATE_POST = https + "useen_private_posts.php";//
    public String USEENPRIATE_CHAT = https + "useen_private_chat.php";//

}
