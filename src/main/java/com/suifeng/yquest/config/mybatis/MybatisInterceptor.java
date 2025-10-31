package com.suifeng.yquest.config.mybatis;
import com.suifeng.yquest.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        //获取当前登录用户的id
        String loginId = LoginUtil.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            return invocation.proceed();
        }
        if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE) {
            replaceEntityProperty(parameter, loginId, sqlCommandType);
        }

        return invocation.proceed();
    }

    private void replaceEntityProperty(Object parameter, String loginId, SqlCommandType sqlCommandType) {
        if (parameter instanceof Map) {
            replaceMap((Map) parameter, loginId, sqlCommandType);
        } else {
            replace(parameter, loginId, sqlCommandType);
        }
    }

    private void replaceMap(Map parameter, String loginId, SqlCommandType sqlCommandType) {
        for (Object val : parameter.values()) {
            replace(val, loginId, sqlCommandType);
        }
    }

    private void replace(Object parameter, String loginId, SqlCommandType sqlCommandType) {
        if (sqlCommandType == SqlCommandType.INSERT) {
            dealInsert(parameter, loginId);
        } else {
            dealUpdate(parameter, loginId);
        }
    }

    private void dealUpdate(Object parameter, String loginId) {
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
                if ("updatedBy".equals(field.getName())) {
                    field.set(parameter, loginId);
                    field.setAccessible(false);
                } else if ("updatedTime".equals(field.getName())) {
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

    private void dealInsert(Object parameter, String loginId) {
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
                if ("isDeleted".equals(field.getName())) {
                    field.set(parameter, 0);
                    field.setAccessible(false);
                } else if ("createdBy".equals(field.getName())) {
                    field.set(parameter, loginId);
                    field.setAccessible(false);
                } else if ("createdTime".equals(field.getName())) {
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
        if (object != null) {

            Class<?> clazz = object.getClass();
            List<Field> fieldList = new ArrayList<>();
            while (clazz != null) {
                fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }
            Field[] fields = new Field[fieldList.size()];
            fieldList.toArray(fields);
            return fields;
            // 继续后续处理
        } else {
            // 处理空对象的情况
            log.warn("尝试处理空对象的字段");
            return new Field[0];
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
