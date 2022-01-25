package com.meta.redisson.lock.aop;

import com.meta.redisson.lock.aop.annotation.LockKey;
import com.meta.redisson.lock.aop.annotation.UseRLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Xiong Mao
 * @date 2021/12/23 10:58
 **/
@Aspect
@Component
public class UseRLockAspect {
    private final static String REDIS_NS = "rlock:";
    private final static String DEFAULT = "default";
    private final Logger log = LoggerFactory.getLogger(UseRLockAspect.class);

    @Autowired
    private RedissonClient redissonClient;


    @AfterThrowing(value = "@annotation(com.meta.redisson.lock.aop.annotation.UseRLock)", throwing = "e")
    public void AfterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            unlock(joinPoint);
        } else {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    try {
                        unlock(joinPoint);
                    } catch (Exception e) {
                        log.error("unlock: {}", e);
                    }
                }
            });
        }
    }

    @Around(value = "@annotation(com.meta.redisson.lock.aop.annotation.UseRLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        lock(joinPoint);
        return joinPoint.proceed();
    }


    @AfterReturning(value = "@annotation(com.meta.redisson.lock.aop.annotation.UseRLock)")
    public void afterReturning(JoinPoint joinPoint) throws NoSuchMethodException {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            unlock(joinPoint);
        } else {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    try {
                        unlock(joinPoint);
                    } catch (Exception e) {
                        log.error("unlock: {}", e);
                    }
                }
            });
        }
    }

    private void unlock(JoinPoint joinPoint) throws NoSuchMethodException {
        UseRLock useRLock = getUseRLock(joinPoint);
        String lockKey = getLockKey(useRLock.area(), useRLock.clazz(), useRLock.clazz(), joinPoint);

        RLock lock = redissonClient.getLock(lockKey);

        if (!Objects.isNull(lock)) {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    private void lock(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        UseRLock useRLock = getUseRLock(joinPoint);

        long maxLockTime = useRLock.maxLockTime();
        String area = useRLock.area();
        String clazz = useRLock.clazz();
        String methodName = useRLock.method();
        UseRLock.LockPolicy policy = useRLock.policy();

        String lockKey = getLockKey(area, clazz, methodName, joinPoint);
        RLock lock = redissonClient.getLock(lockKey);

        boolean locked = lock.isLocked();
        if (UseRLock.LockPolicy.REJECT.equals(policy) && locked) {
            throw new RuntimeException("此业务调用仅允许一个并发处理");
        }

        lock.lock(maxLockTime, TimeUnit.MILLISECONDS);
    }

    private String getLockKey(String area, String clazz, String methodName, JoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature mSignature = (MethodSignature) signature;
        Class<?> declaringType = mSignature.getDeclaringType();
        Method method = declaringType.getDeclaredMethod(mSignature.getName(), mSignature.getParameterTypes());

        if (DEFAULT.equals(clazz)) {
            clazz = declaringType.getName();
        }
        if (DEFAULT.equals(methodName)) {
            methodName = method.getName();
        }

        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        StringBuilder lockKey = new StringBuilder();
        lockKey.append(REDIS_NS);
        lockKey.append(area);
        lockKey.append(":");
        lockKey.append(clazz);
        lockKey.append(":");
        lockKey.append(methodName);
        lockKey.append(":");

        String customValue = "";

        boolean hasLockKey = false;
        for (int i = 0; i < args.length; i++) {
            Parameter parameter = parameters[i];
            LockKey annotation = parameter.getAnnotation(LockKey.class);
            if (annotation != null) {
                Object arg = args[i];
                String argStr = arg.toString();
                if (StringUtils.isEmpty(argStr)) {
                    throw new RuntimeException("@LockKey parameter must not be null");
                }
                hasLockKey = true;
                customValue = argStr;
            }
        }
        if (!hasLockKey) {
            //TODO 获取当前用户token
//            customValue = "";
            throw new RuntimeException("@LockKey must not assigned");
        }

        return lockKey.append(customValue).toString();
    }

    private UseRLock getUseRLock(JoinPoint pjp) throws NoSuchMethodException {
        Signature signature = pjp.getSignature();
        MethodSignature mSignature = (MethodSignature) signature;
        Class<?> declaringType = mSignature.getDeclaringType();
        Method method = declaringType.getDeclaredMethod(mSignature.getName(), mSignature.getParameterTypes());
        return method.getAnnotation(UseRLock.class);
    }

}
