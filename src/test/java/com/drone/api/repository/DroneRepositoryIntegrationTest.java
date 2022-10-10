package com.drone.api.repository;

import com.drone.api.fixture.DroneFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class DroneRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DroneRepository droneRepository;

//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Test
    void injectedComponentsAreNotNull() {
        //assertThat(dataSource).isNotNull();
        //assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(droneRepository).isNotNull();
    }

    @Test
    void whenSaved_thenFindsByState() {
        droneRepository.save(DroneFixture.getDroneEntity());
        assertThat(droneRepository.findDroneByState("IDLE")).isNotNull();
    }
}
