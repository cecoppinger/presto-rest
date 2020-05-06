package rocks.prestodb.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static com.google.common.base.MoreObjects.toStringHelper;

public class RestBaseDescription {
    private final Optional<String> schemaName;
    private final String baseUrl;
    private final List<RestTableDescription> tables;

    @JsonCreator
    public RestBaseDescription(
            @JsonProperty("schemaName") Optional<String> schemaName,
            @JsonProperty("baseUrl") String baseUrl,
            @JsonProperty("tables") List<RestTableDescription> tables)
    {
        this.schemaName = requireNonNull(schemaName, "schemaName is null");
        this.baseUrl = requireNonNull(baseUrl, "baseUrl is null");
        this.tables = ImmutableList.copyOf(requireNonNull(tables, "tables is null"));
    }

    @JsonProperty
    public Optional<String> getSchemaName() {
        return schemaName;
    }

    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
    }

    @JsonProperty
    public List<RestTableDescription> getTables() {
        return tables;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("schemaName", schemaName)
                .add("baseUrl", baseUrl)
                .add("tables", tables)
                .toString();
    }
}
