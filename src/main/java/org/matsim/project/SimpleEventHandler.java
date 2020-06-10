package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class SimpleEventHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler, LinkEnterEventHandler {

    Map<Id<Person>, Double> departureTimeByPersonMap = new HashMap<>();
    Map<Integer, Integer> agentNumberPerHour = new HashMap<>(); //key = 0 for 0:00 - 1:00

    private final BufferedWriter bufferedWriter;

    // he created a class for thiss...
    // this is the necessary constructor
    // also look up exceptions in java..
    public LinkEventHandler(String outputFile) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            bufferedWriter = newBufferedWriter(fileWriter);
        } catch (IDException ee) {
            throw new RuntimeException(ee);
        }


    }
}

    @Override
    public void handleEvent(PersonDepartureEvent event) {
        System.out.println("Departure event; time " + event.getTime() + " -- linkId: " + event.getLinkId()
                + " personId: " + event.getPersonId());

        departureTimeByPersonMap.put(event.getPersonId(), event.getTime()); // its listening to the event manager



    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        // play around with travel times here

        System.out.println("Person ID: " + event.getPersonId() + " Travel time = " + (event.getTime() - departureTimeByPersonMap.get(event.getPersonId())));

    }

    private int getSlot(double time) {
        return (int) time/3600;
    }

    private double[] volumeLink6 = new double[24];

    @Override
    public void handleEvent(LinkEnterEvent event) {
        if(event.getLinkId().toString().equals("6")){
            System.out.println("Time of entry: " + event.getTime() + " -- LinkID: " + event.getLinkId());

            int slot = getSlot(event.getTime());
            this.volumeLink6[slot]++;


            //set by time on hashmap
            // int h = (int) event.getTime()/(60*60);
            //agentNumberPerHour

        }
    }

    public void printResult() {
        try {
            bufferedWriter.write("Hour \t Volume");
            bufferedWriter.newLine();
            for (int i=0; i<24; i++) {
                double volume = this.volumeLink6[i];
                bufferedWriter.write(i + "\t" + volume);
                bufferedWriter.newLine();
                // System.out.println("Volume on link 6 from " + i + "to " + (i + 1) + " o'clock = " + volume);

            } bufferedWriter.close();
            } catch (IDException ee) {
                throw new RuntimeException()(ee);
            }
    }


}
