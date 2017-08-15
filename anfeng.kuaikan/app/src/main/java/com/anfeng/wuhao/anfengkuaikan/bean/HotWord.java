package com.anfeng.wuhao.anfengkuaikan.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class HotWord {

    /**
     * code : 200
     * data : {"guide_text":"","hot_word":[{"hot_word_source":"运营","action_type":2,"target_title":"朝花惜时","target_id":616},{"hot_word_source":"运营","action_type":2,"target_title":"你曾经爱我","target_id":882},{"hot_word_source":"运营","action_type":2,"target_title":"蓝翅","target_id":1164},{"hot_word_source":"运营","action_type":2,"target_title":"甜美的咬痕","target_id":906},{"hot_word_source":"运营","action_type":2,"target_title":"河神大人求收养","target_id":359},{"hot_word_source":"运营","action_type":2,"target_title":"国民老公带回家：偷吻55次","target_id":1131},{"hot_word_source":"运营","action_type":2,"target_title":"偷星九月天","target_id":1182},{"hot_word_source":"运营","action_type":2,"target_title":"绯闻女一号","target_id":876},{"hot_word_source":"运营","action_type":2,"target_title":"纯情丫头火辣辣","target_id":1076},{"hot_word_source":"运营","action_type":2,"target_title":"哑舍","target_id":1227}],"since":10}
     * message : ok
     */

    private int code;
    private DataBean data;
    private String message;

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
        /**
         * guide_text :
         * hot_word : [{"hot_word_source":"运营","action_type":2,"target_title":"朝花惜时","target_id":616},{"hot_word_source":"运营","action_type":2,"target_title":"你曾经爱我","target_id":882},{"hot_word_source":"运营","action_type":2,"target_title":"蓝翅","target_id":1164},{"hot_word_source":"运营","action_type":2,"target_title":"甜美的咬痕","target_id":906},{"hot_word_source":"运营","action_type":2,"target_title":"河神大人求收养","target_id":359},{"hot_word_source":"运营","action_type":2,"target_title":"国民老公带回家：偷吻55次","target_id":1131},{"hot_word_source":"运营","action_type":2,"target_title":"偷星九月天","target_id":1182},{"hot_word_source":"运营","action_type":2,"target_title":"绯闻女一号","target_id":876},{"hot_word_source":"运营","action_type":2,"target_title":"纯情丫头火辣辣","target_id":1076},{"hot_word_source":"运营","action_type":2,"target_title":"哑舍","target_id":1227}]
         * since : 10
         */

        private String guide_text;
        private int since;
        private List<HotWordBean> hot_word;

        public String getGuide_text() {
            return guide_text;
        }

        public void setGuide_text(String guide_text) {
            this.guide_text = guide_text;
        }

        public int getSince() {
            return since;
        }

        public void setSince(int since) {
            this.since = since;
        }

        public List<HotWordBean> getHot_word() {
            return hot_word;
        }

        public void setHot_word(List<HotWordBean> hot_word) {
            this.hot_word = hot_word;
        }

        public static class HotWordBean {
            /**
             * hot_word_source : 运营
             * action_type : 2
             * target_title : 朝花惜时
             * target_id : 616
             */

            private String hot_word_source;
            private int action_type;
            private String target_title;
            private int target_id;

            public String getHot_word_source() {
                return hot_word_source;
            }

            public void setHot_word_source(String hot_word_source) {
                this.hot_word_source = hot_word_source;
            }

            public int getAction_type() {
                return action_type;
            }

            public void setAction_type(int action_type) {
                this.action_type = action_type;
            }

            public String getTarget_title() {
                return target_title;
            }

            public void setTarget_title(String target_title) {
                this.target_title = target_title;
            }

            public int getTarget_id() {
                return target_id;
            }

            public void setTarget_id(int target_id) {
                this.target_id = target_id;
            }
        }
    }
}
