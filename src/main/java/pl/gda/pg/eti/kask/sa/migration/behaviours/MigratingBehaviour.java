package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

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
        Location location = myAgent.getLocations().get(0);
        myAgent.getLocations().remove(location);
        myAgent.doMove(location);
        if (myAgent.getLocations().isEmpty()) {
            myAgent.addBehaviour(new RequestContainersListBehaviour(myAgent));
        } else {
            myAgent.addBehaviour(new MigratingBehaviour(myAgent));
        }
    }

//    @Override
//    public boolean done() {
//        return myAgent.getLocations().isEmpty();
//    }

}
