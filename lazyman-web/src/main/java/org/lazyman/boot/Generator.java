package org.lazyman.boot;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.lazyman.common.constant.StringPool;

import java.util.*;

public class Generator {
    /**
     * 根据模板生成代码
     *
     * @param tableName            表名
     * @param moduleName           模块名
     * @param simpleMode           是否简单输出模式
     * @param isTenantMode         是否自实现多租户模式
     * @param isOutputSelectOption 是否输出下拉列表接口
     * @param isOutputStateAction  是否输出启用/禁用接口
     * @param isOutputTreeAction   是否输出树结构接口
     */
    public void generate(String tableName, String moduleName, boolean simpleMode, boolean isTenantMode,
                         boolean isOutputSelectOption, boolean isOutputStateAction, boolean isOutputTreeAction) {
        AutoGenerator autoGenerator = new AutoGenerator();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("generator-config");
        //全局配置
        String projectPath = System.getProperty("user.dir");
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOpen(false);
        globalConfig.setOutputDir(projectPath + resourceBundle.getString("outputDir"));
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(false);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(false);
        globalConfig.setSwagger2(true);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setAuthor(resourceBundle.getString("author"));
        autoGenerator.setGlobalConfig(globalConfig);
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
        dataSourceConfig.setDriverName(resourceBundle.getString("jdbc.driver"));
        dataSourceConfig.setUsername(resourceBundle.getString("jdbc.user"));
        dataSourceConfig.setPassword(resourceBundle.getString("jdbc.password"));
        dataSourceConfig.setUrl(resourceBundle.getString("jdbc.url"));
        autoGenerator.setDataSource(dataSourceConfig);
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix(new String[]{"t_"});
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setInclude(new String[]{tableName});
        strategyConfig.setSuperEntityClass(resourceBundle.getString(isTenantMode ? "superTenantEntityClass" : "superEntityClass"));
        strategyConfig.setSuperServiceClass(resourceBundle.getString("superServiceClass"));
        strategyConfig.setSuperServiceImplClass(resourceBundle.getString("superServiceImplClass"));
        strategyConfig.setSuperControllerClass(resourceBundle.getString("superControllerClass"));
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setControllerMappingHyphenStyle(true);
        autoGenerator.setStrategy(strategyConfig);
        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(resourceBundle.getString("parentPackage"));
        packageConfig.setModuleName(moduleName);
        autoGenerator.setPackageInfo(packageConfig);
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("parent", packageConfig.getParent());
                map.put("dto", packageConfig.getParent() + StringPool.DOT + "dto");
                map.put("vo", packageConfig.getParent() + StringPool.DOT + "vo");
                map.put("isOutputSelectOption", isOutputSelectOption);
                map.put("isOutputStateAction", isOutputStateAction);
                map.put("isOutputTreeAction", isOutputTreeAction);
                this.setMap(map);
            }
        };
        String rootPackagePath = resourceBundle.getString("parentPackagePath");
        List<FileOutConfig> focList = new ArrayList<>();
        if (!simpleMode) {
            focList.add(new FileOutConfig("/templates/custom/dto.query.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return globalConfig.getOutputDir() + rootPackagePath + StringPool.SLASH + (StrUtil.isEmpty(packageConfig.getModuleName()) ? "" : moduleName)
                            + "/dto/" + tableInfo.getEntityName() + "QueryDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/custom/dto.form.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return globalConfig.getOutputDir() + rootPackagePath + StringPool.SLASH + (StrUtil.isEmpty(packageConfig.getModuleName()) ? "" : moduleName)
                            + "/dto/" + tableInfo.getEntityName() + "FormDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/custom/vo.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return globalConfig.getOutputDir() + rootPackagePath + StringPool.SLASH + (StrUtil.isEmpty(packageConfig.getModuleName()) ? "" : moduleName)
                            + "/vo/" + tableInfo.getEntityName() + "VO.java";
                }
            });
            if (isOutputTreeAction) {
                focList.add(new FileOutConfig("/templates/custom/tree.vo.java.vm") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return globalConfig.getOutputDir() + rootPackagePath + StringPool.SLASH + (StrUtil.isEmpty(packageConfig.getModuleName()) ? "" : moduleName)
                                + "/vo/" + tableInfo.getEntityName() + "TreeVO.java";
                    }
                });
            }
        }
        if (simpleMode) {
            focList.add(new FileOutConfig("/templates/custom/simple.service.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return globalConfig.getOutputDir() + rootPackagePath + StringPool.SLASH + (StrUtil.isEmpty(packageConfig.getModuleName()) ? "" : moduleName)
                            + "/service/I" + tableInfo.getEntityName() + "Service.java";
                }
            });
            focList.add(new FileOutConfig("/templates/custom/simple.serviceImpl.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return globalConfig.getOutputDir() + rootPackagePath + StringPool.SLASH + (StrUtil.isEmpty(packageConfig.getModuleName()) ? "" : moduleName)
                            + "/service/impl/" + tableInfo.getEntityName() + "ServiceImpl.java";
                }
            });
        }
        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("/templates/custom/entity.java.vm");
        templateConfig.setXml("/templates/custom/mapper.xml.vm");
        templateConfig.setMapper("/templates/custom/mapper.java.vm");
        templateConfig.setService(simpleMode ? null : "/templates/custom/service.java.vm");
        templateConfig.setServiceImpl(simpleMode ? null : "/templates/custom/serviceImpl.java.vm");
        templateConfig.setController(simpleMode ? null : "/templates/custom/controller.java.vm");
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.execute();
    }

    public static void main(String[] args) {
        String tableName = "t_app_user";
        String moduleName = "sys";
        boolean isTenantMode = false;
        boolean isSimpleMode = false;
        boolean isOutputSelectOption = true;
        boolean isOutputStateAction = true;
        boolean isOutputTreeAction = false;
        Generator generator = new Generator();
        generator.generate(tableName, moduleName, isSimpleMode, isTenantMode, isOutputSelectOption, isOutputStateAction, isOutputTreeAction);
    }
}
