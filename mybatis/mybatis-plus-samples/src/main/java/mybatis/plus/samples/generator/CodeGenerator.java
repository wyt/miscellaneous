package mybatis.plus.samples.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中 */
public class CodeGenerator {

  /** 读取控制台内容 */
  public static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in);
    StringBuilder help = new StringBuilder();
    help.append("请输入" + tip + "：");
    System.out.println(help.toString());
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (ipt != null && !"".equals(ipt)) return ipt;
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }

  public static void main(String[] args) {

    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();

    // 全局配置
    GlobalConfig globalCfg = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    globalCfg.setOutputDir(projectPath + "/mybatis/mybatis-plus-samples/src/main/java");
    globalCfg.setAuthor("AutoGenerator");
    globalCfg.setOpen(false);

    mpg.setGlobalConfig(globalCfg);

    // 数据源配置
    DataSourceConfig dataSource = new DataSourceConfig();
    dataSource.setUrl(
        "jdbc:mysql://localhost:3306/miscellaneous?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
    dataSource.setDriverName("com.mysql.jdbc.Driver");
    dataSource.setUsername("root");
    dataSource.setPassword("123456");
    mpg.setDataSource(dataSource);

    // 包配置
    PackageConfig packageCfg = new PackageConfig();
    packageCfg.setModuleName(scanner("模块名"));
    packageCfg.setParent("mybatis.plus.samples");
    mpg.setPackageInfo(packageCfg);

    // 自定义配置
    InjectionConfig cfg =
        new InjectionConfig() {
          @Override
          public void initMap() {
            // to do nothing
          }
        };

    List<FileOutConfig> focList = new ArrayList<>();
    focList.add(
        new FileOutConfig("/templates/mapper.xml.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            // 自定义输入文件名称
            return projectPath
                + "/mybatis/mybatis-plus-samples/src/main/resources/mapper/"
                + packageCfg.getModuleName()
                + "/"
                + tableInfo.getEntityName()
                + "Mapper"
                + StringPool.DOT_XML;
          }
        });
    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);
    mpg.setTemplate(new TemplateConfig().setXml(null));

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true);
    strategy.setInclude(scanner("表名"));
    strategy.setSuperEntityColumns("id");
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setTablePrefix(packageCfg.getModuleName() + "_");
    mpg.setStrategy(strategy);
    // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }
}
