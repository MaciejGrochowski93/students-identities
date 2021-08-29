package maciej.grochowski.studentsidentities.utils;

import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Component
public class StudentUtils {

    public void validateAge(StudentCreationDTO DTO) throws TooYoungException {
        LocalDate studentBirthday = DTO.getDob();
        LocalDate today = LocalDate.now();

        Period time = Period.between(studentBirthday, today);
        int years = time.getYears();
        if (years < 18) {
            throw new TooYoungException("Student must be at least 18 years old.");
        }
    }

    public void validatePesel(StudentCreationDTO DTO) throws PeselDateNotMatchException {
        if (!isNumeric(DTO.getPesel())) {
            throw new NumberFormatException();
        }
        String sixLettersFromDOB = getSixPeselLettersFromDOB(DTO);
        String sixLettersFromPESEL = DTO.getPesel().substring(0, 6);
        if (!sixLettersFromDOB.equals(sixLettersFromPESEL)) {
            throw new PeselDateNotMatchException("PESEL and date of birth do not match. Please, check them again.");
        }

    }

    public boolean isNumeric(String numberWord) {
        if (numberWord == null) {
            return false;
        }
        try {
            Double.parseDouble(numberWord);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getSixPeselLettersFromDOB(StudentCreationDTO DTO) {
        LocalDate studentDob = DTO.getDob();
        String dateString = studentDob.toString();

        String peselSixLetters = "";
        for (int i = 2; i < 10; i++) {
            char currentChar = dateString.charAt(i);
            if (currentChar == '-') {
                continue;
            }
            peselSixLetters += currentChar;
        }
        return peselSixLetters;
    }

    public Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
