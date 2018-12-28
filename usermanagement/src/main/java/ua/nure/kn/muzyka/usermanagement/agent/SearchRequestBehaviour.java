package ua.nure.kn.muzyka.usermanagement.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;
import java.util.Objects;

public class SearchRequestBehaviour extends Behaviour {

    private List<AID> aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehaviour(List<AID> aids, String firstName, String lastName) {
        this.aids = aids;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(firstName + "," + lastName);
        if (Objects.nonNull(aids)) {
            for (AID aid : aids) {
                message.addReceiver(aid);
            }
            myAgent.send(message);
        }
    }

    @Override
    public boolean done() {
        return true;
    }
}