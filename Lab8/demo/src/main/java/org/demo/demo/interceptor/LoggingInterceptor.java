package org.demo.demo.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import org.demo.demo.annotation.Loggable;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Loggable
@Interceptor
@Priority(1000)
public class LoggingInterceptor implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    private final SecurityContext securityContext;

    @Inject
    public LoggingInterceptor(@SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object logMethod(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();

        Object[] parameters = context.getParameters();
        Object resourceId = parameters.length > 0 ? parameters[0] : "unknown";

        String logInfo = "User " + securityContext.getCallerPrincipal().getName() + " performed operation " + methodName + " on resource " + resourceId;
        LOGGER.log(Level.INFO, logInfo);

        return context.proceed();
    }
}
