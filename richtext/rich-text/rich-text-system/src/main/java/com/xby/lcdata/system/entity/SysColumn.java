package com.xby.lcdata.system.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SysColumn {
    private Integer id;
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private long ordinalPosition;
    private String columnDefault;
    private String isNullable;
    private String dataType;
    private Long characterMaximumLength;
    private Long characterOctetLength;
    private Long numericPrecision;
    private Long numericScale;
    private Long datetimePrecision;
    private String characterSetName;
    private String collationName;
    private String columnType;
    private String columnKey;
    private String extra;
    private String privileges;
    private String columnComment;
    private String generationExpression;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TABLE_CATALOG")
    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    @Basic
    @Column(name = "TABLE_SCHEMA")
    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Basic
    @Column(name = "TABLE_NAME")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "COLUMN_NAME")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "ORDINAL_POSITION")
    public long getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(long ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Basic
    @Column(name = "COLUMN_DEFAULT")
    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    @Basic
    @Column(name = "IS_NULLABLE")
    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    @Basic
    @Column(name = "DATA_TYPE")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Basic
    @Column(name = "CHARACTER_MAXIMUM_LENGTH")
    public Long getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(Long characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    @Basic
    @Column(name = "CHARACTER_OCTET_LENGTH")
    public Long getCharacterOctetLength() {
        return characterOctetLength;
    }

    public void setCharacterOctetLength(Long characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }

    @Basic
    @Column(name = "NUMERIC_PRECISION")
    public Long getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(Long numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    @Basic
    @Column(name = "NUMERIC_SCALE")
    public Long getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(Long numericScale) {
        this.numericScale = numericScale;
    }

    @Basic
    @Column(name = "DATETIME_PRECISION")
    public Long getDatetimePrecision() {
        return datetimePrecision;
    }

    public void setDatetimePrecision(Long datetimePrecision) {
        this.datetimePrecision = datetimePrecision;
    }

    @Basic
    @Column(name = "CHARACTER_SET_NAME")
    public String getCharacterSetName() {
        return characterSetName;
    }

    public void setCharacterSetName(String characterSetName) {
        this.characterSetName = characterSetName;
    }

    @Basic
    @Column(name = "COLLATION_NAME")
    public String getCollationName() {
        return collationName;
    }

    public void setCollationName(String collationName) {
        this.collationName = collationName;
    }

    @Basic
    @Column(name = "COLUMN_TYPE")
    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    @Basic
    @Column(name = "COLUMN_KEY")
    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    @Basic
    @Column(name = "EXTRA")
    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Basic
    @Column(name = "PRIVILEGES")
    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    @Basic
    @Column(name = "COLUMN_COMMENT")
    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Basic
    @Column(name = "GENERATION_EXPRESSION")
    public String getGenerationExpression() {
        return generationExpression;
    }

    public void setGenerationExpression(String generationExpression) {
        this.generationExpression = generationExpression;
    }

}
