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

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import io.prestosql.spi.connector.ConnectorTableMetadata;
import io.prestosql.spi.connector.SchemaTableName;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rocks.prestodb.rest.Rest;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserRest implements Rest {

    private static final String SCHEMA = "default";

    private final UserRestService service = new Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/cecoppinger/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(UserRestService.class);

    @Override
    public ConnectorTableMetadata getTableMetadata(SchemaTableName schemaTableName) {
        ConnectorTableMetadata metadata = null;

        Objects.requireNonNull(schemaTableName, "schemaTableName is null");

        String tableName = schemaTableName.getTableName();
        if(tableName.equals("users"))
            metadata = User.getTableMetadata();
        else if(tableName.equals("colors"))
            metadata = Color.getTableMetadata();

        return metadata;
    }

    @Override
    public List<String> listSchemas() {
        return ImmutableList.of(SCHEMA);
    }

    @Override
    public List<SchemaTableName> listTables(String schema) {

        return ImmutableList.of(
                new SchemaTableName(SCHEMA, "users"),
                new SchemaTableName(SCHEMA, "colors")
        );
    }

    @Override
    public Collection<? extends List<?>> getRows(SchemaTableName schemaTableName) {
        if(schemaTableName.getTableName().equals("users"))
            return getUsers();
        else if(schemaTableName.getTableName().equals("colors"))
            return getColors();
        else
            return null;
    }

    private Collection<? extends List<?>> getUsers() {
        try {
            Response<List<User>> response = service.getUsers().execute();
            validateResponse(response);
            List<User> users = response.body();
            return users.stream()
                    .map(user -> ImmutableList.of(user.getId(), user.getFirstName(), user.getLastName()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private Collection<? extends List<?>> getColors() {
        try {
            Response<List<Color>> response = service.getColors().execute();
            validateResponse(response);
            List<Color> colors = response.body();
            return colors.stream()
                    .map(color -> ImmutableList.of(color.getName()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private void validateResponse(Response response) {
        if(!response.isSuccessful())
            throw new IllegalStateException("Unable to read: " + response.message());
    }

//    public static void main(String[] args) throws IOException {
//        UserRest rest = new UserRest();
//        List<User> users = rest.service.getUsers().execute().body();
//        for(User user : users) {
//            System.out.println(user.getFirstName() + " " + user.getLastName());
//        }
//    }

}
