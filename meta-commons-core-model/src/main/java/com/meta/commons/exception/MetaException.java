package com.meta.commons.exception;

/**
 * 异常类
 */
public class MetaException extends RuntimeException {

    private String message;

    private String code;

    private String messageKey;

    private Object[] args;

    public MetaException(String message) {
        this.message = message;
    }

    public MetaException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
        this.message = message;
    }
    public MetaException(String messageKey, String message, Throwable throwable) {
        super(message,throwable);
        this.messageKey = messageKey;
        this.message = message;
    }

    public MetaException(String messageKey, String message, Object[] args) {
        super(message);
        this.messageKey = messageKey;
        this.message = message;
        this.args = args;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
