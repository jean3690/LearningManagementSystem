package org.lms.Context;

import org.lms.dto.UsersDto;

public class UserContextHolder {
    private static final ThreadLocal<UsersDto> THREAD_LOCAL = new ThreadLocal<>();
    public static void set(UsersDto usersDto){
        THREAD_LOCAL.set(usersDto);
    }
    public static UsersDto get(){
        return THREAD_LOCAL.get();
    }
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
