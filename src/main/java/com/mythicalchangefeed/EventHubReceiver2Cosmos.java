package com.mythicalchangefeed;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import java.util.*;

/**
 * Azure Functions with Event Hub trigger.
 */
public class EventHubReceiver2Cosmos {
    /**
     * This function will be invoked when an event is received from Event Hub.
     */
    @FunctionName("EventHubReceiver2Cosmos")
    public void run(
        @EventHubTrigger(name = "message", 
        eventHubName = "member4", 
        connection = "AzureEventHubConnection", 
        consumerGroup = "changefeed", 
        cardinality = Cardinality.ONE) String message,
        @CosmosDBOutput(name = "outputItem",
          databaseName = "bulkImportDb",
          collectionName = "returns",
          connectionStringSetting = "AzureCosmosDbConnString")
        OutputBinding<String> outputItem,
        final ExecutionContext context
    ) {
        context.getLogger().info("EH2Cosmos");
        context.getLogger().info(message);
        outputItem.setValue(message);
    }
}
