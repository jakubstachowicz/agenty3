package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Result;
import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

/**
 *
 * @author psysiu
 */
@Log
public class ReceiveContainersLisBehaviour extends Behaviour {

    private boolean done = false;

    protected final MigratingAgent myAgent;

    protected final String conversationId;

    protected MessageTemplate mt;

    public ReceiveContainersLisBehaviour(MigratingAgent agent, String conversationId) {
        super(agent);
        myAgent = agent;
        this.conversationId = conversationId;
    }

    @Override
    public void onStart() {
        super.onStart();
        mt = MessageTemplate.MatchConversationId(conversationId);
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            done = true;
            try {
                ContentElement ce = myAgent.getContentManager().extractContent(msg);
                jade.util.leap.List items = ((Result) ce).getItems();
                List<Location> locations = new ArrayList<>();
                myAgent.setLocationsMap(new HashMap<>());

                items.iterator().forEachRemaining(i -> {
                    locations.add((Location) i);
                    myAgent.getLocationsMap().put(((Location) i).getName(), (Location) i);
                });
                for (Location location : locations) {
                    if (!myAgent.getLocationsVisited().containsKey(location.getName())) {
                        myAgent.getLocationsVisited().put(location.getName(), false);
                    }
                }
                for (String location : myAgent.getLocationsVisited().keySet()) {
                    if (locations.stream().noneMatch(l -> l.getName().equals(location))) {
                        myAgent.getLocationsVisited().remove(location);
                    }
                }

                if (myAgent.getLocationsVisited().isEmpty()) {
                    throw new Exception("sth is very wrong");
                }
                if (myAgent.getLocationsVisited().values().stream().allMatch(value -> value == true)) {
                    myAgent.getLocationsVisited().replaceAll((key, value) -> false);
                }

                myAgent.addBehaviour(new MigratingBehaviour(myAgent));
            } catch (Codec.CodecException | OntologyException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean done() {
        return done;
    }

}
