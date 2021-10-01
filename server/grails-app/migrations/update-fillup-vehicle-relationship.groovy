databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1633043285180-1") {
        addColumn(tableName: "fill_up") {
            column(name: "vehicle_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (manual)", id: "add-initial-vehicle") {
        sql(""" INSERT INTO vehicle (version, id, model_year, make, model)
                VALUES (0, 1, 2011, 'Honda', 'CR-Z') """)
    }

    changeSet(author: "joel (manual)", id: "add-vehicle_id-value-to-existing-fillups") {
        sql(""" UPDATE fill_up SET vehicle_id = 1 WHERE vehicle_id = 0 """)
    }

    changeSet(author: "joel (generated)", id: "1633043285180-2") {
        addForeignKeyConstraint(baseColumnNames: "vehicle_id", baseTableName: "fill_up", constraintName: "FKn03wcdv1yvrxwb7gtcrwv9vod", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vehicle", validate: "true")
    }

    changeSet(author: "joel (generated)", id: "1633043285180-3") {
        dropForeignKeyConstraint(baseTableName: "vehicle_fill_up", constraintName: "FK3cw5ngapxt5u4a4ey864i866a")
    }

    changeSet(author: "joel (generated)", id: "1633043285180-4") {
        dropForeignKeyConstraint(baseTableName: "vehicle_fill_up", constraintName: "FKhmaud1mk7ysftwgdwcu13g8g7")
    }

    changeSet(author: "joel (generated)", id: "1633043285180-5") {
        dropTable(tableName: "vehicle_fill_up")
    }
}
