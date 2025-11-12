package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

import java.util.Map;

/**
 * @author psysiu
 */
public class MigratingBehaviour extends OneShotBehaviour {

    protected final MigratingAgent myAgent;

    public MigratingBehaviour(MigratingAgent agent) {
        super(agent);
        myAgent = agent;
    }

    @Override
    public void action() {
        String location = "";

        for (Map.Entry<String, Boolean> entry : myAgent.getLocationsVisited().entrySet()) {
            if (!entry.getValue().booleanValue()) {
                entry.setValue(true);
                location = entry.getKey();
                break;
            }
        }

        myAgent.setToBeVisited(location);
        myAgent.doMove(myAgent.getLocationsMap().get(location));
        myAgent.addBehaviour(new RequestContainersListBehaviour(myAgent));
    }

//    @Override
//    public boolean done() {
//        return myAgent.getLocations().isEmpty();
//    }

}
