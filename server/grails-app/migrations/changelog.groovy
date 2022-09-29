databaseChangeLog = {

    include file: 'create-initial-tables.groovy'
    include file: 'create-vehicle-table.groovy'
    include file: 'update-fillup-vehicle-relationship.groovy'
    include file: 'create-user-table.groovy'
    include file: 'update-user-relationships.groovy'
    include file: 'create-user-role-tables.groovy'
}
