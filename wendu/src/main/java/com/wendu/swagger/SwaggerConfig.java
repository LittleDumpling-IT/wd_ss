package com.wendu.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author LittleDumpling
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(SwaggerConfig.controllerPackages)
public class SwaggerConfig implements BeanFactoryPostProcessor {

    static final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

    //扫描哪些包的controller
    static final String controllerPackages = "com.wendu.*";

    static final boolean IS_ENABLED_SWAGGER = true;

    static final String SWAGGER_AUTH_KEY = "Swagger-Auth-AdminController";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            if ("springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander".equals(beanDefinition.getBeanClassName())) {
                //去掉原来的ModelAttributeParameterExpander，会导致嵌套死循环
                listableBeanFactory.removeBeanDefinition(name);
            }

            //为每个controller添加一个分组
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                if (annotationMetadata.hasAnnotation(Controller.class.getName()) && isFromControllerPackages(annotationMetadata.getClassName())) {
                    DocketFactory docketFactory = new DocketFactory(annotationMetadata.getClassName());
                    try {
                        listableBeanFactory.registerSingleton(DocketFactory.class.getName() + "." + docketFactory.groupName(), docketFactory.getObject());
                    } catch (Exception e) {
                        LOG.error("注册DocketFactory失败", e);
                    }
                }
            }
        }
    }

    private boolean isFromControllerPackages(String className) {
        for (String pk : controllerPackages.split(",")) {
            if (className.startsWith(pk)) {
                return true;
            }
        }
        return false;
    }

    static class DocketFactory implements FactoryBean<Docket> {

        private String groupName;

        private String controllerClassName;

        public DocketFactory(String controllerClassName) {
            Objects.requireNonNull(controllerClassName, "controllerClassName can not be null");
            this.groupName = controllerClassName.substring(controllerClassName.lastIndexOf(".") + 1);
            this.controllerClassName = controllerClassName;
        }

        public String groupName() {
            return this.groupName;
        }

        @Override
        public Docket getObject() throws Exception {
            return new Docket(DocumentationType.SWAGGER_2)
                    .enable(IS_ENABLED_SWAGGER)
                    .groupName(groupName)
                    .forCodeGeneration(true).select()
                    .apis(controller(controllerClassName))
                    .paths(PathSelectors.any())
                    .build()
                    .globalOperationParameters(setHeaderToken())
                    .apiInfo(apiInfo());
        }

        /**设置全局变量**/
        private List<Parameter> setHeaderToken() {
            ParameterBuilder tokenPar = new ParameterBuilder();
            List<Parameter> pars = new ArrayList<>();
            tokenPar.name(SWAGGER_AUTH_KEY)
                    .description("当前测试用户账号")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false)
                    .build();
            pars.add(tokenPar.build());
            return pars;
        }

        private Predicate<RequestHandler> controller(String controllerClassName) {
            return new Predicate<RequestHandler>() {
                @Override
                public boolean apply(RequestHandler input) {
                    return input.declaringClass().getName().startsWith(controllerClassName);
                }
            };
        }

        @Override
        public Class<?> getObjectType() {
            return Docket.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        private ApiInfo apiInfo() {
            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title("API 集成测试")
                    .description(controllerClassName + " Swagger API Test")
                    .version("2.0")
                    .build();
            return apiInfo;
        }

    }

}