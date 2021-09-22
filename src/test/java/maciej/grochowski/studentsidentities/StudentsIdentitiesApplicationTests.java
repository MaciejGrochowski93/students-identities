package maciej.grochowski.studentsidentities;

import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
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
    void get_six_pesel_letters_from_DOB_CORRECT_equals() {
        DTO.setDob(LocalDate.of(1993, 2, 17));

        String expected = "930217";
        String actual = utils.getSixPeselLettersFromDOB(DTO);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("invalidDOBProvider")
    void get_six_pesel_letters_from_DOB_INCORRECT_fails(String invalidDate) {
        DTO.setDob(LocalDate.of(1993, 2, 17));

        String actual = utils.getSixPeselLettersFromDOB(DTO);

        assertNotEquals(invalidDate, actual);
    }

    private static Stream<Arguments> invalidDOBProvider() {
        return Stream.of(Arguments.of("930218"), Arguments.of("930218154"), Arguments.of("930"), Arguments.of("9302AB"));
    }

    @Test
    void validate_age_under18_throws_TooYoungException() {
        DTO.setDob(LocalDate.now().minusYears(18).plusDays(1));
        assertThrows(TooYoungException.class, () -> utils.validateAge(DTO));
    }

    @Test
    void validate_age_18_or_older_does_NOT_throw(){
        DTO.setDob(LocalDate.now().minusYears(18));
        assertDoesNotThrow(() -> utils.validateAge(DTO));
    }

    @Test
    void validate_pesel_equals_DOB_returns_OK() {
        DTO.setPesel("93021712345");
        DTO.setDob(LocalDate.of(1993, 2, 17));

        String expected = "930217";
        String actual = utils.getSixPeselLettersFromDOB(DTO);

        assertEquals(expected, actual);
    }

    @Test
    void validate_pesel_different_from_DOB_throws_PeselDateNotMatchException() {
        DTO.setPesel("93021712345");
        DTO.setDob(LocalDate.of(1980, 2, 17));

        assertThrows(PeselDateNotMatchException.class, () -> utils.validatePesel(DTO));
    }

    @Test
    void validate_pesel_contains_letters_throws_NumberFormatException() {
        DTO.setPesel("930217abcde");
        DTO.setDob(LocalDate.of(1993, 2, 17));

        assertThrows(NumberFormatException.class, () -> utils.validatePesel(DTO));
    }
}
