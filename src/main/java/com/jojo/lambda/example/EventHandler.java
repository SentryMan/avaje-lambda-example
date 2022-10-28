package com.jojo.lambda.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.jojo.lambda.example.service.EventService;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import jakarta.inject.Singleton;

@Singleton
@Json.Import({SQSEvent.class})
public final class EventHandler {

  private final JsonType<SQSEvent> sqsType;
  private final JsonType<List<String>> strType;
  private final EventService service;

  public EventHandler(Jsonb jsonb, EventService service) {
    sqsType = jsonb.type(SQSEvent.class);
    strType = jsonb.type(String.class).list();
    this.service = service;
  }

  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
    final var logger = context.getLogger();

    final var event = sqsType.fromJson(inputStream);
    logger.log("EVENT TYPE: " + event.getClass().toString());
    logger.log("EVENT TYPE: " + event.getClass().toString());
    final var result = service.process(event);
    strType.toJson(result, outputStream);
  }
}
