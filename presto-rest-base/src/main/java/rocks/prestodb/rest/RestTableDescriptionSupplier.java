package rocks.prestodb.rest;

import com.google.common.collect.ImmutableMap;
import io.airlift.json.JsonCodec;
import io.prestosql.spi.connector.SchemaTableName;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.file.Files.readAllBytes;
import static java.util.Objects.requireNonNull;

public class RestTableDescriptionSupplier
    implements Provider<TableDescriptionSupplier>
{
    private final JsonCodec<RestBaseDescription> baseDescriptionCodec;
    private final File tableDescriptionDir;
    private final String defaultSchema;

    @Inject
    RestTableDescriptionSupplier(RestConfig config, JsonCodec<RestBaseDescription> baseDescriptionCodec)
    {
        this.baseDescriptionCodec = requireNonNull(baseDescriptionCodec, "baseDescriptionCodec is null");
        requireNonNull(config, "restConfig is null");
        this.tableDescriptionDir = config.getTableDescriptionDir();
        this.defaultSchema = config.getDefaultSchema();
    }

    @Override
    public TableDescriptionSupplier get() {
        Map<SchemaTableName, RestTableDescription> tables = populateTables();
        return new MapBasedTableDescriptionSupplier(tables);
    }

    private Map<SchemaTableName, RestTableDescription> populateTables() {
        ImmutableMap.Builder<SchemaTableName, RestTableDescription> builder = ImmutableMap.builder();

        File baseDescriptionFile = getBaseDescriptionFile(tableDescriptionDir);
        RestBaseDescription baseDescription = null;

        try {
            baseDescription = baseDescriptionCodec.fromJson(readAllBytes(baseDescriptionFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert baseDescription != null;
        for(RestTableDescription table : baseDescription.getTables()) {
            table.setFullUrl(baseDescription.getBaseUrl());
            builder.put(new SchemaTableName(baseDescription.getSchemaName().orElse(defaultSchema), table.getTableName()), table);
        }

        return builder.build();
    }

    private File getBaseDescriptionFile(File dir) {
        if((dir != null) && dir.isDirectory()) {
            File[] files = dir.listFiles();
            requireNonNull(files, "table description directory is empty...");
            checkArgument(files.length == 1, "there can only be a single description file...");
            return new File(files[0].getPath());
        }

        return null;
    }
}
