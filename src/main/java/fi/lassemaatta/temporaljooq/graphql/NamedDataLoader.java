package fi.lassemaatta.temporaljooq.graphql;

import org.dataloader.DataLoader;

public interface NamedDataLoader<K, V> {

    String name();

    DataLoader<K, V> dataloader();
}
