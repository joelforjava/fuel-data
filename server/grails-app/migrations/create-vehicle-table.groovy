databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1632958596556-1") {
        dropDefaultValue(columnDataType: "varchar(255)", columnName: "address", tableName: "gas_station")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-2") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "city", tableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-3") {
        dropDefaultValue(columnDataType: "varchar(255)", columnName: "city", tableName: "gas_station")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-4") {
        addNotNullConstraint(columnDataType: "datetime(6)", columnName: "date_created", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-5") {
        addNotNullConstraint(columnDataType: "datetime(6)", columnName: "date_created", tableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-6") {
        addNotNullConstraint(columnDataType: "datetime(6)", columnName: "date_occurred", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (manual)", id: "drop-gas-station-fk-from-fill_up-for-editing-col-1") {
        dropForeignKeyConstraint(baseTableName: "fill_up", constraintName: "fk_gas_station_id")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-7") {
        addNotNullConstraint(columnDataType: "bigint", columnName: "gas_station_id", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-8") {
        addNotNullConstraint(columnDataType: "datetime(6)", columnName: "last_updated", tableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-9") {
        addNotNullConstraint(columnDataType: "decimal(19,3)", columnName: "num_gallons", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-10") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "octane", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-11") {
        dropDefaultValue(columnDataType: "varchar(255)", columnName: "octane", tableName: "fill_up")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-12") {
        addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "odometer_reading", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-13") {
        addNotNullConstraint(columnDataType: "decimal(19,3)", columnName: "price_per_unit", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-14") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "station_name", tableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-15") {
        dropDefaultValue(columnDataType: "varchar(255)", columnName: "station_name", tableName: "gas_station")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-16") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "station_state", tableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-17") {
        dropDefaultValue(columnDataType: "varchar(255)", columnName: "station_state", tableName: "gas_station")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-18") {
        addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "total_cost", tableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-19") {
        dropDefaultValue(columnDataType: "bigint", columnName: "version", tableName: "fill_up")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-20") {
        dropDefaultValue(columnDataType: "bigint", columnName: "version", tableName: "gas_station")
    }

    changeSet(author: "joel (manual)", id: "correct-fill_up-id-type") {
        sql(""" ALTER TABLE fill_up MODIFY COLUMN id BIGINT AUTO_INCREMENT """)
    }

    changeSet(author: "joel (manual)", id: "correct-gas_station-id-type") {
        sql(""" ALTER TABLE gas_station MODIFY COLUMN id BIGINT """)
    }

    changeSet(author: "joel (generated)", id: "add-gas-station-fk-back-to-fill_up-after-editing-col-1") {
        addForeignKeyConstraint(baseColumnNames: "gas_station_id", baseTableName: "fill_up", constraintName: "fk_gas_station_id", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-21") {
        addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "zip_code", tableName: "gas_station", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-22") {
        createTable(tableName: "vehicle") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "vehiclePK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "model_year", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "make", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "model", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1632958596556-23") {
        createTable(tableName: "vehicle_fill_up") {
            column(name: "vehicle_fillups_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "fill_up_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1632958596556-24") {
        addForeignKeyConstraint(baseColumnNames: "vehicle_fillups_id", baseTableName: "vehicle_fill_up", constraintName: "FK3cw5ngapxt5u4a4ey864i866a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vehicle", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1632958596556-25") {
        addForeignKeyConstraint(baseColumnNames: "fill_up_id", baseTableName: "vehicle_fill_up", constraintName: "FKhmaud1mk7ysftwgdwcu13g8g7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "fill_up", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "fix-zip-code-from-decimal-to-bigint") {
        // For some reason, Liquibase changed the type form INT (as it was in the database)
        // To Decimal (in step 21 above) even though the type in the Domain object is BigInteger!
        // Need to keep an eye on this to see if it tries to change again!
        addNotNullConstraint(columnDataType: "bigint", columnName: "zip_code", tableName: "gas_station", validate: "true")
    }

}
