databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1632877596982-1") {
        createTable(tableName: "fill_up") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "fill_upPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "odometer_reading", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "octane", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "num_gallons", type: "DECIMAL(19, 3)") {
                constraints(nullable: "false")
            }

            column(name: "price_per_unit", type: "DECIMAL(19, 3)") {
                constraints(nullable: "false")
            }

            column(name: "gas_station_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "total_cost", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "date_occurred", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1632877596982-2") {
        createTable(tableName: "gas_station") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "gas_stationPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "address", type: "VARCHAR(255)")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "station_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "city", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "zip_code", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "station_state", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1632877596982-3") {
        addForeignKeyConstraint(baseColumnNames: "gas_station_id", baseTableName: "fill_up", constraintName: "FK8yis05uu1bihai4wfsxuj9c8t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "gas_station", validate: "true")
    }
}
