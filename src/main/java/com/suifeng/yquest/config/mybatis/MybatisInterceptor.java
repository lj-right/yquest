package com.suifeng.yquest.config.mybatis;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 填充createBy，createTime等公共字段的拦截器
 */
@Component
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class
})})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (parameter == null) {
            return invocation.proceed();
        }
        if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE) {
            replaceEntityProperty(parameter, sqlCommandType);
        }

        return invocation.proceed();
    }

    private void replaceEntityProperty(Object parameter, SqlCommandType sqlCommandType) {
        if (parameter instanceof Map) {
            replaceMap((Map) parameter, sqlCommandType);
        } else {
            replace(parameter, sqlCommandType);
        }
    }

    private void replaceMap(Map parameter, SqlCommandType sqlCommandType) {
        for (Object val : parameter.values()) {
            replace(val, sqlCommandType);
        }
    }

    private void replace(Object parameter, SqlCommandType sqlCommandType) {
        if (sqlCommandType == SqlCommandType.INSERT) {
            dealInsert(parameter);
        } else {
            dealUpdate(parameter);
        }
    }

    private void dealUpdate(Object parameter) {
        Field[] fields = getAllFields(parameter);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(parameter);
                if (Objects.nonNull(o)) {
                    field.setAccessible(false);
                    continue;
                }
                //填充数据
                if ("updatetime".equals(field.getName())) {
                    field.set(parameter, new Date());
                    field.setAccessible(false);
                } else {
                    field.setAccessible(false);
                }
            } catch (Exception e) {
                log.error("dealUpdate.error:{}", e.getMessage(), e);
            }
        }
    }

    private void dealInsert(Object parameter) {
        Field[] fields = getAllFields(parameter);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(parameter);
                if (Objects.nonNull(o)) {
                    field.setAccessible(false);
                    continue;
                }
                //填充数据
                if ("createtime".equals(field.getName())) {
                    field.set(parameter, new Date());
                    field.setAccessible(false);
                } else if ("updatetime".equals(field.getName())) {
                    field.set(parameter, new Date());
                    field.setAccessible(false);
                } else {
                    field.setAccessible(false);
                }
            } catch (Exception e) {
                log.error("dealInsert.error:{}", e.getMessage(), e);
            }
        }
    }

    private Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
