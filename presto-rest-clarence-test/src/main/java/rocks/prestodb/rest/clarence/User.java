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

import com.google.common.collect.ImmutableList;
import io.prestosql.spi.connector.ColumnMetadata;
import io.prestosql.spi.connector.ConnectorTableMetadata;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.prestosql.spi.connector.SchemaTableName;
import io.prestosql.spi.type.BigintType;
import io.prestosql.spi.type.VarcharType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private static final SchemaTableName schemaTableName = new SchemaTableName("default", "users");

    public User(
            @JsonProperty("id") int id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() { return id; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public static ConnectorTableMetadata getTableMetadata() {

        return new ConnectorTableMetadata(
                schemaTableName,
                ImmutableList.of(
                        new ColumnMetadata("id", BigintType.BIGINT),
                        new ColumnMetadata("firstName", VarcharType.createUnboundedVarcharType()),
                        new ColumnMetadata("lastName", VarcharType.createUnboundedVarcharType())
                )
        );
    }
}
