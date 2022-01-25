# [基于Redisson实现](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95)

## 1. 版本

### 1.0.0-SNAPSHOT

#### 1.支持分布式锁（详细配置，与spring.redis一致）

#### 2.支持aop注解方式进行使用（可重入锁）

##### 注解

```
UseRLock 
        area 服务分组,默认default
        maxLockTime 最大锁定时间, 单位ms 默认5000
        policy: 可选值: UseRLock.LockPolicy.REJECT (默认),UseRLock.LockPolicy.WAIT
        UseRLock.LockPolicy.REJECT 当相同的lockkey存在时 直接拒绝请求返回信息"此业务调用仅允许一个并发处理".
        UseRLock.LockPolicy.WAIT 当相同的lockkey存在时 会等待前面的任务结束. 不建议采用此模式
        
LockKey   
        lock key的生成规则:
        rlock:<area>:<package+class>:<method>:<lockkey>
```

##### aop

```
    UseRLockAspect
    
    /**
    * @param area 服务分组,默认default
    */
    @Override
    @UseRLock(area = "test", maxLockTime = 10000)
    public String testLock(@LockKey String key) {
        ...
        return ...;
    }
    
    /**
    *   没有使用时@LockKey默认使用token作为@Lockkey （暂不支持）
    */
    @Override
    @UseRLock
    public String testLock() {
        ...
        return ...;
    }

```

##### 自主使用
```
    @Autowired
    private RedissonClient redissonClient;
```