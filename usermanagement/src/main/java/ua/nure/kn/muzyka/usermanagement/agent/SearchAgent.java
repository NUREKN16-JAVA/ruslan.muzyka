package ua.nure.kn.muzyka.usermanagement.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ua.nure.kn.muzyka.usermanagement.User;
import ua.nure.kn.muzyka.usermanagement.db.DaoFactory;
import ua.nure.kn.muzyka.usermanagement.db.exception.DatabaseException;
import jade.domain.FIPAException;
import org.dbunit.util.search.SearchException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAgent extends Agent {

    private List<AID> aids;
    private SearchGui gui = null;

    @Override
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");

        gui = new SearchGui(this);
        gui.setVisible(true);

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new TickerBehaviour(this, 5000) {

            @Override
            protected void onTick() {
                DFAgentDescription agentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription1 = new ServiceDescription();
                serviceDescription1.setType("searching");
                agentDescription.addServices(serviceDescription1);
                try {
                    DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
                    aids = new ArrayList<>();
                    for (int i = 0; i < descriptions.length; i++) {
                        DFAgentDescription d = descriptions[i];
                        if (!d.getName().equals(getAID())) {
                            aids.add(d.getName());
                        }
                    }
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });
        addBehaviour(new RequestServer());
    }

    @Override
    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        gui.setVisible(false);
        gui.dispose();
        super.takeDown();
    }

    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            if (!users.isEmpty()) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
            }
        } catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }

    public void showUsers(Collection<User> users) {
        gui.addUsers(users);
    }
}