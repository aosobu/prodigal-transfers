package Complaint.controller;

import Complaint.Service.actor.ActorService;
import Complaint.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/actor/")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("create")
    public Actor createActor(@RequestBody Actor actor) {
        return actorService.createOrUpdateAnActor(actor);
    }

    @GetMapping("all")
    public List<Actor> allActors() {
        return actorService.getAllActors();
    }

    @GetMapping("{id}")
    public Actor getAnActor(@PathVariable Long id) {
        return actorService.getAnActor(id);
    }

    @PatchMapping("{id}")
    public Actor updateAnActor(@PathVariable("id") Long id, @RequestBody Actor actor) {
        return actorService.createOrUpdateAnActor(actor);
    }

    @DeleteMapping("{id}")
    public void deleteActor(@PathVariable Long id) {
        actorService.deleteAnActor(id);
    }
}