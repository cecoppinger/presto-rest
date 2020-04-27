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

import io.prestosql.spi.NodeManager;
import io.prestosql.spi.connector.Connector;
import io.prestosql.spi.connector.ConnectorMetadata;
import io.prestosql.spi.connector.ConnectorRecordSetProvider;
import io.prestosql.spi.connector.ConnectorSplitManager;
import io.prestosql.spi.connector.ConnectorTransactionHandle;
import io.prestosql.spi.transaction.IsolationLevel;

public class RestConnector
        implements Connector
{
    private final NodeManager nodeManager;
    private final Rest rest;

    public RestConnector(NodeManager nodeManager, Rest rest)
    {
        this.nodeManager = nodeManager;
        this.rest = rest;
    }

    @Override
    public ConnectorTransactionHandle beginTransaction(IsolationLevel isolationLevel, boolean readOnly)
    {
        return new RestTransactionHandle(0);
    }

    @Override
    public ConnectorMetadata getMetadata(ConnectorTransactionHandle transaction)
    {
        return new RestMetadata(rest);
    }

    @Override
    public ConnectorSplitManager getSplitManager()
    {
        return new RestSplitManager(nodeManager);
    }

    @Override
    public ConnectorRecordSetProvider getRecordSetProvider()
    {
        return new RestRecordSetProvider(rest);
    }
}
