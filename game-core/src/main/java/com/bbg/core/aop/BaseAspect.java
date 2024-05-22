package com.bbg.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class BaseAspect {

    public Object parserSpEL(String key, ProceedingJoinPoint point) {
        if(key.trim().isEmpty()){
            return "";
        }
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Expression expression = spelExpressionParser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = point.getArgs();
        for (int i = 0; i < argValues.length; i++) {
            context.setVariable(argNames[i], argValues[i]);
        }
        return expression.getValue(context);
    }

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

}
