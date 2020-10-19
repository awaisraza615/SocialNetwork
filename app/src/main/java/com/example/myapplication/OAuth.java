package com.example.myapplication;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class OAuth {

    public TwitterFactory tf;

    public Twitter twitter;

    public void main() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("9alVPGLKVIp0sEtoa1b3tZEnn")
                .setOAuthConsumerSecret("0y8YVdeU09laY402BPJJNqXeWxOmdosJoZn5BPDZgH1Yft2SWP")
                .setOAuthAccessToken("1199152818038308864-egUurjskATwRwHzf5CcppBuAtQGjPQ")
                .setOAuthAccessTokenSecret("JiTLQX3RcVEsZ4JBYeorKYJfZQNta5B0vRvq5EGoShUcA");

        tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public TwitterFactory getTwitterFactory() {
        main();
        return tf;
    }

    public Twitter getTwitter() {
        main();
        return twitter;
    }
}
