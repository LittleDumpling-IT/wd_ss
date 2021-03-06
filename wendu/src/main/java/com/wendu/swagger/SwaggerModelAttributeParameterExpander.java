package com.wendu.swagger;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Sets.newHashSet;
import static springfox.documentation.schema.Collections.collectionElementType;
import static springfox.documentation.schema.Collections.isContainerType;
import static springfox.documentation.schema.Types.typeNameFor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.members.ResolvedField;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.Maps;
import springfox.documentation.schema.Types;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.schema.AlternateTypeProvider;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeField;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

@Component
public class SwaggerModelAttributeParameterExpander extends ModelAttributeParameterExpander {
    private static final Logger LOG = LoggerFactory.getLogger(SwaggerModelAttributeParameterExpander.class);

    private final FieldProvider fieldProvider;

    @Autowired
    public SwaggerModelAttributeParameterExpander(FieldProvider fields) {
        super(fields);
        this.fieldProvider = fields;
    }


    @Override
    public List<Parameter> expand(final String parentName, final ResolvedType paramType, DocumentationContext documentationContext) {
        List<Parameter> parameters = Lists.newArrayList();
        Set<String> beanPropNames = getBeanPropertyNames(paramType.getErasedType());
        Iterable<ResolvedField> fields = FluentIterable.from(fieldProvider.in(paramType))
                .filter(onlyBeanProperties(beanPropNames));
        LOG.debug("Expanding parameter type: {}", paramType);
        AlternateTypeProvider alternateTypeProvider = documentationContext.getAlternateTypeProvider();

        FluentIterable<ModelAttributeField> modelAttributes = FluentIterable.from(fields)
                .transform(toModelAttributeField(alternateTypeProvider));

        FluentIterable<ModelAttributeField> expendables = modelAttributes.filter(not(simpleType()))
                .filter(not(recursiveType(paramType)));
        for (ModelAttributeField each : expendables) {
            LOG.debug("Attempting to expand expandable field: {}", each.getField());
            //parameters.addAll(
            //	expand(nestedParentName(parentName, each.getField()), each.getFieldType(), documentationContext));
        }

        FluentIterable<ModelAttributeField> collectionTypes = modelAttributes
                .filter(and(isCollection(), not(recursiveCollectionItemType(paramType))));
        for (ModelAttributeField each : collectionTypes) {
            LOG.debug("Attempting to expand collection/array field: {}", each.getField());

            ResolvedType itemType = collectionElementType(each.getFieldType());
            if (Types.isBaseType(itemType)) {
                parameters.add(simpleFields(parentName, documentationContext, each));
            } else {
                //parameters
                //	.addAll(expand(nestedParentName(parentName, each.getField()), itemType, documentationContext));
            }
        }

        FluentIterable<ModelAttributeField> simpleFields = modelAttributes.filter(simpleType());
        for (ModelAttributeField each : simpleFields) {
            parameters.add(simpleFields(parentName, documentationContext, each));
        }
        return FluentIterable.from(parameters).filter(not(hiddenParameters())).toList();
    }

    private Predicate<ModelAttributeField> recursiveCollectionItemType(final ResolvedType paramType) {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return equal(collectionElementType(input.getFieldType()), paramType);
            }
        };
    }

    private Predicate<Parameter> hiddenParameters() {
        return new Predicate<Parameter>() {
            @Override
            public boolean apply(Parameter input) {
                return input.isHidden();
            }
        };
    }

    private Parameter simpleFields(String parentName, DocumentationContext documentationContext, ModelAttributeField each) {
        LOG.debug("Attempting to expand field: {}", each);
        String dataTypeName = Optional.fromNullable(typeNameFor(each.getFieldType().getErasedType()))
                .or(each.getFieldType().getErasedType().getSimpleName());
        LOG.debug("Building parameter for field: {}, with type: ", each, each.getFieldType());
        ParameterExpansionContext parameterExpansionContext = new ParameterExpansionContext(dataTypeName, parentName,
                each.getField(), documentationContext.getDocumentationType(), new ParameterBuilder());
        return pluginsManager.expandParameter(parameterExpansionContext);
    }

    private Predicate<ModelAttributeField> recursiveType(final ResolvedType paramType) {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return equal(input.getFieldType(), paramType);
            }
        };
    }

    private Predicate<ModelAttributeField> simpleType() {
        return and(not(isCollection()), not(isMap()), or(belongsToJavaPackage(), isBaseType(), isEnum()));
    }

    private Predicate<ModelAttributeField> isCollection() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return isContainerType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> isMap() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Maps.isMapType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> isEnum() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return input.getFieldType().getErasedType().isEnum();
            }
        };
    }

    private Predicate<ModelAttributeField> belongsToJavaPackage() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return packageName(input.getFieldType().getErasedType()).startsWith("java.lang");
            }
        };
    }

    private Predicate<ModelAttributeField> isBaseType() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Types.isBaseType(input.getFieldType()) || input.getField().getType().isPrimitive();
            }
        };
    }

    private Function<ResolvedField, ModelAttributeField> toModelAttributeField(
            final AlternateTypeProvider alternateTypeProvider) {
        return new Function<ResolvedField, ModelAttributeField>() {
            @Override
            public ModelAttributeField apply(ResolvedField input) {
                return new ModelAttributeField(fieldType(alternateTypeProvider, input), input);
            }
        };
    }

    private Predicate<ResolvedField> onlyBeanProperties(final Set<String> beanPropNames) {
        return new Predicate<ResolvedField>() {
            @Override
            public boolean apply(ResolvedField input) {
                return beanPropNames.contains(input.getName());
            }
        };
    }

    private String nestedParentName(String parentName, ResolvedField field) {
        String name = field.getName();
        ResolvedType fieldType = field.getType();
        if (isContainerType(fieldType) && !Types.isBaseType(collectionElementType(fieldType))) {
            name += "[0]";
        }

        if (isNullOrEmpty(parentName)) {
            return name;
        }
        return String.format("%s.%s", parentName, name);
    }

    private ResolvedType fieldType(AlternateTypeProvider alternateTypeProvider, ResolvedField field) {
        return alternateTypeProvider.alternateFor(field.getType());
    }

    private String packageName(Class<?> type) {
        return Optional.fromNullable(type.getPackage()).transform(toPackageName()).or("java");
    }

    private Function<Package, String> toPackageName() {
        return new Function<Package, String>() {
            @Override
            public String apply(Package input) {
                return input.getName();
            }
        };
    }

    private Set<String> getBeanPropertyNames(final Class<?> clazz) {

        try {
            Set<String> beanProps = new HashSet<String>();
            PropertyDescriptor[] propDescriptors = getBeanInfo(clazz).getPropertyDescriptors();

            for (PropertyDescriptor propDescriptor : propDescriptors) {

                if (propDescriptor.getReadMethod() != null && propDescriptor.getWriteMethod() != null) {
                    beanProps.add(propDescriptor.getName());
                }
            }

            return beanProps;

        } catch (IntrospectionException e) {
            LOG.warn(String.format("Failed to get bean properties on (%s)", clazz), e);
        }
        return newHashSet();
    }

    @VisibleForTesting
    BeanInfo getBeanInfo(Class<?> clazz) throws IntrospectionException {
        return Introspector.getBeanInfo(clazz);
    }

}
