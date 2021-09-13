package maciej.grochowski.studentsidentities;

import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentsIdentitiesApplicationTests {

    StudentUtils utils;
    TestInfo testInfo;
    TestReporter testReporter;
    StudentCreationDTO DTO;

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        utils = new StudentUtils();
        DTO = new StudentCreationDTO();
    }

    @Test
    void getSixPeselLettersFromDOBTest() {
        DTO.setDob(LocalDate.of(1993, 2, 17));

        String expected = "930217";
        String actual = utils.getSixPeselLettersFromDOB(DTO);

        assertEquals(expected, actual);

        String expectedFail = "930218";
        assertNotEquals(expectedFail, actual);
    }

    @Test
    void validateAgeTest() {
        DTO.setDob(LocalDate.now().minusYears(18).plusDays(1));
        assertThrows(TooYoungException.class, () -> utils.validateAge(DTO));

        DTO.setDob(LocalDate.now().minusYears(18));
        assertDoesNotThrow(() -> utils.validateAge(DTO));
    }

    @Test
    void validatePeselTest() {
        DTO.setPesel("93021712345");
        DTO.setDob(LocalDate.of(1993, 2, 17));
        assertDoesNotThrow(() -> utils.validatePesel(DTO));

        DTO.setPesel("12345678912");
        assertThrows(PeselDateNotMatchException.class, () -> utils.validatePesel(DTO));

        DTO.setPesel("930217abcde");
        assertThrows(NumberFormatException.class, () -> utils.validatePesel(DTO));
    }
}
