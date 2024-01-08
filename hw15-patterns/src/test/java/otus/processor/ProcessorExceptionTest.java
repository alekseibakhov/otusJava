package otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorThrowsException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ProcessorExceptionTest {

    @Test
    void dontThrowException() {
        LocalDateTime dateTime1 = LocalDateTime.now().withSecond(1);

        var processor = new ProcessorThrowsException(dateTime1);

        var msg = new Message.Builder(1L).field1("field1").build();

        assertThat(processor.process(msg)).isNotNull();
    }

    @Test
    void throwException() {
        LocalDateTime dateTime = LocalDateTime.now().withSecond(2);

        var processor = new ProcessorThrowsException(dateTime);

        var msg = new Message.Builder(1L).field1("field1").build();

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processor.process(msg));
    }
}
