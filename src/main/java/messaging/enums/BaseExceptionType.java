package messaging.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum BaseExceptionType {

    REST_GENERAL_EXCEPTION("General exception occured."),
    REST_CONSTRAINT_VIOLATAION_EXCEPTION("Constraint violation exception occured"),
    REST_METHOD_ARGUEMENT_NOT_VALID_EXCEPTION("Arguments passed not valid."),
    REST_RESOURCE_NOT_FOUND_EXCEPTION("Resource not found.");
    @Getter
    private final String validationMessage;

    BaseExceptionType(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public static BaseExceptionType forMessage(String validationMessage) {
        if(StringUtils.isEmpty(validationMessage)){
            return null;
        }
        for(BaseExceptionType exceptionType : values()){
            if(exceptionType.validationMessage.equalsIgnoreCase(validationMessage)){
                return exceptionType;
            }
        }
        return null;
    }
}
