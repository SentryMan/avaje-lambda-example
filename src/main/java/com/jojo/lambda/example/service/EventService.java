package com.jojo.lambda.example.service;

import java.util.List;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import jakarta.inject.Singleton;

@Singleton
public class EventService  {

  public List<String> process(SQSEvent event) {
    return event.getRecords().stream().map(SQSMessage::getBody).toList();
  }
}
