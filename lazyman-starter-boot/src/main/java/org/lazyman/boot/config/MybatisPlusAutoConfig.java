package org.lazyman.boot.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.lazyman.common.constant.CommonConstant;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.util.ThreadLocalUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(TenantProperties.class)
@MapperScan(basePackages = CommonConstant.MYBATIS_MAPPER_PKG)
public class MybatisPlusAutoConfig {
    @Resource
    private TenantProperties tenantProperties;

    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        if (!tenantProperties.getEnable()) {
            return paginationInterceptor;
        }
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 创建租户SQL解析器
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        // 设置租户处理器
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean select) {
                Long tenantId = ThreadLocalUtils.getCurrentTenantId();
                if (ObjectUtil.isNotNull(tenantId)) {
                    return new LongValue(tenantId);
                }
                return new NullValue();
            }

            @Override
            public String getTenantIdColumn() {
                return tenantProperties.getColumn();
            }

            @Override
            public boolean doTableFilter(String tableName) {
                if (StrUtil.isBlank(tenantProperties.getIgnoreTables())) {
                    return false;
                }
                List<String> ignoreTableList = Arrays.asList(tenantProperties.getIgnoreTables().split(StringPool.COMMA));
                if (ignoreTableList.contains(tableName)) {
                    return true;
                }
                return false;
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }
}
