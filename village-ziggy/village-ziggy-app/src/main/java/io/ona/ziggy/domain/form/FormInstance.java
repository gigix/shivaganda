package io.ona.ziggy.domain.form;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FormInstance {
    private String form_data_definition_version;
    private FormData form;

    public FormInstance(FormData form, String form_data_definition_version) {
        this.form = form;
        this.form_data_definition_version = form_data_definition_version;
    }

    public FormData form() {
        return form;
    }

    public String getFieldValue(String name) {
        return form.getFieldValue(name);
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
