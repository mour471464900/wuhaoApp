package com.anfeng.wuhao.anfengkuaikan.bean;

import java.util.List;

/**
 * banner轮播图的实体类
 */
public class BannerBean extends Entity  {


    /**
     * code : 200
     * data : {"banner_group":[{"pic":"http://f2.kkmh.com/image/161007/9bkpwd8rv.webp-w640","title":"","type":3,"value":"16796"},{"pic":"http://f2.kkmh.com/image/161007/gihcg13qj.webp-w640","title":"","type":2,"value":"717"},{"pic":"http://f2.kkmh.com/image/161005/vmgipwisq.webp-w640","title":"","type":3,"value":"17027"}]}
     * message : OK
     */

    private int code;
    private DataBean data;
    private String message;

    @Override
    public String toString() {
        return "BannerBean{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<BannerGroupBean> banner_group;

        public List<BannerGroupBean> getBanner_group() {
            return banner_group;
        }

        public void setBanner_group(List<BannerGroupBean> banner_group) {
            this.banner_group = banner_group;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "banner_group=" + banner_group +
                    '}';
        }

        public static class BannerGroupBean {
            /**
             * pic : http://f2.kkmh.com/image/161007/9bkpwd8rv.webp-w640
             * title :
             * type : 3
             * value : 16796
             */

            private String pic;
            private String title;
            private int type;
            private String value;

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "BannerGroupBean{" +
                        "pic='" + pic + '\'' +
                        ", title='" + title + '\'' +
                        ", type=" + type +
                        ", value='" + value + '\'' +
                        '}';
            }
        }
    }
}
