package com.xinfan.blueblue.exception;


public final class ExceptionType {
	private String name;
	public ExceptionType(String name){
		this.name = name;
	}
    
    public static final ExceptionType UNSUPPORT = new ExceptionType("UNSUPPORT");
    public static final  ExceptionType FILE_ERROR = new ExceptionType("FILE_ERROR");
    public static final  ExceptionType NET_UN_AVAILABLE = new ExceptionType("NET_UN_AVAILABLE");
    public static final  ExceptionType LOCATE_FAILDED = new ExceptionType("LOCATE_FAILDED");
    public static final  ExceptionType NET_FAILED = new ExceptionType("NET_FAILED");
    public static final  ExceptionType SERVER_ERROR = new ExceptionType("SERVER_ERROR");
    public static final  ExceptionType PARSE_ERROR = new ExceptionType("PARSE_ERROR");
    public static final  ExceptionType SD_ERROR_UNAVAILABLE = new ExceptionType("SD_ERROR_UNAVAILABLE");
    public static final  ExceptionType SD_ERROR_NOTENOUGH = new ExceptionType("SD_ERROR_NOTENOUGH");
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}
