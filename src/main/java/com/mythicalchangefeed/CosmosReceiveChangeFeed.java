package com.mythicalchangefeed;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Cosmos DB trigger.
 */
public class CosmosReceiveChangeFeed {
    /**
     * This function will be invoked when there are inserts or updates in the specified database and collection.
     */
    @FunctionName("CosmosReceiveChangeFeed")
    
    public void run(
        @CosmosDBTrigger(
            name = "items",
            databaseName = "bulkImportDb",
            collectionName = "c3",
            connectionStringSetting = "AzureCosmosDbConnString",
            createLeaseCollectionIfNotExists = true
        )
        Object[] items,
        @EventHubOutput(name = "event", eventHubName = "member4", connection = "AzureEventHubConnection") OutputBinding<Object> toEH,
        final ExecutionContext context
    ) {
        context.getLogger().info("Documents count: " + items.length);
        for (Object obj : items) {
         toEH.setValue(obj);   
        }
    }
}
