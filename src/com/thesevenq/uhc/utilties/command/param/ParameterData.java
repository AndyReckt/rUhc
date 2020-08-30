package com.thesevenq.uhc.utilties.command.param;

import lombok.Getter;

public final class ParameterData {

    @Getter private String name;
    @Getter private boolean wildcard;
    @Getter private String defaultValue;
    @Getter private String[] tabCompleteFlags;
    @Getter private Class<?> parameterClass;

    public ParameterData(Parameter parameterAnnotation, Class<?> parameterClass) {
        this.name = parameterAnnotation.name();
        this.wildcard = parameterAnnotation.wildcard();
        this.defaultValue = parameterAnnotation.defaultValue();
        this.tabCompleteFlags = parameterAnnotation.tabCompleteFlags();
        this.parameterClass = parameterClass;
    }

}