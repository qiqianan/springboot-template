package com.west2.common;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 1
 * 代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) {

        // 1.获取代码生成器的对象
        AutoGenerator autoGenerator = new AutoGenerator();

        // 设置数据库相关配置
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/securityStudy_db?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        autoGenerator.setDataSource(dataSource);

        // 设置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        // 设置代码生成位置
        globalConfig.setOutputDir("F:\\JavaTotal\\SpringSecurity-study\\src\\main\\java");
        // 设置生成完毕后是否打开生成代码所在的目录
        globalConfig.setOpen(false);
        // 设置作者
        globalConfig.setAuthor("翁鹏");
        // 设置是否覆盖原始生成的文件
        globalConfig.setFileOverride(false);
        // 设置数据层接口名，%s为占位符，指代模块名称
        globalConfig.setMapperName("%sDao");
        autoGenerator.setGlobalConfig(globalConfig);

        // 设置包名相关配置
        PackageConfig packageInfo = new PackageConfig();
        // 设置生成的包名，与代码所在位置不冲突，二者叠加组成完整路径
        packageInfo.setParent("com.wp");
        // 设置实体类包名
        packageInfo.setEntity("domain");
        // 设置数据层包名
        packageInfo.setMapper("dao");
        autoGenerator.setPackageInfo(packageInfo);

        // 策略设置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 设置当前参与生成的表名，参数为可变参数
        strategyConfig.setInclude("t_role_resource");
        // 设置数据库表名映射到实体类的命名策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 设置数据库表的前缀名称，模块名 = 数据库表名 - 前缀名  例如： User = tbl_user - tbl_
        strategyConfig.setTablePrefix("t_");
        // 设置是否启用Rest风格
        strategyConfig.setRestControllerStyle(true);
        // 设置是否启用lombok
        strategyConfig.setEntityLombokModel(true);
        // 设置自动填充字段
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(createTime);
        tableFillList.add(updateTime);
        strategyConfig.setTableFillList(tableFillList);
        autoGenerator.setStrategy(strategyConfig);
        //2.执行生成操作
        autoGenerator.execute();
    }
}
