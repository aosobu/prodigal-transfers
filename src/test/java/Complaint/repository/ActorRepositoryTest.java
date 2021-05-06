package Complaint.repository;

import Complaint.model.Actor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    Actor sampleActor;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Create an Actor")
    void createActor_thenSaveToDB() {
        Actor actor = new Actor();
        actor.setId(1L);
        actor.setDesk("333");
        actor.setBranchCode("XYZ987");
        actor.setStaffName("TestnameII");
        actor.setStaffType("Contract");
        actor.setStaffId("B321");

        sampleActor = actorRepository.save(actor);
        log.info("Actor has been created ---> " + sampleActor);
        assertThat(actor.getId()).isNotNull();
    }

    @Test
    @DisplayName("Actor exists in DB")
    void findActorByIdInTheDB(){

        sampleActor = actorRepository.findById((long) 1).orElse(null);
        assertThat(sampleActor).isNotNull();
        log.info("Test actor retrieved from the database ---> " + sampleActor);
    }

    @Test
    @DisplayName("Actor does not exist in DB")
    void findThatActorByIdInTheDBIsNotPresent(){

        sampleActor = actorRepository.findById((long) 3).orElse(null);
        assertThat(sampleActor).isNull();
        log.info("No test actor retrieved from the database with this ID");
    }

    @Test
    @DisplayName("Get all actors test")
    void whenFindAllActorsIsCalled_thenReturnAllActorsListTest(){

        List<Actor> actors = actorRepository.findAll();
        assertThat(actors.size()).isEqualTo(1);
        log.info("All actors retrieved from the database --> "+ actors);
    }

    @Test
    @DisplayName("Update actor Details test")
    void whenTransactionDetailsIsUpdated_thenUpdateInDB(){

        sampleActor = actorRepository.findById(2L).orElse(null);
        assertThat(sampleActor).isNotNull();
        log.info("The actor exists in the database ---> " + sampleActor);
        sampleActor.setStaffId("C456");

        sampleActor = actorRepository.save(sampleActor);
        assertThat(sampleActor.getStaffId()).isEqualTo("C456");
    }

    @Test
    @DisplayName("Delete Actor Test")
    void whenDeleteIsCalled_thenDeleteActorTest(){

        List<Actor> allActors = actorRepository.findAll();
        assertThat(allActors).isNotNull();
        assertThat(allActors.size()).isEqualTo(2);

        Actor scapeActor = actorRepository.findById(2L).orElse(null);
        assertThat(scapeActor).isNotNull();
        actorRepository.deleteById(2L);

        allActors = actorRepository.findAll();
        assertThat(allActors).isNotNull();
        assertThat(allActors.size()).isEqualTo(1);
    }
}