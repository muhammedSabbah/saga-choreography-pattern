package com.woody.saga.choreography.pattern.event;

import java.util.Date;
import java.util.UUID;

public interface Event {

	UUID getEventId();

	Date getEventDate();

}
