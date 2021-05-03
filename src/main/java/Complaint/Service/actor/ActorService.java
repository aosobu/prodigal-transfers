package Complaint.Service.actor;

import Complaint.model.Actor;

import java.util.List;

public interface ActorService {

    List<Actor> getAllActors();

    Actor getAnActor(Long actorId);

    Actor createOrUpdateAnActor(Actor actor);

    void deleteAnActor(Long actorId);
}
