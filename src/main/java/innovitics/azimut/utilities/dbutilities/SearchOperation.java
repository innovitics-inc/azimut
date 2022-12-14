package innovitics.azimut.utilities.dbutilities;

import java.sql.Date;

import javax.persistence.criteria.Path;

public enum  SearchOperation {

	GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    NOT_EQUAL,
    EQUAL,
    LIKE,
    LIKE_START,
    LIKE_END,
    IN,
    NOT_IN,
    IS_NULL,
    IS_NOT_NULL,
    BETWEEN,
    BEFORE,
    AFTER,
    GROUP_BY,
    PARENT_GREATER_THAN,
    PARENT_LESS_THAN,
    PARENT_GREATER_THAN_EQUAL,
    PARENT_LESS_THAN_EQUAL,
    PARENT_NOT_EQUAL,
    PARENT_EQUAL,
    PARENT_LIKE,
    PARENT_LIKE_START,
    PARENT_LIKE_END,
    PARENT_IN,
    PARENT_NOT_IN,
    PARENT_IS_NULL,
    PARENT_IS_NOT_NULL,
    PARENT_BETWEEN,
    PARENT_BEFORE,
    PARENT_AFTER,
    PARENT_GROUP_BY,
    COUNT,
    MAX,
    MIN,
    AVG,
    SUM
    
    
    
    

}
