package io.github.tubb.prefetch.test;

/**
 * Created by tubingbing on 18/3/11.
 */

public class UserInfo {
    public String userId;
    public String name;

    @Override
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
