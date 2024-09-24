package com.yc;

//两个参数， 用于  请求的地址:   https://api.github.com/repos/OpenFeign/feign/contributors 返回值的封装
//
public class Contributor
{
        String login;
        int contributions;
        int id;
        String node_id;
        String avatar_url;
        String gravatar_id;
        String url;
        String html_url;
        String followers_url;
//        String following_url;
//        String gists_url;
//        String starred_url;
//        String subscriptions_url;
//        String organizations_url;
//        String repos_url;
//        String events_url;
//        String received_events_url;
//        String type;
 
 
        @Override
        public String toString() {
                return "Contributor{" +
                        "login='" + login + '\'' +
                        ", contributions=" + contributions +
                        ", id=" + id +
                        ", node_id='" + node_id + '\'' +
                        ", avatar_url='" + avatar_url + '\'' +
                        ", gravatar_id='" + gravatar_id + '\'' +
                        ", url='" + url + '\'' +
                        ", html_url='" + html_url + '\'' +
                        ", followers_url='" + followers_url + '\'' +
                        '}';
        }
}