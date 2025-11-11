package pl.gda.pg.eti.kask.sa.migration.agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;
import java.util.List;
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
    private List<Location> locations;

    @Setter
    @Getter
    private Location firstLocation;

    @Getter
    @Setter
    private int timeout;
    
    public MigratingAgent() {
    }

    @Override
    protected void setup() {
        super.setup();
        this.timeout = 1200;
        this.firstLocation = this.here();
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
//        JOptionPane.showMessageDialog(null, "Przybywam!");
        try {
            Thread.sleep(this.timeout/2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void beforeMove() {
        try {
            Thread.sleep(this.timeout/2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        JOptionPane.showMessageDialog(null, "Odchodzę!");
        //stop threads        
        //save state
        super.beforeMove();
    }

}
