databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1664233777155-1") {
        addColumn(tableName: "vehicle") {
            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1664233777155-2") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "vehicle", constraintName: "FKrmyxjc1r0nxymg692mq9emy56", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }
}
