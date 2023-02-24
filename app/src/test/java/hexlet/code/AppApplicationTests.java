package hexlet.code;

import com.github.database.rider.core.api.dataset.DataSet;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.models.User;
import hexlet.code.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
@ExtendWith(SpringExtension.class)
//@DataSet("datasets/users.yml")
class AppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private static UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        userRepository.save(new User("John", "Doe", "john-doe.@mail.com", "123"));
        userRepository.save(new User("Jessica", "Simpson", "simp@gmail.com", "1234"));
        userRepository.save(new User("Robert", "Lock", "loc@loc.com", "1235"));
        userRepository.save(new User("John", "Smith", "john@gmail.com", "1236"));
    }

    @Test
    void testGetUsers() throws Exception {
        var response = mockMvc.perform(get("/api/users")).andReturn().getResponse();
        var content = response.getContentAsString();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertTrue(content.contains("John"));
        assertTrue(content.contains("Smith"));
        assertTrue(content.contains("john@gmail.com"));
        assertFalse(content.contains("password"));
    }

    @Test
    void testGetUser() {

    }

    @Test
    void testCreateUser() {

    }

    @Test
    void testUpdateUser() {

    }

    @Test
    void testDeleteUser() {

    }

}
