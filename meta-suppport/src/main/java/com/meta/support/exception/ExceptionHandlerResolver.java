package com.meta.support.exception;

import com.meta.commons.exception.MetaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Xiong Mao
 * @date 2022/04/26 01:50
 **/
@RestControllerAdvice
public class ExceptionHandlerResolver {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerResolver.class);

    public static class ExceptionError {
        public static final String SYS_ERROR_CODE = "-1";
        private String code;
        private String message;
        private String Ii8nKey;
        private Object[] args;

        public ExceptionError() {
            super();
        }

        public ExceptionError(String code, String message, String ii8nKey) {
            super();
            this.code = code;
            this.message = message;
            Ii8nKey = ii8nKey;
        }

        public ExceptionError(String code, String message, String ii8nKey, Object[] args) {
            super();
            this.code = code;
            this.message = message;
            this.Ii8nKey = ii8nKey;
            this.args = args;
        }

        public ExceptionError(String code, String message) {
            super();
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIi8nKey() {
            return Ii8nKey;
        }

        public void setIi8nKey(String ii8nKey) {
            Ii8nKey = ii8nKey;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }

    @ExceptionHandler(value = MetaException.class)
    public ResponseEntity<ExceptionError> catchIhr360Exception(MetaException e) {
        LOGGER.error(">>>>>>meta exception={}", e);
        Object[] args = e.getArgs();
        String message = e.getMessage();
        if (args != null && args.length > 0) {
            message = String.format(message, args);
        }
        ExceptionError errorInfo = new ExceptionError(e.getCode(), message, e.getMessageKey(), args);
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionError> catchSysException(Exception e) {
        LOGGER.error(">>>>>>system exception={}", e);
        ExceptionError errorInfo = new ExceptionError(ExceptionError.SYS_ERROR_CODE, "服务异常, 请联系管理员!");
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }
}
