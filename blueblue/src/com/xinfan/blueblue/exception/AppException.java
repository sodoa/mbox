package com.xinfan.blueblue.exception;

import java.util.HashMap;
import java.util.Map;

import com.xinfan.blueblue.util.LogUtil;

public class AppException extends Exception {
    private static final Map<ExceptionType, Integer> EXCEPTION_STRING = new HashMap<ExceptionType, Integer>();
    private static final long serialVersionUID = -0x13e8862f7badd1b3L;
    private ExceptionType type;
    
    static {
        EXCEPTION_STRING.put(ExceptionType.FILE_ERROR, Integer.valueOf(0x7f0b0032));
        EXCEPTION_STRING.put(ExceptionType.NET_FAILED, Integer.valueOf(0x7f0b002f));
        EXCEPTION_STRING.put(ExceptionType.NET_UN_AVAILABLE, Integer.valueOf(0x7f0b0030));
        EXCEPTION_STRING.put(ExceptionType.LOCATE_FAILDED, Integer.valueOf(0x7f0b0031));
        EXCEPTION_STRING.put(ExceptionType.SD_ERROR_NOTENOUGH, Integer.valueOf(0x7f0b0033));
    }
    
    public AppException(ExceptionType type, Throwable throwable) {
        super(type.getName(), throwable);
        LogUtil.e("AppException", type.getName(), throwable);
        this.type = type;
    }
    
    public AppException(ExceptionType type) {
        super(type.getName());
        this.type = type;
    }
    
    public ExceptionType getExType() {
        return type;
    }
    
    public boolean hasStringResourceId() {
        return EXCEPTION_STRING.containsKey(type);
    }
    
    public int getStringResourceId() {
        return (Integer)EXCEPTION_STRING.get(type).intValue();
    }
}
