package ch.ceruleansands.seshat;

import com.google.common.hash.HashCode;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Random;

/**
 * @author Thomsch
 */
@Singleton
public class TileIdGenerator {
    private final Random random;

    @Inject
    public TileIdGenerator(Random random) {
        this.random = random;
    }

    public String getNext() {
        return HashCode.fromLong(random.nextLong()).toString();
    }
}
