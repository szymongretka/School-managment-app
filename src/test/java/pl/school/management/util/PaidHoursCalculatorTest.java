package pl.school.management.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class PaidHoursCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void calculate(LocalDateTime from, LocalDateTime to, int expected) {
        int result = PaidHoursCalculator.calculate(from, to);

        assertThat(result)
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideData() {
        LocalDateTime dateTime4_10 = LocalDateTime.of(2024, 2, 18, 4, 10, 0);
        LocalDateTime dateTime6_59 = LocalDateTime.of(2024, 2, 18, 6, 59, 0);
        LocalDateTime dateTime8_15 = LocalDateTime.of(2024, 2, 18, 8, 15, 0);
        LocalDateTime dateTime11_00 = LocalDateTime.of(2024, 2, 18, 11, 0, 0);
        LocalDateTime dateTime11_59 = LocalDateTime.of(2024, 2, 18, 11, 59, 0);
        LocalDateTime dateTime12_00 = LocalDateTime.of(2024, 2, 18, 12, 0, 0);
        LocalDateTime dateTime14_23 = LocalDateTime.of(2024, 2, 18, 14, 23, 0);
        LocalDateTime dateTime20_20 = LocalDateTime.of(2024, 2, 18, 20, 20, 0);

        return Stream.of(
                Arguments.of(dateTime8_15, dateTime11_00, 0),
                Arguments.of(dateTime6_59, dateTime12_00, 2),
                Arguments.of(dateTime4_10, dateTime14_23, 6),
                Arguments.of(dateTime4_10, dateTime6_59, 3),
                Arguments.of(dateTime14_23, dateTime20_20, 7),
                Arguments.of(dateTime4_10, dateTime11_00, 3),
                Arguments.of(dateTime11_59, dateTime14_23, 3)
        );
    }

}