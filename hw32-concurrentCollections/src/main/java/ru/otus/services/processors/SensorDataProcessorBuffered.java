package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final PriorityBlockingQueue<SensorData> queue;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.queue = new PriorityBlockingQueue<>(bufferSize, new SensorDataComparator());
    }

    @Override
    public void process(SensorData data) {
        queue.put(data);
        if (queue.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        try {
            List<SensorData> list = new ArrayList<>();
            queue.drainTo(list);
            if (!list.isEmpty()) {
                writer.writeBufferedData(list);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }

    static class SensorDataComparator implements Comparator<SensorData> {

        @Override
        public int compare(SensorData o, SensorData o2) {
            return o.getMeasurementTime().compareTo(o2.getMeasurementTime());
        }
    }
}
