package maciej.grochowski.studentsidentities.utils;

import maciej.grochowski.studentsidentities.entity.Student;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class StudentUtils {

    public boolean isAdult(Student student) {
        LocalDate studentBirthday = student.getDob();
        LocalDate today = LocalDate.now();

        Period time = Period.between(studentBirthday, today);
        int years = time.getYears();
        return years >= 18;
    }

    public boolean isPeselValid(Student student) {
        String sixLettersFromDOB = getSixPeselLettersFromDOB(student);
        String sixLettersFromPESEL = student.getPesel().substring(0, 6);
        return sixLettersFromDOB.equals(sixLettersFromPESEL);
    }

    public String getSixPeselLettersFromDOB(Student student) {
        LocalDate studentDob = student.getDob();;
        String dateString = studentDob.toString();
        // 2021-08-12

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
