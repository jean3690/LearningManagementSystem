package org.lms.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.constant.AutoFillConstant;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("@annotation(org.lms.annotation.AutoFill) && execution(* org.lms.mapper.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void autoFill(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OpreationType[] types = autoFill.value();
        Object[] args = joinPoint.getArgs();

        // 参数校验
        if (args == null || args.length == 0) {
            log.warn("No arguments in method: {}", signature.getName());
            return;
        }

        Object entity = args[0];
        Instant now = Instant.now();

        try {
            switch (types.length) {
                case 1:
                    fillField(entity, types[0], now);
                    break;
                case 2:
                    if (types[0] == types[1]) {
                        throw new IllegalArgumentException("AutoFill 注解中不允许重复的 OpreationType: " + types[0]);
                    }
                    fillField(entity, types[0], now);
                    fillField(entity, types[1], now);
                    break;
                default:
                    throw new IllegalArgumentException("AutoFill 注解只支持 1 或 2 个 OpreationType，当前数量: " + types.length);
            }
        } catch (Exception e) {
            log.error("自动填充时间字段失败，方法: {}, 实体: {}", signature.getMethod().getName(), entity.getClass().getSimpleName(), e);
            throw new RuntimeException("自动填充失败: " + e.getMessage(), e);
        }
    }

    /**
     * 填充指定的时间字段
     */
    private void fillField(Object entity, OpreationType type, Instant now) throws Exception {
        String methodName;
        if (type == OpreationType.CREATEDAT) {
            methodName = AutoFillConstant.SET_CREATEDAT;
        } else if (type == OpreationType.UPDATEDAT) {
            methodName = AutoFillConstant.SET_UPDATEDAT;
        } else {
            throw new IllegalArgumentException("不支持的 OpreationType: " + type);
        }

        Method method = entity.getClass().getDeclaredMethod(methodName, Instant.class);
        method.setAccessible(true); // 确保私有方法可访问
        method.invoke(entity, now);
    }
}