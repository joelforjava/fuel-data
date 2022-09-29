databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1664233777155-1") {
        addColumn(tableName: "vehicle") {
            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (manual)", id: "add-initial-user") {
        sql(""" INSERT INTO user (version, id, date_created, username, password)
                VALUES (0, 1, '2022-09-29 07:00:00.0', 'joelforjava', 'changeme') """)
    }

    changeSet(author: "joel (manual)", id: "add-user-to-vehicle") {
        sql(""" UPDATE vehicle set user_id = 1 where user_id = 0 """)
    }

    changeSet(author: "joel (generated)", id: "1664233777155-2") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "vehicle", constraintName: "FKrmyxjc1r0nxymg692mq9emy56", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }
}
