package io.ona.ziggy.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FormSubmissionDTO {
    private String reporterId;
    private String instanceId;
    private String entityId;
    private String formName;
    private String formInstance;
    private String clientVersion;
    private String serverVersion;
    private String formDataDefinitionVersion;

    public FormSubmissionDTO(String reporterId, String instanceId, String entityId, String formName, String formInstance, String clientVersion, String formDataDefinitionVersion) {
        this.reporterId = reporterId;
        this.instanceId = instanceId;
        this.entityId = entityId;
        this.formName = formName;
        this.formInstance = formInstance;
        this.clientVersion = clientVersion;
        this.formDataDefinitionVersion = formDataDefinitionVersion;
    }

    public FormSubmissionDTO withServerVersion(String version) {
        this.serverVersion = version;
        return this;
    }

    public String reporterId() {
        return this.reporterId;
    }

    public String instanceId() {
        return this.instanceId;
    }

    public String entityId() {
        return this.entityId;
    }

    public String formName() {
        return this.formName;
    }

    public String instance() {
        return formInstance;
    }

    public String clientVersion() {
        return this.clientVersion;
    }

    public String formDataDefinitionVersion() {
        return this.formDataDefinitionVersion;
    }

    public String serverVersion() {
        return serverVersion;
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
