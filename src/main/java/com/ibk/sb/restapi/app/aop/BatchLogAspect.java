package com.ibk.sb.restapi.app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BatchLogAspect {

//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final Environment env;
//    private final LoggingService loggingService;
//
//    public BatchLogAspect(Environment env, LoggingService loggingService) {
//        this.env = env;
//        this.loggingService = loggingService;
//    }
//
//    @Around("@annotation(com.ibk.sb.restapi.app.annotation.JobSchedulerTarget)")
//    public Object logAspect(ProceedingJoinPoint joinPoint) {
//
//        Object processResult = new Boolean(false);
//        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
//        String enabledProperty = method.getAnnotation(JobSchedulerTarget.class).enabled();
//        boolean enabledValue = Boolean.valueOf(env.resolveRequiredPlaceholders(enabledProperty));
//
//        if(enabledValue) {
//
//            String schedulerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
//            String methodName = method.getName();
//            BatchStatus status = BatchStatus.PROCESSING;
//
//            try {
//
//                logger.info("[BATCH {}.{}] This batch job is started.", schedulerName, methodName);
//
//                // 결과 로그 update (DB 이력 남기기 - 시작시점)
//                loggingService.startBatch(schedulerName, methodName, status);
//
//                // 시작점 로그 insert
//                /**
//                boolean result = (boolean) joinPoint.proceed();
//                status = result ? BatchResult.SUCCESS : BatchResult.FAILURE;
//                 */
//                processResult = joinPoint.proceed();
//                if(processResult != null)
//                    status = (boolean) processResult ? BatchStatus.SUCCESS : BatchStatus.FAILURE;
//
//                logger.info("[BATCH {}.{}] This batch job is completed. => {}", schedulerName, methodName, status);
//
//            } catch (Throwable e) {
//
//                logger.error("[BATCH {}.{}] A error is occurred. => {}", schedulerName, methodName, e.getMessage());
//                status = BatchStatus.ERROR;
//
//            } finally {
//
//                logger.info("[BATCH {}.{}] This batch job is ended. => {}", schedulerName, methodName, status);
//
//                // 결과 로그 update (DB 이력 남기기 - 종료시점)
//                loggingService.endBatch(schedulerName, methodName, status);
//
//            }
//        }
//        return processResult;
//    }

}
