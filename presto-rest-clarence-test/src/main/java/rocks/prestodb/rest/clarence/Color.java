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

package rocks.prestodb.rest.clarence;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import io.prestosql.spi.connector.ColumnMetadata;
import io.prestosql.spi.connector.ConnectorTableMetadata;
import io.prestosql.spi.connector.SchemaTableName;
import io.prestosql.spi.type.VarcharType;

public class Color {
    private final String name;
    private static final SchemaTableName schemaTableName = new SchemaTableName("default", "colors");

    public Color(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public static ConnectorTableMetadata getTableMetadata() {

        return new ConnectorTableMetadata(
                schemaTableName,
                ImmutableList.of(
                        new ColumnMetadata("name", VarcharType.createUnboundedVarcharType())
                )
        );
    }
}
