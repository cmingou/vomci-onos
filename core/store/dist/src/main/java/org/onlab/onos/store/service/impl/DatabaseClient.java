package org.onlab.onos.store.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import net.kuujo.copycat.protocol.Response.Status;
import net.kuujo.copycat.protocol.SubmitRequest;
import net.kuujo.copycat.protocol.SubmitResponse;
import net.kuujo.copycat.spi.protocol.ProtocolClient;

import org.onlab.onos.store.service.DatabaseException;
import org.onlab.onos.store.service.ReadRequest;
import org.onlab.onos.store.service.WriteRequest;

public class DatabaseClient {

    private final ProtocolClient client;

    public DatabaseClient(ProtocolClient client) {
        this.client = client;
    }

    private static String nextId() {
        return UUID.randomUUID().toString();
    }

    public boolean createTable(String tableName) {

        SubmitRequest request =
                new SubmitRequest(
                        nextId(),
                        "createTable",
                        Arrays.asList(tableName));
        CompletableFuture<SubmitResponse> future = client.submit(request);
        try {
            return (boolean) future.get().result();
        } catch (InterruptedException | ExecutionException e) {
            throw new DatabaseException(e);
        }
    }

    public void dropTable(String tableName) {

        SubmitRequest request =
                new SubmitRequest(
                        nextId(),
                        "dropTable",
                        Arrays.asList(tableName));
        CompletableFuture<SubmitResponse> future = client.submit(request);
        try {
            if (future.get().status() == Status.OK) {
                throw new DatabaseException(future.get().toString());
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new DatabaseException(e);
        }
    }

    public void dropAllTables() {

        SubmitRequest request =
                new SubmitRequest(
                        nextId(),
                        "dropAllTables",
                        Arrays.asList());
        CompletableFuture<SubmitResponse> future = client.submit(request);
        try {
            if (future.get().status() != Status.OK) {
                throw new DatabaseException(future.get().toString());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new DatabaseException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> listTables() {

        SubmitRequest request =
                new SubmitRequest(
                        nextId(),
                        "listTables",
                        Arrays.asList());
        CompletableFuture<SubmitResponse> future = client.submit(request);
        try {
            return (List<String>) future.get().result();
        } catch (InterruptedException | ExecutionException e) {
            throw new DatabaseException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<InternalReadResult> batchRead(List<ReadRequest> requests) {

        SubmitRequest request = new SubmitRequest(
                        nextId(),
                        "read",
                        Arrays.asList(requests));

        CompletableFuture<SubmitResponse> future = client.submit(request);
        try {
            List<InternalReadResult> internalReadResults = (List<InternalReadResult>) future.get().result();
            return internalReadResults;
        } catch (InterruptedException | ExecutionException e) {
            throw new DatabaseException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<InternalWriteResult> batchWrite(List<WriteRequest> requests) {

        SubmitRequest request = new SubmitRequest(
                        nextId(),
                        "write",
                        Arrays.asList(requests));

        CompletableFuture<SubmitResponse> future = client.submit(request);
        try {
            List<InternalWriteResult> internalWriteResults = (List<InternalWriteResult>) future.get().result();
            return internalWriteResults;
        } catch (InterruptedException | ExecutionException e) {
            throw new DatabaseException(e);
        }
    }
}
