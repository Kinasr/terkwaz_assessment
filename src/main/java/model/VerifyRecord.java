package model;

import lombok.Data;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

public @Data
class VerifyRecord {

    private final String message;
    private Boolean condition;
    private final Executable executable;

    public VerifyRecord(String message, boolean condition, Executable executable) {
        this.message = message;
        this.condition = condition;
        this.executable = executable;
    }

    public VerifyRecord(String message, Executable executable) {
        this.message = message;
        this.executable = executable;
    }

    public Optional<Boolean> condition() {
        return Optional.ofNullable(this.condition);
    }
}
