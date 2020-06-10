package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkModifier {
    public static void main(String[] args) { // read in the network file
        Path inputNetwork = Paths.get(args[0]);
        Path outputNetwork = Paths.get(args[1]); // could also hard code the path here

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        network.getLinks().get(Id.createLinkId("6")).setCapacity(10); // 10 vehicles per hour

        new NetworkWriter(network).write(outputNetwork.toString());
    }
}
