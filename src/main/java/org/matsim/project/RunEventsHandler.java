package org.matsim.project;

import org.apache.commons.lang3.event.EventUtils;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventsHandler {

    public static void main(String[] args) {

        String inputFile = "output/output_events.xml.gz";
        String outputFile = "output100/output100.txt";

        // this manager is infrastructure for matsim... super fundamental
        EventsManager eventsManager = EventsUtils.createEventsManager();

        SimpleEventHandler eventHandler = new SimpleEventHandler();
        // there is confusion with the class he created and I didn't
        //LinkEventHandler eventHandler = new SimpleEventHandler();
        eventsManager.addHandler(eventHandler); // add more events

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager); // reads events
        eventsReader.readFile(inputFile);

        eventHandler.printResult();
    }
}
