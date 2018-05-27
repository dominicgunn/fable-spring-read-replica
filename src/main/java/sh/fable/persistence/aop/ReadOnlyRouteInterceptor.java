package sh.fable.persistence.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.fable.persistence.routing.RoutingDataSource;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dominic Gunn
 */
@Aspect
@Component
@Order(0)
public class ReadOnlyRouteInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ReadOnlyRouteInterceptor.class);

	@Around("@annotation(transactional)")
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Transactional transactional) throws Throwable {
		try {
			if (transactional.readOnly()) {
				RoutingDataSource.setReplicaRoute();
				logger.info("Routing database call to the read replica");
			}
			return proceedingJoinPoint.proceed();
		} finally {
			RoutingDataSource.clearReplicaRoute();
		}
	}
}
