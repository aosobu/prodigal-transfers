package Complaint.Service.actor;

import Complaint.model.Actor;
import Complaint.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ActorServiceImpl implements ActorService {


    private ActorRepository actorRepository;

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor getAnActor(Long actorId) {
        return actorRepository.findById(actorId).orElse(null);
    }

    @Override
    public Actor createOrUpdateAnActor(Actor anActor) {
        return actorRepository.save(anActor);
    }

    @Override
    public void deleteAnActor(Long actorId) {
        actorRepository.deleteById(actorId);
    }
}
