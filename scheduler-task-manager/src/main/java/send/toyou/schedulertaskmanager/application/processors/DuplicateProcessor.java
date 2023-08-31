package send.toyou.schedulertaskmanager.application.processors;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import send.toyou.schedulertaskmanager.domain.events.JobTriggerEvent;

public class DuplicateProcessor {
    private static final String STORE_NAME = "job-triggers-store";

    public KStream<String, JobTriggerEvent> process(final KStream<String, JobTriggerEvent> inbound) {
        return inbound.transform(Deduplicate::new, STORE_NAME).selectKey(((key, value) -> null));
    }

    private static class Deduplicate implements Transformer<String, JobTriggerEvent, KeyValue<String, JobTriggerEvent>> {
        private KeyValueStore<String, Short> store;

        @Override
        public void init(ProcessorContext context) {
            this.store = context.getStateStore(STORE_NAME);
        }

        @Override
        public KeyValue<String, JobTriggerEvent> transform(String key, JobTriggerEvent value) {
            final var hasBeenProcessed = store.get(key);

            if (hasBeenProcessed == null) {
                return null;
            }

            store.put(key, (short) 1);
            return KeyValue.pair(key, value);
        }

        @Override
        public void close() {

        }
    }
}
