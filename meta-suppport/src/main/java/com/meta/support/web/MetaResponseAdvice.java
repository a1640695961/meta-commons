package com.meta.support.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.meta.commons.vo.ResultVo;
import com.meta.support.annotation.DisableResponseAdvice;
import com.meta.support.exception.ExceptionHandlerResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author xiongmao
 */
@RestControllerAdvice
public class MetaResponseAdvice implements ResponseBodyAdvice<Object> {

    private final static String SPRING_BOOT = "org.springframework.boot";
    private final static String SWAGGER_DOCUMENTATION = "springfox.documentation";

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // String类型的返回不处理
//        if (StringHttpMessageConverter.class.isAssignableFrom(converterType)) {
//            return false;
//        }ø
        // 不重写spring boot接口的返回结果
        if (returnType.getParameterType().getName().startsWith(SPRING_BOOT)) {
            return false;
        }
        // 不重写Swagger接口的返回结果
        if (returnType.getMethod().getDeclaringClass().getName().startsWith(SWAGGER_DOCUMENTATION)) {
            return false;
        }
        Method method = returnType.getMethod();
        DisableResponseAdvice disableResponseAdvice = AnnotationUtils.findAnnotation(method, DisableResponseAdvice.class);
        if (disableResponseAdvice != null) {
            return false;
        }
        Class<?> aClass = returnType.getDeclaringClass();
        DisableResponseAdvice annotation = AnnotationUtils.findAnnotation(aClass, DisableResponseAdvice.class);
        if (annotation != null) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.isNull(body)) {
            return ResultVo.success(null);
        }
        if (body instanceof ExceptionHandlerResolver.ExceptionError) {
            return body;
        }

        if (String.class.isAssignableFrom(returnType.getParameterType()) || this.reponseEntityGeneric(returnType)) {
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            try {
                return objectMapper.writeValueAsString(ResultVo.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return ResultVo.success(body);
    }

    public boolean reponseEntityGeneric(MethodParameter returnType) {
        if (ResponseEntity.class.isAssignableFrom(returnType.getParameterType())) {
            //获取泛型
            Type type = returnType.getGenericParameterType();
            if (type instanceof ParameterizedType) {
                //获取泛型Class
                Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
                if (actualTypeArgument instanceof Class) {
                    Class clazz = (Class) actualTypeArgument;
                    if (String.class.isAssignableFrom(clazz)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
