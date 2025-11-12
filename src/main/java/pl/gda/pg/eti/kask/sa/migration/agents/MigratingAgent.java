package pl.gda.pg.eti.kask.sa.migration.agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.sa.migration.behaviours.RequestContainersListBehaviour;

/**
 *
 * @author psysiu
 */
public class MigratingAgent extends Agent {

    @Setter
    @Getter
    private Map<String, Boolean> locationsVisited = new HashMap<>();

    @Setter
    @Getter
    private Map<String, Location> locationsMap = new HashMap<>();
    
    public MigratingAgent() {
    }

    @Override
    protected void setup() {
        super.setup();

        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
        this.addBehaviour(new RequestContainersListBehaviour(this));
    }

    @Override
    protected void afterMove() {
        super.afterMove();

        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());

        //restore state
        //resume threads
        String visited = "";
        for (Map.Entry<String, Boolean> entry : locationsVisited.entrySet()) {
            visited = visited.concat(entry.getKey() + ": " + (entry.getValue() ? "Tak\n" : "Nie\n"));
        }

        JOptionPane.showMessageDialog(null, "Przybywam do " + this.here().getName() + "\n\nOdwiedzono:\n".concat(visited));
//        try {
//            Thread.sleep(this.timeout/2);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }



    @Override
    protected void beforeMove() {
//        try {
//            Thread.sleep(this.timeout/2);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        String visited = "";
        for (Map.Entry<String, Boolean> entry : locationsVisited.entrySet()) {
            visited = visited.concat(entry.getKey() + ": " + (entry.getValue() ? "Tak\n" : "Nie\n"));
        }

        JOptionPane.showMessageDialog(null, "Odchodzę z " + this.here().getName() + "\n\nOdwiedzono:\n".concat(visited));
        //stop threads        
        //save state
        super.beforeMove();
    }

}
