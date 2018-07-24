package cn.redick01.prog.core;

import cn.redick01.prog.annotation.Dlock;
import cn.redick01.prog.annotation.DlockKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
public class BusinessKeyHandler {

    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private ExpressionParser parser = new SpelExpressionParser();

    /**
     * 获取用户定义业务key
     * @param joinPoint
     * @param dlock
     * @return
     */
    public String getKeyName(ProceedingJoinPoint joinPoint, Dlock dlock) {

        List<String> keyList = new ArrayList<>();
        Method method = getMethod(joinPoint);
        List<String> definitionKeys = getSpelDefinitionKey(dlock.keys(), method, joinPoint.getArgs());
        keyList.addAll(definitionKeys);
        List<String> parameterKeys = getParameterKey(method.getParameters(), joinPoint.getArgs());
        keyList.addAll(parameterKeys);
        return StringUtils.collectionToDelimitedString(keyList, "", "-", "");
    }

    /**
     * 根据切面获取方法
     * @param joinPoint
     * @return
     */
    public Method getMethod(ProceedingJoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    /**
     * 表达式key
     * @param keys
     * @param method
     * @param parameterValues
     * @return
     */
    public List<String> getSpelDefinitionKey(String [] keys, Method method, Object [] parameterValues) {
        List<String> definitionKeyList = new ArrayList<>();
        for (String definitionKey : keys) {
            if (null != definitionKey && !definitionKey.isEmpty()) {
                EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, parameterNameDiscoverer);
                String key = parser.parseExpression(definitionKey).getValue(context).toString();
                definitionKeyList.add(key);
            }
        }
        return definitionKeyList;
    }

    /**
     * 获取参数key
     * @param parameters
     * @param parameterValues
     * @return
     */
    public List<String> getParameterKey(Parameter [] parameters, Object [] parameterValues) {
        List<String> parameterKey = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getAnnotation(DlockKey.class) != null) {
                DlockKey dlockKey = parameters[i].getAnnotation(DlockKey.class);
                if (dlockKey.value().isEmpty()) {
                    parameterKey.add(parameters[i].toString());
                } else {
                    StandardEvaluationContext context = new StandardEvaluationContext(parameterValues[i]);
                    String key = parser.parseExpression(dlockKey.value()).getValue(context).toString();
                    parameterKey.add(key);
                }
            }
        }
        return parameterKey;
    }
}
