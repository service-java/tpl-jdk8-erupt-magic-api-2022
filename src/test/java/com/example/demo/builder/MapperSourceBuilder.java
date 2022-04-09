package com.example.demo.builder;

import java.io.Writer;
import java.util.Arrays;
import org.beetl.core.Template;
import org.beetl.sql.gen.BaseProject;
import org.beetl.sql.gen.Entity;
import org.beetl.sql.gen.SourceConfig;
import org.beetl.sql.gen.simple.BaseTemplateSourceBuilder;

public class MapperSourceBuilder extends BaseTemplateSourceBuilder {
    public static String mapperPath = "mapper.btl";
    String suffix;

    public MapperSourceBuilder() {
        super("mapper");
        this.suffix = "Mapper";
    }

    public MapperSourceBuilder(String suffix) {
        super("mapper");
        this.suffix = suffix;
    }

    public void generate(BaseProject project, SourceConfig config, Entity entity) {
        Template template = groupTemplate.getTemplate(mapperPath);
        String mapperClass = entity.getName() + this.suffix;
        template.binding("className", mapperClass);
        template.binding("tableName", entity.getTableName());
        template.binding("package", project.getBasePackage(this.name));
        template.binding("entityClass", entity.getName());
        String entityPkg = project.getBasePackage("entity");
        String mapperHead = entityPkg + ".*";
        template.binding("imports", Arrays.asList(mapperHead));
        Writer writer = project.getWriterByName(this.name, entity.getName() + this.suffix + ".java");
        template.renderTo(writer);
    }
}
