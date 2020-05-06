package rocks.prestodb.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.prestosql.spi.type.Type;

import static com.google.common.base.MoreObjects.toStringHelper;

public class RestFieldDescription {
    private final String name;
    private final Type type;
    private final String dataFormat;
    private final String mapping;

    @JsonCreator
    public RestFieldDescription(
            @JsonProperty("name") String name,
            @JsonProperty("type") Type type,
            @JsonProperty("dataFormat") String dataFormat,
            @JsonProperty("mapping") String mapping)
    {
        this.name = name;
        this.type = type;
        this.dataFormat = dataFormat;
        this.mapping = mapping;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Type getType() {
        return type;
    }

    @JsonProperty
    public String getDataFormat() {
        return dataFormat;
    }

    @JsonProperty
    public String getMapping() {
        return mapping;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("type", type)
                .add("dataFormat", dataFormat)
                .add("mapping", mapping)
                .toString();
    }
}
