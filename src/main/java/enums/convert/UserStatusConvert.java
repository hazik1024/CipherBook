package enums.convert;

import enums.UserStatus;

import javax.persistence.AttributeConverter;

public class UserStatusConvert implements AttributeConverter<UserStatus, Integer> {
    public Integer convertToDatabaseColumn(UserStatus userStatus) {
        return userStatus.getValue();
    }

    public UserStatus convertToEntityAttribute(Integer i) {
        if (UserStatus.normal.getValue() == i) {
            return UserStatus.normal;
        }
        else if (UserStatus.freeze.getValue() == i) {
            return UserStatus.freeze;
        }
        else if (UserStatus.cancel.getValue() == i) {
            return UserStatus.cancel;
        }
        return UserStatus.none;
    }
}
