/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rocks.prestodb.rest;

import io.prestosql.spi.connector.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class RestMetadata
        implements ConnectorMetadata
{
    private final Rest rest;

    public RestMetadata(Rest rest)
    {
        this.rest = rest;
    }

    @Override
    public List<String> listSchemaNames(ConnectorSession connectorSession)
    {
        return rest.listSchemas();
    }

    @Override
    public ConnectorTableHandle getTableHandle(ConnectorSession connectorSession, SchemaTableName schemaTableName)
    {
        if (rest.listTables().contains(schemaTableName)) {
            return new RestTableHandle(schemaTableName);
        }
        return null;
    }

    @Override
    public ConnectorTableMetadata getTableMetadata(ConnectorSession connectorSession, ConnectorTableHandle connectorTableHandle)
    {
        RestTableHandle tableHandle = Types.checkType(connectorTableHandle, RestTableHandle.class, "tableHandle");
        return rest.getTableMetadata(tableHandle.getSchemaTableName());
    }

    @Override
    public List<SchemaTableName> listTables(ConnectorSession connectorSession, Optional<String> schemaNameOrNull)
    {
        return rest.listTables();
    }

    @Override
    public Map<String, ColumnHandle> getColumnHandles(ConnectorSession connectorSession, ConnectorTableHandle connectorTableHandle)
    {
        return getTableMetadata(connectorSession, connectorTableHandle).getColumns().stream()
                .collect(toMap(column -> column.getName(), column -> new RestColumnHandle(column.getName(), column.getType())));
    }

    @Override
    public ColumnMetadata getColumnMetadata(ConnectorSession connectorSession, ConnectorTableHandle connectorTableHandle, ColumnHandle columnHandle)
    {
        RestColumnHandle restColumnHandle = Types.checkType(columnHandle, RestColumnHandle.class, "columnHandle");
        return new ColumnMetadata(restColumnHandle.getName(), restColumnHandle.getType());
    }

    @Override
    public Map<SchemaTableName, List<ColumnMetadata>> listTableColumns(ConnectorSession connectorSession, SchemaTablePrefix schemaTablePrefix)
    {
        return null;
    }

    @Override
    public boolean usesLegacyTableLayouts() {
        return false;
    }

    @Override
    public ConnectorTableProperties getTableProperties(ConnectorSession session, ConnectorTableHandle table)
    {
        return new ConnectorTableProperties();
    }
}
