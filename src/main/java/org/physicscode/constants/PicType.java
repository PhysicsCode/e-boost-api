package org.physicscode.constants;

import lombok.Getter;
import org.physicscode.exception.ErrorCode;
import org.physicscode.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum PicType {

    PROFILE("profile"),
    PROFILE_BACKGROUND("profile_back");

    private static final Map<String, PicType> aliasMap = new HashMap<>();

    static {
        Stream.of(PicType.values()).forEach(entry -> aliasMap.put(entry.getAlias(), entry));
    }

    private final String alias;

    PicType(String alias) {

        this.alias = alias;
    }

    public static PicType reverseLookupPicType(String alias) {

        return Optional.ofNullable(aliasMap.get(alias)).orElseThrow(() -> new ServiceException(ErrorCode.INVALID_PICTURE_TYPE));
    }
}
