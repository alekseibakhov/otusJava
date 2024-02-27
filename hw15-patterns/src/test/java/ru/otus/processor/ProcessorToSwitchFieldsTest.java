package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProcessorToSwitchFieldsTest {
    @Test
    void processorReplacesField11AndField12() {
        var processor = new ProcessorToSwitchFields();

        var message = new Message.Builder(1L)
                .field11("11")
                .field12("12")
                .build();

        var newMessage = processor.process(message);

        assertThat(newMessage.getField11()).isEqualTo("12");
        assertThat(newMessage.getField12()).isEqualTo("11");
    }
}
