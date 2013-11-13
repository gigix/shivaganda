package io.ona.ziggy.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

public class Village {
    public String entityId;
    public String name;
    public String code;
    public Map<String, String> details;

    public Village(String entityId, String name, String code, Map<String, String> details) {
        this.entityId = entityId;
        this.name = name;
        this.code = code;
        this.details = details;
    }

    public String entityId() {
        return entityId;
    }

    public String name() {
        return name;
    }

    public String code() {
        return code;
    }

    public Map<String, String> details() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
