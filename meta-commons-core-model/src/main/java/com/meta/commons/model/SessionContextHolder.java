package com.meta.commons.model;

import java.util.Objects;

/**
 * @author Xiong Mao
 * @date 2022/01/13 17:53
 **/
public class SessionContextHolder {

    private static ThreadLocal<SessionUser> context = new InheritableThreadLocal<>();

    public static SessionUser getSessionUser() {
        return context.get();
    }

    public static void setSessionUser(SessionUser sessionUser) {
        context.set(sessionUser);
    }

    public static void resetSessionUser() {
        context.remove();
    }

    public static String getUserId() {
        SessionUser sessionUser = getSessionUser();
        if (Objects.isNull(sessionUser)) {
            return null;
        }
        return sessionUser.getUserId();
    }

    public static String getUserName() {
        SessionUser sessionUser = getSessionUser();
        if (Objects.isNull(sessionUser)) {
            return null;
        }
        return sessionUser.getUserName();
    }


    public static class SessionUser {
        public static final String USER_ID = "userId";
        public static final String USER_NAME = "userName";
        /**
         * 当前登录用户ID
         */
        private String userId;
        /**
         * 员工名
         */
        private String userName;

        public SessionUser() {
            super();
        }

        public SessionUser(String userId, String userName) {
            super();
            this.userId = userId;
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "SessionUser [userId=" + userId + ", userName=" + userName + "]";
        }

    }
}
