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

import io.airlift.configuration.Config;
import io.airlift.configuration.ConfigDescription;

import javax.validation.constraints.NotNull;
import java.io.File;

public class RestConfig {
//    private String restEndpoint;
    private String defaultSchema = "default";
    private File tableDescriptionDir = new File("etc/rest/");

//    @NotNull
//    public String getRestEndPoint() {
//        return restEndpoint;
//    }
//
//    @Config("rest.endpoint")
//    @ConfigDescription("The endpoint for this rest connector. Must not be empty")
//    public RestConfig setRestEndpoint(String endpoint) {
//        this.restEndpoint = endpoint;
//        return this;
//    }

    public File getTableDescriptionDir() {
        return tableDescriptionDir;
    }

    @Config("rest.table-description-dir")
    @ConfigDescription("The directory to store table description files. Default is etc/rest/")
    public RestConfig setTableDescriptionDir(File tableDescriptionDir) {
        this.tableDescriptionDir = tableDescriptionDir;
        return this;
    }

    @NotNull
    public String getDefaultSchema() {
        return defaultSchema;
    }

    @Config("rest.default-schema")
    @ConfigDescription("Schema name to use in the connector")
    public RestConfig setDefaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
        return this;
    }

}
