package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.StrategyConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule;
import org.matsim.core.scenario.ScenarioUtils;

public class NewMatsimRunner {

    // normal matsim script. i need to be able to type this.

    public static void main(String[] args) {
        Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");
        // edit the config file here. xml is for people that don't know java
        config.controler().setLastIteration(5);
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);

//        config.controler().setWriteEventsInterval();

        StrategyConfigGroup.StrategySettings stratSets = new StrategyConfigGroup.StrategySettings();
        stratSets.setWeight(0.1);
//        stratSets.setStrategyName(DefaultPlanStrategiesModule.DefaultStrategy.ChangeTripMode);
        stratSets.setStrategyName(DefaultPlanStrategiesModule.DefaultSelector.BestScore); // or .SelectExpBeta
        config.strategy().addStrategySettings(stratSets);

        config.plans().setInputFile("plans100.xml");
        // there are places in the output to check the file being run
        config.network().setInputFile("../../output/output_network.xml.gz");

        Scenario scenario = ScenarioUtils.loadScenario(config);

        Controler controler = new Controler(scenario);

        controler.run();
    }
}
