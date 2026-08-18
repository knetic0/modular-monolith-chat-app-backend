package com.mehmetsolak.mini_chat_app.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.mongodb.MongoInterruptedException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import lombok.Setter;
import org.bson.Document;

@Setter
public class MongoAppender extends AppenderBase<ILoggingEvent> {

    private String uri;
    private String database;
    private String collection;

    private MongoClient mongoClient;
    private MongoCollection<Document> mongoCollection;

    @Override
    public void start() {
        mongoClient = MongoClients.create(uri);
        mongoCollection = mongoClient.getDatabase(database).getCollection(collection);
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if(!isStarted() || mongoCollection == null) return;

        try {
            Document document = new Document()
                    .append("message", eventObject.getFormattedMessage())
                    .append("level", eventObject.getLevel().toString())
                    .append("thread", eventObject.getThreadName())
                    .append("logger", eventObject.getLoggerName())
                    .append("timestamp", eventObject.getTimeStamp());

            if(eventObject.getMDCPropertyMap() != null) {
                document.append("mdc", eventObject.getMDCPropertyMap());
            }

            if (eventObject.getThrowableProxy() != null) {
                document.append(
                        "exception",
                        eventObject.getThrowableProxy().getClassName()
                                + ": "
                                + eventObject.getThrowableProxy().getMessage()
                );
            }

            mongoCollection.insertOne(document);
        } catch(MongoInterruptedException ignore) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            addError("Error occurred while logging", e);
        }
    }

    @Override
    public void stop() {
        if(!isStarted() || mongoCollection == null) return;

        super.stop();

        if (mongoClient != null) {
            boolean interrupted = Thread.interrupted();

            try {
                mongoClient.close();
            } catch (Exception e) {
                addError("Error occurred while closing MongoClient", e);
            } finally {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
