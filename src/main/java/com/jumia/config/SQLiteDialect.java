package com.jumia.config;

import java.sql.Types;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StringType;


public class SQLiteDialect extends Dialect {
	String integerValue = "integer";
    public SQLiteDialect() {
    	
    	String substrValue = "substr";
    	registerColumnType(Types.BIT, integerValue);
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, integerValue);
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.REAL, "real");
        registerColumnType(Types.DOUBLE, "double");
        registerColumnType(Types.NUMERIC, "numeric");
        registerColumnType(Types.DECIMAL, "decimal");
        registerColumnType(Types.CHAR, "char");
        registerColumnType(Types.VARCHAR, "varchar");
        registerColumnType(Types.LONGVARCHAR, "longvarchar");
        registerColumnType(Types.DATE, "date");
        registerColumnType(Types.TIME, "time");
        registerColumnType(Types.TIMESTAMP, "timestamp");
        registerColumnType(Types.BINARY, "blob");
        registerColumnType(Types.VARBINARY, "blob");
        registerColumnType(Types.LONGVARBINARY, "blob");
       
        registerColumnType(Types.BLOB, "blob");
        registerColumnType(Types.CLOB, "clob");
        registerColumnType(Types.BOOLEAN, integerValue);
 
        registerFunction("concat", new VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""));
        registerFunction("mod", new SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"));
        registerFunction(substrValue, new StandardSQLFunction(substrValue, StringType.INSTANCE));
        registerFunction("substring", new StandardSQLFunction(substrValue, StringType.INSTANCE));
    }
 
    public boolean supportsIdentityColumns() {
        return true;
    }
 
    public boolean hasDataTypeInIdentityColumn() {
        return false; // As specify in NHibernate dialect
    }
 
    public String getIdentityColumnString() {

        return integerValue;
    }
 

 
    public boolean supportsLimit() {
        return true;
    }
 
    protected String getLimitString(String query, boolean hasOffset) {
        return new StringBuffer(query.length() + 20).append(query).append(hasOffset ? " limit ? offset ?" : " limit ?")
                .toString();
    }
 
    public boolean supportsTemporaryTables() {
        return true;
    }
 
    public String getCreateTemporaryTableString() {
        return "create temporary table if not exists";
    }
 
    public boolean dropTemporaryTableAfterUse() {
        return false;
    }
 

 
    public boolean hasAlterTable() {
        return false; // As specify in NHibernate dialect
    }
 
    public boolean dropConstraints() {
        return false;
    }
 
    public String getAddColumnString() {
        return "add column";
    }
 
    public String getForUpdateString() {
        return "";
    }
 
    public boolean supportsOuterJoinForUpdate() {
        return false;
    }
 
   
}