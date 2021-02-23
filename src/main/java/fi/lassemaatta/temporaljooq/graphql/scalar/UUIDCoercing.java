package fi.lassemaatta.temporaljooq.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingSerializeException;

import java.util.UUID;


public class UUIDCoercing implements Coercing<UUID, UUID> {

    @Override
    public UUID serialize(final Object input) {
        if (input == null) {
            throw new CoercingSerializeException("Expected type 'UUID' but was 'null'.");
        }
        if (!(input instanceof UUID)) {
            throw new CoercingSerializeException("Expected type 'UUID' but was '" + input.getClass().getSimpleName() + "'.");
        }
        return ((UUID) input);
    }

    @Override
    public UUID parseValue(final Object input) {
        if (input == null) {
            throw new CoercingSerializeException("Expected type 'UUID' but was 'null'.");
        }
        if (input instanceof UUID) {
            return (UUID) input;
        }
        if (input instanceof String) {
            try {
                return UUID.fromString((String) input);
            } catch (final RuntimeException e) {
                throw new CoercingSerializeException("Coercing failed due to String->UUID exception", e);
            }
        }
        throw new CoercingSerializeException("Expected type 'UUID' but was '" + input.getClass().getSimpleName() + "'.");
    }

    @Override
    public UUID parseLiteral(final Object input) {
        if (input == null) {
            throw new CoercingParseLiteralException("Expected type 'StringValue' but was 'null'.");
        }
        if (input instanceof StringValue) {
            try {
                return UUID.fromString(((StringValue) input).getValue());
            } catch (final RuntimeException e) {
                throw new CoercingParseLiteralException("Coercing failed due to String->UUID exception", e);
            }
        } else {
            throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + input.getClass().getSimpleName() + "'.");
        }
    }
}
