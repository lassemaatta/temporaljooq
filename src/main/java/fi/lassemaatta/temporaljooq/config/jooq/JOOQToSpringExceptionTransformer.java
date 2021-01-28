package fi.lassemaatta.temporaljooq.config.jooq;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class JOOQToSpringExceptionTransformer extends DefaultExecuteListener {

    @Override
    public void exception(final ExecuteContext ctx) {
        final SQLDialect dialect = ctx.configuration().dialect();
        final SQLExceptionTranslator translator = (dialect != null)
                ? new SQLErrorCodeSQLExceptionTranslator(dialect.name())
                : new SQLStateSQLExceptionTranslator();

        ctx.exception(translator.translate("jOOQ",
                                           ctx.sql(),
                                           ctx.sqlException()));
    }
}
