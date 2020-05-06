package rocks.prestodb.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class RestTableDescription {
    private final String tableName;
    private final String endpoint;
    private final List<RestFieldDescription> fields;
    private String fullUrl;

    @JsonCreator
    public RestTableDescription(
            @JsonProperty("tableName") String tableName,
            @JsonProperty("endpoint") String endpoint,
            @JsonProperty("fields") List<RestFieldDescription> fields)
    {
        this.tableName = tableName;
        this.endpoint = endpoint;
        this.fields = fields;
    }

    @JsonProperty
    public String getTableName() {
        return tableName;
    }

    @JsonProperty
    public String getEndpoint() {
        return fullUrl;
    }

    @JsonProperty
    public List<RestFieldDescription> getFields() {
        return fields;
    }

    void setFullUrl(String base) {
        requireNonNull(base, "baseUrl is null");
        fullUrl = base + endpoint;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("tableName", tableName)
                .add("endpoint", endpoint)
                .add("fields", fields)
                .toString();
    }
}
