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
        eventHubName = "items", 
        connection = "AzureCosmosDbConnString", 
        consumerGroup = "changefeed", 
        cardinality = Cardinality.MANY) List<String> message,
        @CosmosDBOutput(name = "outputItem",
          databaseName = "bulkImportDb",
          collectionName = "returns",
          connectionStringSetting = "AzureCosmosDbConnString")
        OutputBinding<List<String>> outputItem,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger function executed.");
        context.getLogger().info("Length:" + message.size());
        outputItem.setValue(message);
        message.forEach(singleMessage -> context.getLogger().info(singleMessage));
    }
}
