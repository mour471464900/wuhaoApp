package com.anfeng.wuhao.anfengkuaikan.utils;

/**
 * Created by hao on 2016/7/13.
 */
public class URLCommon {

    public static final String DAY_START = "http://api.kuaikanmanhua.com/v1/daily/comic_lists/";

    public static final String DAY_END = "?since=0";

    public static final String URL_COMMENT_HOT="http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=&since=&page_num=1&catalog_type=2";
    public static final String url_comment_RECENTLY_start="http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=&since=&page_num=" ;
    public static final String url_comment_RECENTLY_end="&catalog_type=1";


    //这是评论热门链接
    public static final String getUrl_comment_hot_refresh="http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=&since=29&page_num=4&catalog_type=2";
//这是下拉刷新链接

    // http://f1.kkmh.com/("images")-c.w750.jpg
    //这是评论链接json数据中图片的链接,用images对象数组里的大小写字母将上文链接中的("images")替换即可
    public static  final String COMMENT_START="http://f1.kkmh.com/";
    public static  final String COMMENT_END="-c.w750.jpg";
    public static final String URL_COMMENT_RECENTLY="http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=&since=&page_num=1&catalog_type=1"  ;

//这是评论最新链接


//    http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=&since=&page_num=1&catalog_type=1




    //热门跳转后的链接
 //   http://api.kuaikanmanhua.com/v1/comments/feed/16386219085176832/order/time?offset=0&limit=20
public static final String COMMENT_START_HOT_CONTEXT ="http://api.kuaikanmanhua.com/v1/comments/feed/";
    public static final String COMMENT_END_HOT_CONTEXT="/order/time?offset=0&limit=20";



  // "http://api.kuaikanmanhua.com/v1/users/6638304";
  public static final String comment_icon_datum ="http://api.kuaikanmanhua.com/v1/users/";
//这是点击图像后跳转进来的链接
//    http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=13278865&since=13718771437449216&page_num=2&catalog_type=3
public static final String comment_icon_dynamic_start="http://api.kuaikanmanhua.com/v1/feeds/feed_lists?uid=";
  // public static final String comment_icon_dynamic_second="&since=";
    public static final String comment_icon_dynamic_end="&since=&page_num=2&catalog_type=3";
//这首点击图像后跳转进来的动态链接


 // public static final String works="http://api.kuaikanmanhua.com/v1/topics/799?sort=0"  ;
 public static final String works_start="http://api.kuaikanmanhua.com/v1/topics/";
    public static final String works_end="?sort=0";


    //搜索
    // http://api.kuaikanmanhua.com/v1/topics/search?keyword=神&offset=0&limit=20
public static final String SEARCH_START="http://api.kuaikanmanhua.com/v1/topics/search?keyword=";
    public static final String SEARCH_END="&offset=0&limit=20";

    //http://api.kuaikanmanhua.com/v1/comments/feed/18480773846302720/order/time?offset=0&limit=20
    public static final String ZUIXIN_CONTENT_START="http://api.kuaikanmanhua.com/v1/comments/feed/";
    public static final String ZUIXIN_CONTENT_END="/order/time?offset=0&limit=20";


    //http://api.kuaikanmanhua.com/v1/comments/feed/18633178751741952/order/score?offset=0&limit=20
    public static final String ZUIXIN_CONTENT_START_1="http://api.kuaikanmanhua.com/v1/comments/feed/";
    public static final String ZUIXIN_CONTENT_END_1="/order/score?offset=0&limit=20";




    //广告
    public static final String URL_BANNER = "http://api.kuaikanmanhua.com/v1/banners";

    //热门除了广告外的
    public static final String URL_OTHER = "http://api.kuaikanmanhua.com/v1/topic_lists/mixed/new";

    //热门界面条目箭头的点击
    public static final String URL_PRO_ARRAY_START="http://api.kuaikanmanhua.com/v1/topic_lists/";
    public static final String URL_PRO_ARRAY_END="?offset=0";
    //分类
    public static final String URL_CLASSIFY = "http://api.kuaikanmanhua.com/v1/tag/suggestion";
    //分类的点击
    public static final String URL_CLASSIFYINFO = "http://api.kuaikanmanhua.com/v1/topics?offset=0&limit=20&tag=";


//  http://api.kuaikanmanhua.com/v1/comics/14133/hot_comments
//     这是看漫画的页面的  下面的评论

//    http://api.kuaikanmanhua.com/v1/comics/14133
//    这是  看漫画的主要页面

    public static final String URL_FULL_WATCH = "http://api.kuaikanmanhua.com/v1/comics/";
    //       这个是跳转到看漫画的内容的主要  呈现图片的 界面
    public static final String URL_FULL_COMMENT_START = "http://api.kuaikanmanhua.com/v1/comics/";
    //    这个是热门评论的界面
    public static final String URL_FULL_COMMENT_END = "/hot_comments";


    //    最新评论
    public static final String URL_ZUI_RE_START = "http://api.kuaikanmanhua.com/v1/comics/";
    public static final String URL_ZUI_RE_END = "/comments/0?order=";
//  http://api.kuaikanmanhua.com/v1/comics/14213/comments/0?order=

    //    最热评论
    public static final String URL_ZUI_XING_START = "http://api.kuaikanmanhua.com/v1/comics/";
    public static final String URL_ZUI_XING_END = "/comments/0?order=score";
    //    http://api.kuaikanmanhua.com/v1/comics/14213/comments/0?order=score
//    作品的详情
//    http://api.kuaikanmanhua.com/v1/topics/363?sort=0
    public static final String URL_OPUS_START="http://api.kuaikanmanhua.com/v1/topics/";
    public static final String URL_OPUS_END="?sort=0";


//   获取日期列表的集合地址
    public static String getDayUrl(long id){
        return  DAY_START+id+DAY_END;
    }

    /**
     * 获取搜索热词
     * @param since
     * @return
     */
    public static String getNewHotWord(int since){
      return   "http://api.kuaikanmanhua.com/v2/topic/search/new_hot_word?since="+since+"&count=10";
    }

}
