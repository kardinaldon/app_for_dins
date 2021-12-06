package com.dins.kafka_app.config;

import com.dins.kafka_app.model.DataTableOne;
import com.dins.kafka_app.model.DataTableTwo;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NamingStrategyConfig extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1L;

    @Value("${application.db.table.first}")
    private String tableOneName;

    @Value("${application.db.table.second}")
    private String tableTwoName;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String tableName;
        if(name.getText().equals(new DataTableOne().getClass().getSimpleName())) {
            tableName = tableOneName;
        }
        else if(name.getText().equals(new DataTableTwo().getClass().getSimpleName())){
            tableName = tableTwoName;
        }
        else{
            tableName = name.getText();
        }
        return Identifier.quote(Identifier.toIdentifier(tableName));
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String columnName = name.getText();
        return Identifier.quote(Identifier.toIdentifier(columnName));
    }
}
