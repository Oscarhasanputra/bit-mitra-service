package com.bit.microservices.mitra.filter;


import java.util.Objects;

public class UserContextData {

    private final static InheritableThreadLocal<UserData> threadLocal = new InheritableThreadLocal<>();

    public static UserData getUserData(){
        UserData temp =threadLocal.get();

        if(Objects.isNull(temp)){
            return new UserData();
        }
        return temp;

    }
    public static void setUserData(UserData userData){
        threadLocal.set(userData);
    }

    public static void removeUserData(){
        threadLocal.remove();
    }
}
